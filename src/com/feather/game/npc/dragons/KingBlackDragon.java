package com.feather.game.npc.dragons;

import com.feather.game.Tile;
import com.feather.game.npc.NPC;

@SuppressWarnings("serial")
public class KingBlackDragon extends NPC {

	public KingBlackDragon(int id, Tile tile, int mapAreaNameHash,
						   boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setLureDelay(0);
	}

	public static boolean atKBD(Tile tile) {
		if ((tile.getX() >= 2250 && tile.getX() <= 2292)
				&& (tile.getY() >= 4675 && tile.getY() <= 4710))
			return true;
		return false;
	}

}
