package com.feather.game.npc.dragons;

import com.feather.game.WorldTile;
import com.feather.game.npc.NPC;

@SuppressWarnings("serial")
public class KingBlackDragon extends NPC {

	public KingBlackDragon(int id, WorldTile tile, int mapAreaNameHash,
                           boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setLureDelay(0);
	}

	public static boolean atKBD(WorldTile tile) {
		if ((tile.getX() >= 2250 && tile.getX() <= 2292)
				&& (tile.getY() >= 4675 && tile.getY() <= 4710))
			return true;
		return false;
	}

}
