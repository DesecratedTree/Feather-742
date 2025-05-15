package com.feather.game.player.content;

import java.util.HashMap;

import com.feather.game.Tile;
import com.feather.game.npc.NPC;

public class FishingSpotsHandler {

	public static final HashMap<Integer, Integer> moveSpots = new HashMap<Integer, Integer>();

	public static void init() {
		moveSpots.put(new Tile(2836, 3431, 0).getTileHash(),
				new Tile(2845, 3429, 0).getTileHash());
		moveSpots.put(new Tile(2853, 3423, 0).getTileHash(),
				new Tile(2860, 3426, 0).getTileHash());
		moveSpots.put(new Tile(3110, 3432, 0).getTileHash(),
				new Tile(3104, 3423, 0).getTileHash());
		moveSpots.put(new Tile(3104, 3424, 0).getTileHash(),
				new Tile(3110, 3433, 0).getTileHash());
		moveSpots.put(new Tile(3632, 5082, 0).getTileHash(),
				new Tile(3621, 5087, 0).getTileHash());
		moveSpots.put(new Tile(3625, 5083, 0).getTileHash(),
				new Tile(3617, 5087, 0).getTileHash());
		moveSpots.put(new Tile(3621, 5119, 0).getTileHash(),
				new Tile(3617, 5123, 0).getTileHash());
		moveSpots.put(new Tile(3628, 5136, 0).getTileHash(),
				new Tile(3633, 5137, 0).getTileHash());
		moveSpots.put(new Tile(3637, 5139, 0).getTileHash(),
				new Tile(3634, 5148, 0).getTileHash());
		moveSpots.put(new Tile(3652, 5141, 0).getTileHash(),
				new Tile(3658, 5145, 0).getTileHash());
		moveSpots.put(new Tile(3680, 5110, 0).getTileHash(),
				new Tile(3675, 5114, 0).getTileHash());
		
	}

	public static boolean moveSpot(NPC npc) {
		int key = npc.getTileHash();
		Integer spot = moveSpots.get(key);
		if (spot == null && moveSpots.containsValue(key)) {
			for (Integer k : moveSpots.keySet()) {
				Integer v = moveSpots.get(k);
				if (v == key) {
					spot = k;
					break;
				}
			}
		}
		if (spot == null)
			return false;
		npc.setNextWorldTile(new Tile(spot));
		return true;
	}

}
