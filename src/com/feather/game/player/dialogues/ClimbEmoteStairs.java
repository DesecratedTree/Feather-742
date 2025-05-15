package com.feather.game.player.dialogues;

import com.feather.game.Tile;

public class ClimbEmoteStairs extends Dialogue {

	private Tile upTile;
	private Tile downTile;
	private int emoteId;

	// uptile, downtile, climbup message, climbdown message, emoteid
	@Override
	public void start() {
		upTile = (Tile) parameters[0];
		downTile = (Tile) parameters[1];
		emoteId = (Integer) parameters[4];
		sendOptionsDialogue("What would you like to do?",
				(String) parameters[2], (String) parameters[3], "Never mind.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (componentId == OPTION_1)
			player.useStairs(emoteId, upTile, 2, 3);
		else if (componentId == OPTION_2)
			player.useStairs(emoteId, downTile, 2, 2);
		end();
	}

	@Override
	public void finish() {

	}

}
