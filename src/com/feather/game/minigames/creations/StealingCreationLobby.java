package com.feather.game.minigames.creations;

import com.feather.game.Tile;
import com.feather.game.player.controlers.Controler;

/**
 * @author Richard
 * @author Khaled
 *
 */
public class StealingCreationLobby extends Controler {

	@Override
	public void start() {
		if ((boolean) getArguments()[0])
			StealingCreation.getRedTeam().add(player);
		else 
			StealingCreation.getRedTeam().add(player);
		sendInterfaces();
	}

	@Override
	public void sendInterfaces() {
		player.getInterfaceManager().sendTab(804, player.getInterfaceManager().hasResizableScreen() ? 11 : 27);//TODO find correct one
		StealingCreation.updateInterfaces();
	}

	//TODO object click for exit

	@Override
	public boolean processMagicTeleport(Tile toTile) {
		player.getDialogueManager().startDialogue("SimpleMessage", "A magical force prevents you from teleporting from the arena.");
		return false;
	}

	@Override
	public boolean processItemTeleport(Tile toTile) {
		player.getDialogueManager().startDialogue("SimpleMessage","A magical force prevents you from teleporting from the arena.");
		return false;
	}

	@Override
	public void magicTeleported(int type) {
		player.getControlerManager().forceStop();
	}

	@Override
	public void forceClose() {
		if ((boolean) getArguments()[0])
			StealingCreation.getRedTeam().remove(player);
		else 
			StealingCreation.getRedTeam().remove(player);
		StealingCreation.updateInterfaces();
	}
}
