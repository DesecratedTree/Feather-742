package com.feather.game.npc.others;

import com.feather.game.Entity;
import com.feather.game.Tile;
import com.feather.game.minigames.CastleWars;
import com.feather.game.npc.NPC;
import com.feather.utils.Utils;

@SuppressWarnings("serial")
public class CastleWarBarricade extends NPC {

	private int team;

	public CastleWarBarricade(int team, Tile tile) {
		super(1532, tile, -1, true, true);
		setCantFollowUnderCombat(true);
		this.team = team;
	}

	@Override
	public void processNPC() {
		if (isDead())
			return;
		cancelFaceEntityNoCheck();
		if (getId() == 1533 && Utils.getRandom(20) == 0)
			sendDeath(this);
	}

	public void litFire() {
		transformIntoNPC(1533);
		sendDeath(this);
	}

	public void explode() {
		// TODO gfx
		sendDeath(this);
	}

	@Override
	public void sendDeath(Entity killer) {
		resetWalkSteps();
		getCombat().removeTarget();
		if (this.getId() != 1533) {
			setNextAnimation(null);
			reset();
			setLocation(getRespawnTile());
			finish();
		} else {
			super.sendDeath(killer);
		}
		CastleWars.removeBarricade(team, this);
	}

}
