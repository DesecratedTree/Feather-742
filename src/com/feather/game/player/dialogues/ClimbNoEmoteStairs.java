package com.feather.game.player.dialogues;

import com.feather.game.Tile;

public class ClimbNoEmoteStairs extends Dialogue {

	private Tile upTile;
	private Tile downTile;

	// uptile, downtile, climbup message, climbdown message, emoteid
	@Override
	public void start() {
		upTile = (Tile) parameters[0];
		downTile = (Tile) parameters[1];
		sendOptionsDialogue("What would you like to do?",
				(String) parameters[2], (String) parameters[3], "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1) {
			player.useStairs(-1, upTile, 0, 1);
		} else if (componentId == OPTION_2)
			player.useStairs(-1, downTile, 0, 1);
		end();
	}

	@Override
	public void finish() {

	}

}
