package com.feather.game.npc.fightkiln;

import java.util.ArrayList;
import java.util.List;

import com.feather.game.Animation;
import com.feather.game.Entity;
import com.feather.game.World;
import com.feather.game.Tile;
import com.feather.game.npc.NPC;
import com.feather.game.player.Player;

@SuppressWarnings("serial")
public class HarAkenTentacle extends NPC {

	private HarAken aken;
	
	public HarAkenTentacle(int id, Tile tile, HarAken aken) {
		super(id, tile, -1, true, true);
		setForceMultiArea(true);
		setCantFollowUnderCombat(true);
		setNextAnimation(new Animation(id == 15209 ? 16238 : 16241));
		this.aken = aken;
	}

	@Override
	public void sendDeath(Entity source) {
		aken.removeTentacle(this);
		super.sendDeath(source);
	}
	
	@Override
	public ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>(1);
		List<Integer> playerIndexes = World.getRegion(getRegionId()).getPlayerIndexes();
		if(playerIndexes != null) {
			for (int npcIndex : playerIndexes) {
				Player player = World.getPlayers().get(npcIndex);
				if (player == null
						|| player.isDead()
						|| player.hasFinished()
						|| !player.isRunning())
					continue;
				possibleTarget.add(player);
			}
		}
		return possibleTarget;
	}
	
	@Override
	public double getMagePrayerMultiplier() {
		return 0.1;
	}
	
	@Override
	public double getRangePrayerMultiplier() {
		return 0.1;
	}
	
	@Override
	public double getMeleePrayerMultiplier() {
		return 0.1;
	}
}
