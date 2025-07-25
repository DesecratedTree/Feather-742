package com.feather.game.npc.kalph;

import com.feather.game.Animation;
import com.feather.game.Entity;
import com.feather.game.Graphics;
import com.feather.game.WorldTile;
import com.feather.game.npc.NPC;
import com.feather.game.npc.combat.NPCCombatDefinitions;
import com.feather.game.tasks.WorldTask;
import com.feather.game.tasks.WorldTasksManager;

@SuppressWarnings("serial")
public class KalphiteQueen extends NPC {

	public KalphiteQueen(int id, WorldTile tile, int mapAreaNameHash,
                         boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setLureDelay(0);
		setForceAgressive(true);
	}
	
	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		getCombat().removeTarget();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));
				} else if (loop >= defs.getDeathDelay()) {
					if(getId() == 1158) {
						setCantInteract(true);
						transformIntoNPC(1160);
						setNextGraphics(new Graphics(1055));
						setNextAnimation(new Animation(6270));
						WorldTasksManager.schedule(new WorldTask() {

							@Override
							public void run() {
								reset();
								setCantInteract(false);
							}
							
						}, 5);
					}else{
						drop();
						reset();
						setLocation(getRespawnTile());
						finish();
						if (!isSpawned())
							setRespawnTask();
						transformIntoNPC(1158);
					}
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
}
