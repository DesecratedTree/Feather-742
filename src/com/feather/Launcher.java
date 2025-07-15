package com.feather;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.alex.store.Index;
import com.feather.cache.Cache;
import com.feather.cache.parser.ItemDefinitions;
import com.feather.cache.parser.ItemsEquipIds;
import com.feather.cache.parser.NPCDefinitions;
import com.feather.cache.parser.ObjectDefinitions;
import com.feather.cores.CoresManager;
import com.feather.game.Region;
import com.feather.game.RegionBuilder;
import com.feather.game.World;
import com.feather.game.npc.combat.CombatScriptsHandler;
import com.feather.game.player.LendingManager;
import com.feather.game.player.Player;
import com.feather.game.player.content.FishingSpotsHandler;
import com.feather.game.player.content.FriendChatsManager;
import com.feather.game.player.controlers.ControlerHandler;
import com.feather.game.player.cutscenes.CutscenesHandler;
import com.feather.game.player.dialogues.DialogueHandler;
import com.feather.game.tasks.WorldTasksManager;
import com.feather.game.worldlist.WorldList;
import com.feather.net.ServerChannelHandler;
import com.feather.utils.*;
import com.feather.utils.huffman.Huffman;

public final class Launcher {

	private static final String SEPARATOR = "═══════════════════════════════════════════════════════════";
	private static final String LOADING_PREFIX = "▶ ";
	private static final String SUCCESS_PREFIX = "✓ ";
	private static final String ERROR_PREFIX = "✗ ";
	private static final String INFO_PREFIX = "ℹ ";

	public static void main(String[] args) throws Exception {
		printHeader();

		Settings.HOSTED = false;
		Settings.DEBUG = true;

		long startTime = Utils.currentTimeMillis();

		try {
			initializeServer();
			long totalTime = Utils.currentTimeMillis() - startTime;
			printSuccessMessage(totalTime);
			startBackgroundTasks();
		} catch (Exception e) {
			Logger.handle(e);
			logError("Server initialization failed: " + e.getMessage());
			System.exit(1);
		}
	}

	private static void printHeader() {
		System.out.println("╔" + SEPARATOR + "╗");
		System.out.println("║                  FEATHER GAME SERVER                      ║");
		System.out.println("║                  Starting up...                           ║");
		System.out.println("╚" + SEPARATOR + "╝");
	}

	private static void initializeServer() throws Exception {
		// Phase 1: Core Systems
		logPhase("Phase 1: Core Systems");
		initWithTimer("Cache", Cache::init);
		initWithTimer("Items Equipment IDs", ItemsEquipIds::init);
		initWithTimer("Huffman Compression", Huffman::init);

		// Phase 2: Data Files
		logPhase("Phase 2: Data Files");
		initWithTimer("Display Names", DisplayNames::init);
		initWithTimer("IP Ban List", IPBanL::init);
		initWithTimer("PK Rankings", PkRank::init);
		initWithTimer("Duel Tournament Rankings", DTRank::init);
		initWithTimer("Map Archive Keys", MapArchiveKeys::init);
		initWithTimer("Map Areas", MapAreas::init);
		initWithTimer("Object Spawns", ObjectSpawns::init);
		initWithTimer("NPC Spawns", NPCSpawns::init);
		initWithTimer("NPC Combat Definitions", NPCCombatDefinitionsL::init);
		initWithTimer("NPC Bonuses", NPCBonuses::init);
		initWithTimer("NPC Drops", NPCDrops::init);
		initWithTimer("Item Examines", ItemExamines::init);
		initWithTimer("Music Hints", MusicHints::init);
		initWithTimer("Shops Handler", ShopsHandler::init);
		initWithTimer("NPC Examines", NPCExamines::init);

		// Phase 3: Game Systems
		logPhase("Phase 3: Game Systems");
		initWithTimer("World List", WorldList::init);
		initWithTimer("Lending Manager", LendingManager::init);
		initWithTimer("Fishing Spots", FishingSpotsHandler::init);
		initWithTimer("Combat Scripts", CombatScriptsHandler::init);
		initWithTimer("Dialogue Handler", DialogueHandler::init);
		initWithTimer("Controllers", ControlerHandler::init);
		initWithTimer("Cutscenes", CutscenesHandler::init);
		initWithTimer("Friend Chats Manager", FriendChatsManager::init);

		// Phase 4: Final Systems
		logPhase("Phase 4: Final Systems");
		initWithTimer("Cores Manager", CoresManager::init);
		initWithTimer("World", World::init);
		initWithTimer("Region Builder", RegionBuilder::init);

		// Phase 5: Network
		logPhase("Phase 5: Network");
		initNetworkHandler();
	}

	@FunctionalInterface
	private interface InitializerTask {
		void run() throws Exception;
	}

	private static void initWithTimer(String component, InitializerTask initializer) {
		long startTime = Utils.currentTimeMillis();
		logLoading(component);

		try {
			initializer.run();
			long duration = Utils.currentTimeMillis() - startTime;
			logSuccess(component, duration);
		} catch (Exception e) {
			logError("Failed to initialize " + component + ": " + e.getMessage());
			throw new RuntimeException("Initialization failed for " + component, e);
		}
	}

	private static void initNetworkHandler() {
		long startTime = Utils.currentTimeMillis();
		logLoading("Server Channel Handler");

		try {
			ServerChannelHandler.init();
			long duration = Utils.currentTimeMillis() - startTime;
			logSuccess("Server Channel Handler", duration);
		} catch (Throwable e) {
			Logger.handle(e);
			logError("Failed to initialize Server Channel Handler. Shutting down...");
			System.exit(1);
		}
	}

