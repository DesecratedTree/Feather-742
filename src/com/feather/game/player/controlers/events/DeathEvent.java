package com.feather.game.player.controlers.events;

import java.util.concurrent.TimeUnit;

import com.feather.cores.CoresManager;
import com.feather.game.Animation;
import com.feather.game.RegionBuilder;
import com.feather.game.GameObject;
import com.feather.game.Tile;
import com.feather.game.player.content.Magic;
import com.feather.game.player.controlers.Controler;
import com.feather.game.tasks.WorldTask;
import com.feather.game.tasks.WorldTasksManager;

public class DeathEvent extends Controler {

	public static final Tile RESPAWN = new Tile(3214, 3423, 0);

	
	private int[] boundChuncks;
	private Stages stage;
	
	@Override
	public void start() {
		loadRoom();
	}
	
	public boolean login() {
		loadRoom();
		return false;
	}
	
	public boolean logout() {
		player.setLocation(new Tile(1978, 5302, 0));
		destroyRoom();
		return false;
	}
	
	private static enum Stages {
		LOADING,
		RUNNING,
		DESTROYING
	}
	
	@Override
	public void sendInterfaces() {
		player.getInterfaceManager().closeCombatStyles();
		player.getInterfaceManager().closeTaskSystem();
		player.getInterfaceManager().closeSkills();
		player.getInterfaceManager().closeInventory();
		player.getInterfaceManager().closeEquipment();
		player.getInterfaceManager().closePrayerBook();
		player.getInterfaceManager().closeMagicBook();
		player.getInterfaceManager().closeEmotes();
	}
	
	public void loadRoom() {
		stage = Stages.LOADING;
		player.lock(); //locks player
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				boundChuncks = RegionBuilder.findEmptyChunkBound(2, 2); 
				RegionBuilder.copyMap(246, 662, boundChuncks[0], boundChuncks[1], 2, 2, new int[1], new int[1]);
				player.reset();
				player.setNextWorldTile(new Tile(boundChuncks[0] * 8 + 10, boundChuncks[1] * 8 + 6, 0));
				player.setNextAnimation(new Animation(-1));
				//1delay because player cant walk while teleing :p, + possible issues avoid
				WorldTasksManager.schedule(new WorldTask()  {
					@Override
					public void run() {
						player.getMusicsManager().playMusic(683);
						player.getPackets().sendBlackOut(2);
						sendInterfaces();
						player.unlock(); //unlocks player
						stage = Stages.RUNNING;
					}

				}, 1);
			}
		});
	}
	

	@Override
	public boolean processMagicTeleport(Tile toTile) {
		return false;
	}

	@Override
	public boolean processItemTeleport(Tile toTile) {
		return false;
	}
	
	/**
	 * return process normaly
	 */
	public boolean processObjectClick1(GameObject object) {
		if(object.getId() == 45803) {
			Magic.sendObjectTeleportSpell(player, true, RESPAWN);
			return false;
		}
		return true;
	}

	

	@Override
	public void magicTeleported(int type) {
		destroyRoom();
		player.getPackets().sendBlackOut(0);
		player.getInterfaceManager().sendCombatStyles();
		player.getCombatDefinitions().sendUnlockAttackStylesButtons();
		player.getInterfaceManager().sendTaskSystem();
		player.getInterfaceManager().sendSkills();
		player.getInterfaceManager().sendInventory();
		player.getInventory().unlockInventoryOptions();
		player.getInterfaceManager().sendEquipment();
		player.getInterfaceManager().sendPrayerBook();
		player.getPrayer().unlockPrayerBookButtons();
		player.getInterfaceManager().sendMagicBook();
		player.getInterfaceManager().sendEmotes();
		player.getEmotesManager().unlockEmotesBook();
		removeControler();
	}
	
	public void destroyRoom() {
		if(stage != Stages.RUNNING) 
			return;
		stage = Stages.DESTROYING;
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}
	@Override
	public void forceClose() {
		destroyRoom();
	}

	
}
