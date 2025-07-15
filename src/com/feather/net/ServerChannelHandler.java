package com.feather.net;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.feather.Settings;
import com.feather.cores.CoresManager;
import com.feather.game.tasks.WorldTasksManager;
import com.feather.io.InputStream;
import com.feather.net.decoders.WorldPacketsDecoder;
import com.feather.utils.Logger;

public final class ServerChannelHandler extends SimpleChannelHandler {

	private static volatile ChannelGroup channels;
	private static volatile ServerBootstrap bootstrap;
	private static final AtomicInteger connectionCount = new AtomicInteger(0);

	// Performance constants
	private static final int MIN_PACKET_SIZE = 1;
	private static final String HANDLER_NAME = "feather-handler";

	public static void init() {
		if (channels != null) {
			Logger.log("ServerChannelHandler", "Already initialized, skipping...");
			return;
		}
		new ServerChannelHandler();
	}

	public static int getConnectedChannelsSize() {
		return connectionCount.get();
	}

	public static boolean isInitialized() {
		return channels != null && bootstrap != null;
	}

	/**
	 * Private constructor - initializes the server
	 * Throws exception if server cannot be started
	 */
	private ServerChannelHandler() {
		try {
			initializeServer();
			//Logger.log("ServerChannelHandler", "Server bound to port " + Settings.PORT_ID);
		} catch (Exception e) {
			Logger.handle(e);
			throw new RuntimeException("Failed to initialize server channel handler", e);
		}
	}

	private void initializeServer() {
		// Initialize channel group for connection management
		channels = new DefaultChannelGroup("feather-channels");

		// Create server bootstrap with optimized settings
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				CoresManager.serverBossChannelExecutor,
				CoresManager.serverWorkerChannelExecutor,
				CoresManager.serverWorkersCount));

		// Configure pipeline
		bootstrap.getPipeline().addLast(HANDLER_NAME, this);

		// Optimize socket options for game server
		configureSocketOptions();

		// Bind to port
		bootstrap.bind(new InetSocketAddress(Settings.PORT_ID));
	}

	private void configureSocketOptions() {
		// Connection options
		bootstrap.setOption("reuseAddress", true);
		bootstrap.setOption("backlog", 1000); // Handle more concurrent connections

		// Child channel options (per connection)
		bootstrap.setOption("child.tcpNoDelay", true);        // Disable Nagle's algorithm
		bootstrap.setOption("child.keepAlive", true);         // Enable keep-alive
		bootstrap.setOption("child.soLinger", 0);             // Close immediately on shutdown
		bootstrap.setOption("child.receiveBufferSize", 8192); // Optimize buffer size
		bootstrap.setOption("child.sendBufferSize", 8192);    // Optimize buffer size

		// Remove the incorrect TcpAckFrequency option (not a valid Netty option)
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		channels.add(e.getChannel());
		connectionCount.incrementAndGet();
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		channels.remove(e.getChannel());
		connectionCount.decrementAndGet();
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		// Create session and attach to context
		Session session = new Session(e.getChannel());
		ctx.setAttachment(session);

		if (Settings.DEBUG) {
			Logger.log("ServerChannelHandler",
					"New connection from: " + e.getChannel().getRemoteAddress());
		}
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Session session = getSession(ctx);
		if (session == null) {
			return;
		}

		try {
			// Handle player disconnection and cleanup
			if (session.getDecoder() instanceof WorldPacketsDecoder) {
                if (session.getWorldPackets().getPlayer() != null) {
					// Clean up player tasks before finishing
					WorldTasksManager.cleanupEntityTasks(session.getWorldPackets().getPlayer());
					session.getWorldPackets().getPlayer().finish();
				}
			}

			if (Settings.DEBUG) {
				Logger.log("ServerChannelHandler",
						"Connection closed: " + e.getChannel().getRemoteAddress());
			}

		} catch (Exception ex) {
			Logger.handle(ex);
		} finally {
			// Clear session reference
			ctx.setAttachment(null);
		}
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		// Quick type check
		if (!(e.getMessage() instanceof ChannelBuffer)) {
			return;
		}

		Session session = getSession(ctx);
		if (session == null || session.getDecoder() == null) {
			return;
		}

		ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
		processIncomingData(session, buffer);
	}

	private void processIncomingData(Session session, ChannelBuffer buffer) {
		// Mark reader index for potential reset
		buffer.markReaderIndex();

		int availableBytes = buffer.readableBytes();

		// Validate packet size
		if (availableBytes < MIN_PACKET_SIZE || availableBytes > Settings.RECEIVE_DATA_LIMIT) {
			if (Settings.DEBUG) {
				Logger.log("ServerChannelHandler",
						"Invalid packet size: " + availableBytes + " bytes");
			}
			return;
		}

		// Read data efficiently
		byte[] data = new byte[availableBytes];
		buffer.readBytes(data);

		// Process packet
		try {
			session.getDecoder().decode(new InputStream(data));
		} catch (Exception ex) {
			Logger.handle(ex);
			// Consider disconnecting problematic clients
			if (ex instanceof SecurityException) {
				session.getChannel().close();
			}
		}
	}

	/**
	 * Safely retrieve session from context
	 */
	private Session getSession(ChannelHandlerContext ctx) {
		Object attachment = ctx.getAttachment();
		return (attachment instanceof Session) ? (Session) attachment : null;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		Throwable cause = e.getCause();

		// Log significant exceptions
		if (Settings.DEBUG) {
			Logger.log("ServerChannelHandler",
					"Exception in channel: " + cause.getMessage());
		}

		// Handle specific exception types
		if (cause instanceof java.io.IOException) {
			// Connection reset, timeout, etc. - normal network issues
			if (Settings.DEBUG) {
				Logger.log("ServerChannelHandler", "Network error: " + cause.getMessage());
			}
		} else {
			// More serious exceptions
			Logger.handle(cause);
		}

		// Close problematic connections
		ctx.getChannel().close();
	}

	/**
	 * Graceful shutdown with timeout
	 */
	public static void shutdown() {
		if (channels == null || bootstrap == null) {
			return;
		}

		try {
			Logger.log("ServerChannelHandler", "Shutting down server...");

			// Close all channels with timeout
			channels.close().awaitUninterruptibly(5000); // 5 second timeout

			// Release external resources
			bootstrap.releaseExternalResources();

			Logger.log("ServerChannelHandler", "Server shutdown complete");

		} catch (Exception e) {
			Logger.handle(e);
		} finally {
			// Reset static references
			channels = null;
			bootstrap = null;
			connectionCount.set(0);
		}
	}

	/**
	 * Get server statistics
	 */
	public static String getServerStats() {
		if (channels == null) {
			return "Server not initialized";
		}

		return String.format("Connections: %d | Channels: %d",
				connectionCount.get(), channels.size());
	}
}