	private static void startBackgroundTasks() {
		logPhase("Background Tasks");
		addAccountsSavingTask();

		if (Settings.HOSTED) {
			addUpdatePlayersOnlineTask();
		}

		addCleanMemoryTask();
		addTaskCleanupTask(); // New task for cleaning up world tasks

		logInfo("All background tasks started successfully");
	}

	private static void printSuccessMessage(long totalTime) {
		System.out.println();
		System.out.println("╔" + SEPARATOR + "╗");
		System.out.println("║                        SERVER READY                       ║");
		System.out.println("║                                                           ║");
		System.out.printf("║   Launch Time: %6d ms                                  ║%n", totalTime);
		System.out.println("║                                                           ║");
		System.out.println("╚" + SEPARATOR + "╝");
	}

	private static void logPhase(String phase) {
		System.out.println();
		System.out.println("┌─────────────────────────────────────────────────────────┐");

		// Format the phase text to fit within the box (59 chars max)
		String paddedPhase = String.format("│ %-55s │", phase);
		System.out.println(paddedPhase);

		System.out.println("└─────────────────────────────────────────────────────────┘");
	}

	private static void logLoading(String component) {
		System.out.printf("  %s Loading %-40s", LOADING_PREFIX, component + "...");
	}

	private static void logSuccess(String component, long duration) {
		System.out.printf(" %s (%d ms)%n", SUCCESS_PREFIX, duration);
	}

	private static void logError(String message) {
		System.out.println();
		System.out.printf("  %s %s%n", ERROR_PREFIX, message);
	}

	private static void logInfo(String message) {
		System.out.printf("  %s %s%n", INFO_PREFIX, message);
	}

	// Background Tasks
	private static void addUpdatePlayersOnlineTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(() -> {
			try {
				updateWebsitePlayerCount(World.getPlayers().size());
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}, 2, 2, TimeUnit.MINUTES);

		logInfo("Website player count updater started (2 min intervals)");
	}

	private static void addCleanMemoryTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(() -> {
			try {
				boolean lowMemory = Runtime.getRuntime().freeMemory() < Settings.MIN_FREE_MEM_ALLOWED;
				cleanMemory(lowMemory);

				if (lowMemory) {
					logInfo("Memory cleanup performed (low memory detected)");
				}
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}, 0, 5, TimeUnit.MINUTES);

		logInfo("Memory cleanup task started (5 min intervals)");
	}

	private static void addAccountsSavingTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(() -> {
			try {
				saveFiles();
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}, 1, 30, TimeUnit.SECONDS);

		logInfo("Auto-save task started (30 second intervals)");
	}

	private static void addTaskCleanupTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(() -> {
			try {
				int taskCount = WorldTasksManager.getTasksCount();
				int entityCount = WorldTasksManager.getBoundEntitiesCount();

				// Log task statistics every 10 minutes
				if (taskCount > 1000 || entityCount > 100) {
					logInfo(String.format("Task Status: %d active tasks, %d bound entities",
							taskCount, entityCount));
				}
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}, 10, 10, TimeUnit.MINUTES);

		logInfo("Task monitoring started (10 min intervals)");
	}

	// Utility Methods
	private static void updateWebsitePlayerCount(int playerCount) throws IOException {
		URL url = new URL("http://www.matrixftw.com/updateplayeramount.php?players="
				+ playerCount + "&auth=JFHDJF3847234");
		url.openStream().close();
	}

	public static void saveFiles() {
		int savedPlayers = 0;

		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished()) {
				continue;
			}
			SerializableFilesManager.savePlayer(player);
			savedPlayers++;
		}

		// Save other data
		DisplayNames.save();
		IPBanL.save();
		PkRank.save();
		DTRank.save();

		if (Settings.DEBUG && savedPlayers > 0) {
			logInfo("Saved " + savedPlayers + " player accounts");
		}
	}

	public static void cleanMemory(boolean force) {
		long before = Runtime.getRuntime().freeMemory();

		if (force) {
			ItemDefinitions.clearItemsDefinitions();
			NPCDefinitions.clearNPCDefinitions();
			ObjectDefinitions.clearObjectDefinitions();

			for (Region region : World.getRegions().values()) {
				region.removeMapFromMemory();
			}
		}

		for (Index index : Cache.STORE.getIndexes()) {
			index.resetCachedFiles();
		}

		CoresManager.fastExecutor.purge();
		System.gc();

		long after = Runtime.getRuntime().freeMemory();
		long cleaned = after - before;

		if (Settings.DEBUG && cleaned > 0) {
			logInfo(String.format("Memory cleaned: %d KB freed", cleaned / 1024));
		}
	}

	// Shutdown Methods
	public static void shutdown() {
		try {
			logInfo("Server shutdown initiated...");

			// Clean up world tasks before shutdown
			WorldTasksManager.cleanupAllTasks();

			closeServices();

			logInfo("Server shutdown complete");
			System.exit(0);
		} catch (Exception e) {
			Logger.handle(e);
			throw new RuntimeException("Shutdown failed", e);
		}
	}

	public static void closeServices() {
		logInfo("Closing server services...");

		ServerChannelHandler.shutdown();
		CoresManager.shutdown();
		WorldTasksManager.cleanupAllTasks();

		if (Settings.HOSTED) {
			try {
				updateWebsitePlayerCount(0);
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}

		logInfo("All services closed");
	}

	public static void restart() {
		logInfo("Server restart initiated...");

		closeServices();
		System.gc();

		try {
			Runtime.getRuntime().exec("java -server -Xms256m -Xmx2048m Launcher false false true false");
			System.exit(0);
		} catch (Throwable e) {
			Logger.handle(e);
			logError("Restart failed: " + e.getMessage());
		}
	}

	private Launcher() {
		// Utility class
	}
}