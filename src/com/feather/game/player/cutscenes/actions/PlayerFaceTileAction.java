package com.feather.game.player.cutscenes.actions;

import com.feather.game.Tile;
import com.feather.game.player.Player;
import com.feather.game.player.cutscenes.Cutscene;

public class PlayerFaceTileAction extends CutsceneAction {

	private int x, y;

	public PlayerFaceTileAction(int x, int y, int actionDelay) {
		super(-1, actionDelay);
		this.x = x;
		this.y = y;
	}

	@Override
	public void process(Player player, Object[] cache) {
		Cutscene scene = (Cutscene) cache[0];
		player.setNextFaceWorldTile(new Tile(scene.getBaseX() + x, scene
				.getBaseY() + y, player.getPlane()));
	}

}
