package com.feather.game.player.controlers.pestcontrol;

import com.feather.game.GameObject;
import com.feather.game.Tile;
import com.feather.game.minigames.pest.Lander;
import com.feather.game.player.controlers.Controler;

public final class PestControlLobby extends Controler {
	
	private int landerId;
	
	@Override
	public void start() {
		this.landerId = (Integer) getArguments()[0];
		Lander.getLanders()[landerId].enterLander(player);
	}
	
	@Override
	public void sendInterfaces() {
		player.getPackets().sendIComponentText(407, 3, "");
		player.getPackets().sendIComponentText(407, 14, "" + Lander.getLanders()[landerId].getPlayers().size());
		player.getPackets().sendIComponentText(407, 16, "" + Integer.toString(player.getPestPoints()));
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasResizableScreen() ? 1 : 11, 407);
	}
	
	@Override
	public boolean processMagicTeleport(Tile toTile) {
		this.forceClose();
		return false;
	}

	@Override
	public boolean processItemTeleport(Tile toTile) {
		this.forceClose();
		return false;
	}
	
	@Override
	public void forceClose() {
		player.closeInterfaces();
		Lander.getLanders()[landerId].remove(player);
	}
	
	@Override
	public boolean login() {
		return false;
	}
	
	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean processObjectClick1(GameObject object) {
		switch (object.getId()) {
		case 14314:
			player.getDialogueManager().startDialogue("LanderD");
			return true;
		}
		return true;
	}
}
