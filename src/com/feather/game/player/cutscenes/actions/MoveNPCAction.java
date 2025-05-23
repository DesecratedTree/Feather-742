package com.feather.game.player.cutscenes.actions;

import com.feather.game.Tile;
import com.feather.game.npc.NPC;
import com.feather.game.player.Player;
import com.feather.game.player.cutscenes.Cutscene;

public class MoveNPCAction extends CutsceneAction {

	private int x, y, plane, movementType;

	public MoveNPCAction(int cachedObjectIndex, int x, int y, boolean run,
			int actionDelay) {
		this(cachedObjectIndex, x, y, -1, run ? Player.RUN_MOVE_TYPE
				: Player.WALK_MOVE_TYPE, actionDelay);
	}

	public MoveNPCAction(int cachedObjectIndex, int x, int y, int plane,
			int movementType, int actionDelay) {
		super(cachedObjectIndex, actionDelay);
		this.x = x;
		this.y = y;
		this.plane = plane;
		this.movementType = movementType;
	}

	@Override
	public void process(Player player, Object[] cache) {
		NPC npc = (NPC) cache[getCachedObjectIndex()];
		Cutscene scene = (Cutscene) cache[0];
		if (movementType == Player.TELE_MOVE_TYPE) {
			npc.setNextWorldTile(new Tile(scene.getBaseX() + x, scene
					.getBaseY() + y, plane));
			return;
		}
		npc.setRun(movementType == Player.RUN_MOVE_TYPE);
		npc.addWalkSteps(scene.getBaseX() + x, scene.getBaseY() + y);
	}

}
