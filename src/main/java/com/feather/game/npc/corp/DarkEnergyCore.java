package com.feather.game.npc.corp;

import java.util.ArrayList;

import com.feather.game.Entity;
import com.feather.game.Hit;
import com.feather.game.World;
import com.feather.game.Hit.HitLook;
import com.feather.game.WorldTile;
import com.feather.game.npc.NPC;
import com.feather.game.player.Player;
import com.feather.utils.Utils;

@SuppressWarnings("serial")
public class DarkEnergyCore extends NPC {

	private CorporealBeast beast;
	private Entity target;

	public DarkEnergyCore(CorporealBeast beast) {
		super(8127, beast, -1, true, true);
		setForceMultiArea(true);
		this.beast = beast;
		changeTarget = 2;
	}

	private int changeTarget;
	private int delay;

	@Override
	public void processNPC() {
		if (isDead() || hasFinished())
			return;
		if (delay > 0) {
			delay--;
			return;
		}
		if (changeTarget > 0) {
			if (changeTarget == 1) {
				ArrayList<Entity> possibleTarget = beast.getPossibleTargets();
				if (possibleTarget.isEmpty()) {
					finish();
					beast.removeDarkEnergyCore();
					return;
				}
				target = possibleTarget.get(Utils.getRandom(possibleTarget
						.size() - 1));
				setNextWorldTile(new WorldTile(target));
				World.sendProjectile(this, this, target, 1828, 0, 0, 40, 40,
						20, 0);
			}
			changeTarget--;
			return;
		}
		if (target == null || target.getX() != getX()
				|| target.getY() != getY() || target.getPlane() != getPlane()) {
			changeTarget = 5;
			return;
		}
		int damage = Utils.getRandom(50) + 50;
		target.applyHit(new Hit(this, Utils.random(1, 131), HitLook.MAGIC_DAMAGE));
		beast.heal(damage);
		delay = getPoison().isPoisoned() ? 10 : 3;
		if (target instanceof Player) {
			Player player = (Player) target;
			player.getPackets()
					.sendGameMessage(
							"The dark core creature steals some life from you for its master.");
		}
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}
	
	@Override
	public void sendDeath(Entity source) {
		super.sendDeath(source);
		beast.removeDarkEnergyCore();
	}

}
