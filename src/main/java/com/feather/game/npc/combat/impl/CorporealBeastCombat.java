package com.feather.game.npc.combat.impl;

import java.util.ArrayList;

import com.feather.game.*;
import com.feather.game.npc.NPC;
import com.feather.game.npc.combat.CombatScript;
import com.feather.game.npc.combat.NPCCombatDefinitions;
import com.feather.game.npc.corp.CorporealBeast;
import com.feather.game.player.Player;
import com.feather.game.player.Skills;
import com.feather.game.tasks.WorldTask;
import com.feather.game.tasks.WorldTasksManager;
import com.feather.utils.Utils;

public class CorporealBeastCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 8133 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(40) == 0) {
			CorporealBeast beast = (CorporealBeast) npc;
			beast.spawnDarkEnergyCore();
		}
		int size = npc.getSize();
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		boolean stomp = false;
		for (Entity t : possibleTargets) {
			int distanceX = t.getX() - npc.getX();
			int distanceY = t.getY() - npc.getY();
			if (distanceX < size && distanceX > -1 && distanceY < size
					&& distanceY > -1) {
				stomp = true;
				delayHit(
						npc,
						0,
						t,
						getRegularHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, t)));
			}
		}
		if (stomp) {
			npc.setNextAnimation(new Animation(10496));
			npc.setNextGraphics(new Graphics(1834));
			return defs.getAttackDelay();
		}
		int attackStyle = Utils.getRandom(4);
		if (attackStyle == 0 || attackStyle == 1) { // melee
			int distanceX = target.getX() - npc.getX();
			int distanceY = target.getY() - npc.getY();
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1)
				attackStyle = 2 + Utils.getRandom(2); // set mage
			else {
				npc.setNextAnimation(new Animation(attackStyle == 0 ? defs
						.getAttackEmote() : 10058));
				delayHit(
						npc,
						0,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				return defs.getAttackDelay();
			}
		}
		if (attackStyle == 2) { // powerfull mage spiky ball
			npc.setNextAnimation(new Animation(10410));
			delayHit(
					npc,
					1,
					target,
					getMagicHit(
							npc,
							getRandomMaxHit(npc, 650,
									NPCCombatDefinitions.MAGE, target)));
			World.sendProjectile(npc, target, 1825, 41, 16, 41, 0, 16, 0);
		} else if (attackStyle == 3) { // translucent ball of energy
			npc.setNextAnimation(new Animation(10410));
			delayHit(
					npc,
					1,
					target,
					getMagicHit(
							npc,
							getRandomMaxHit(npc, 550,
									NPCCombatDefinitions.MAGE, target)));
			if (target instanceof Player) {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						int skill = Utils.getRandom(2);
						skill = skill == 0 ? Skills.MAGIC
								: (skill == 1 ? Skills.SUMMONING
										: Skills.PRAYER);
						Player player = (Player) target;
						if (skill == Skills.PRAYER)
							player.getPrayer().drainPrayer(
									10 + Utils.getRandom(40));
						else {
							int lvl = player.getSkills().getLevel(skill);
							lvl -= 1 + Utils.getRandom(4);
							player.getSkills().set(skill, lvl < 0 ? 0 : lvl);
						}
						player.getPackets().sendGameMessage(
								"Your " + Skills.SKILL_NAME[skill]
										+ " has been slighly drained!");
					}

				}, 1);
				World.sendProjectile(npc, target, 1823, 41, 16, 41, 0, 16, 0);
			}
		} else if (attackStyle == 4) {
			npc.setNextAnimation(new Animation(10410));
			final WorldTile tile = new WorldTile(target);
			World.sendProjectile(npc, tile, 1824, 41, 16, 30, 0, 16, 0);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					for (int i = 0; i < 6; i++) {
						final WorldTile newTile = new WorldTile(tile, 3);
						if (!World.canMoveNPC(newTile.getPlane(),
								newTile.getX(), newTile.getY(), 1))
							continue;
						World.sendProjectile(npc, tile, newTile, 1824, 0, 0,
								25, 0, 30, 0);
						for (Entity t : possibleTargets) {
							if (Utils.getDistance(newTile.getX(),
									newTile.getY(), t.getX(), t.getY()) > 1
									|| !t.clipedProjectile(newTile, false))
								continue;
							delayHit(
									npc,
									0,
									t,
									getMagicHit(
											npc,
											getRandomMaxHit(npc, 350,
													NPCCombatDefinitions.MAGE,
													t)));
						}
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								World.sendGraphics(npc, new Graphics(1806),
										newTile);
							}

						});
					}
				}
			}, 1);
		}
		return defs.getAttackDelay();
	}
}
