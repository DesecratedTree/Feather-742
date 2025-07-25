package com.feather;

import java.io.File;
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
import com.feather.game.worldlist.WorldList;
import com.feather.net.ServerChannelHandler;
import com.feather.utils.*;
import com.feather.utils.huffman.Huffman;

public final class Launcher {

	public static void main(String[] args) throws Exception {
		Settings.HOSTED = false;
		Settings.DEBUG = true;
		long currentTime = Utils.currentTimeMillis();
		if (Settings.HOSTED) {

		}
		Logger.log("Launcher", "Initing Cache...");
		Cache.init();
		ItemsEquipIds.init();
		Huffman.init();
		Logger.log("Launcher", "Initing Data Files...");
		DisplayNames.init();
		IPBanL.init();
		PkRank.init();
		DTRank.init();
		MapArchiveKeys.init();
		MapAreas.init();
		ObjectSpawns.init();
		NPCSpawns.init();
		NPCCombatDefinitionsL.init();
		NPCBonuses.init();
		NPCDrops.init();
		ItemExamines.init();
		MusicHints.init();
		ShopsHandler.init();
		NPCExamines.init();
		Logger.log("Launcher", "Initing WorldList...");
		WorldList.init();
		Logger.log("Launcher", "Initing Lent Items...");
		LendingManager.init();
		Logger.log("Launcher", "Initing Fishing Spots...");
		FishingSpotsHandler.init();
		Logger.log("Launcher", "Initing NPC Combat Scripts...");
		CombatScriptsHandler.init();
		Logger.log("Launcher", "Initing Dialogues...");
		DialogueHandler.init();
		Logger.log("Launcher", "Initing Controlers...");
		ControlerHandler.init();
		Logger.log("Launcher", "Initing Cutscenes...");
		CutscenesHandler.init();
		Logger.log("Launcher", "Initing Friend Chats Manager...");
		FriendChatsManager.init();
		Logger.log("Launcher", "Initing Cores Manager...");
		CoresManager.init();
		Logger.log("Launcher", "Initing World...");
		World.init();
		Logger.log("Launcher", "Initing Region Builder...");
		RegionBuilder.init();
		Logger.log("Launcher", "Initing Server Channel Handler...");
		try {
			ServerChannelHandler.init();
		} catch (Throwable e) {
			Logger.handle(e);
			Logger.log("Launcher",
					"Failed initing Server Channel Handler. Shutting down...");
			System.exit(1);
			return;
		}
		Logger.log("Launcher", "Server took "
				+ (Utils.currentTimeMillis() - currentTime)
				+ " milli seconds to launch.");
		Logger.log("Launcher", "Hosted: " + Settings.HOSTED + " | Debug: " + Settings.DEBUG);
		addAccountsSavingTask();
		if (Settings.HOSTED)
			addUpdatePlayersOnlineTask();
		addCleanMemoryTask();
		// Donations.init();
	}

	private static void setWebsitePlayersOnline(int amount) throws IOException {
		URL url = new URL("http://www.matrixftw.com/updateplayeramount.php?players="+ amount + "&auth=JFHDJF3847234");
		url.openStream().close();
	}

	private static void addUpdatePlayersOnlineTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					setWebsitePlayersOnline(World.getPlayers().size());
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 2, 2, TimeUnit.MINUTES);
	}

	private static void addCleanMemoryTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					cleanMemory(Runtime.getRuntime().freeMemory() < Settings.MIN_FREE_MEM_ALLOWED);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 5, TimeUnit.MINUTES);
	}

	private static void addAccountsSavingTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					saveFiles();
				} catch (Throwable e) {
					Logger.handle(e);
				}

			}
		}, 1, 1, TimeUnit.SECONDS);
	}

	public static void saveFiles() {
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished())
				continue;
			SerializableFilesManager.savePlayer(player);
		}
		DisplayNames.save();
		IPBanL.save();
		PkRank.save();
		DTRank.save();
	}

	public static void cleanMemory(boolean force) {
		if (force) {
			ItemDefinitions.clearItemsDefinitions();
			NPCDefinitions.clearNPCDefinitions();
			ObjectDefinitions.clearObjectDefinitions();
			for (Region region : World.getRegions().values())
				region.removeMapFromMemory();
		}
		for (Index index : Cache.STORE.getIndexes())
			index.resetCachedFiles();
		CoresManager.fastExecutor.purge();
		System.gc();
	}

	public static void shutdown() {
		try {
			closeServices();
		} finally {
			System.exit(0);
		}
	}

	public static void closeServices() {
		ServerChannelHandler.shutdown();
		CoresManager.shutdown();
		if (Settings.HOSTED) {
			try {
				setWebsitePlayersOnline(0);
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}
	}

	public static void restart() {
		closeServices();
		System.gc();
		try {
			Runtime.getRuntime().exec("java -server -Xms256m -Xmx2048m Launcher false false true false");
			System.exit(0);
		} catch (Throwable e) {
			Logger.handle(e);
		}

	}

	private Launcher() {

	}

}
