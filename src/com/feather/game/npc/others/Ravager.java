package com.feather.game.npc.others;

import com.feather.game.Tile;
import com.feather.game.npc.NPC;

@SuppressWarnings("serial")
public class Ravager extends NPC {
	
	boolean destroyingObject = false;

	public Ravager(int id, Tile tile, int mapAreaNameHash,
				   boolean canBeAttackFromOutOfArea) {
		super(id, tile, -1, false, false);
	}
	
	@Override
	public void processNPC() {
	}

}
