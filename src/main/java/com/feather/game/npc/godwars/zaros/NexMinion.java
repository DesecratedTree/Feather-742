package com.feather.game.npc.godwars.zaros;

import com.feather.game.Entity;
import com.feather.game.WorldTile;
import com.feather.game.minigames.ZarosGodwars;
import com.feather.game.npc.NPC;

@SuppressWarnings("serial")
public class NexMinion extends NPC {

	private boolean hasNoBarrier;

	public NexMinion(int id, WorldTile tile, int mapAreaNameHash,
                     boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setCantFollowUnderCombat(true);
		setCapDamage(0);
	}

	public void breakBarrier() {
		setCapDamage(-1);
		hasNoBarrier = true;
	}

	@Override
	public void processNPC() {
		if (isDead() || !hasNoBarrier)
			return;
		if (!getCombat().process())
			checkAgressivity();
	}

	@Override
	public void sendDeath(Entity source) {
		super.sendDeath(source);
		ZarosGodwars.moveNextStage();
	}

}
