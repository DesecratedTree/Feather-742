package com.feather.game.minigames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.feather.cores.CoresManager;
import com.feather.game.*;
import com.feather.game.npc.godwars.zaros.Nex;
import com.feather.game.npc.godwars.zaros.NexMinion;
import com.feather.game.player.Player;
import com.feather.game.tasks.WorldTask;
import com.feather.game.tasks.WorldTasksManager;
import com.feather.utils.Utils;

public final class ZarosGodwars {

	private static final List<Player> playersOn = Collections
			.synchronizedList(new ArrayList<Player>());
	// private static final Object LOCK = new Object();

	public static Nex nex;
	public static NexMinion fumus;
	public static NexMinion umbra;
	public static NexMinion cruor;
	public static NexMinion glacies;

	public static int getPlayersCount() {
		return playersOn.size();
	}

	public static void breakFumusBarrier() {
		// synchronized(LOCK) {
		if (fumus == null)
			return;
		fumus.breakBarrier();
		// }
	}

	public static void breakUmbraBarrier() {
		// synchronized(LOCK) {
		if (umbra == null)
			return;
		umbra.breakBarrier();
		// }
	}

	public static void breakCruorBarrier() {
		// synchronized(LOCK) {
		if (cruor == null)
			return;
		cruor.breakBarrier();
		// }
	}

	public static void breakGlaciesBarrier() {
		// synchronized(LOCK) {
		if (glacies == null)
			return;
		glacies.breakBarrier();
		// }
	}

	public static void addPlayer(Player player) {
		// synchronized(LOCK) {
		if (playersOn.contains(player)) {
			// System.out.println("ERROR DOUBLE ENTRY!");
			return;
		}
		playersOn.add(player);
		startWar();
		// }
	}

	public static void removePlayer(Player player) {
		// synchronized(LOCK) {
		playersOn.remove(player);
		cancelWar();
		// }
	}

	public static void deleteNPCS() {
		if (nex != null) {
			nex.killBloodReavers();
			nex.finish();
			nex = null;
		}
		if (fumus != null) {
			fumus.finish();
			fumus = null;
		}
		if (umbra != null) {
			umbra.finish();
			umbra = null;
		}
		if (cruor != null) {
			cruor.finish();
			cruor = null;
		}
		if (glacies != null) {
			glacies.finish();
			glacies = null;
		}
	}

	private static void cancelWar() {
		if (getPlayersCount() == 0)
			deleteNPCS();
	}

	public static ArrayList<Entity> getPossibleTargets() {
		// synchronized(LOCK) {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>(
				playersOn.size());
		for (Player player : playersOn) {
			if (player == null || player.isDead() || player.hasFinished()
					|| !player.isRunning())
				continue;
			possibleTarget.add(player);
			/*
			 * if (player.getFamiliar() != null &&
			 * player.getFamiliar().isAgressive())
			 * possibleTarget.add(player.getFamiliar());
			 */
		}
		return possibleTarget;
		// }
	}

	public static void moveNextStage() {
		// synchronized(LOCK) {
		if (nex == null)
			return;
		nex.moveNextStage();
		// }
	}

	public static void endWar() {
		// synchronized(LOCK) {
		deleteNPCS();
		CoresManager.slowExecutor.schedule(new Runnable() {

			@Override
			public void run() {
				startWar();
			}

		}, 1, TimeUnit.MINUTES);
		// }
	}

	private static void startWar() {
		if (getPlayersCount() >= 1) {
			if (nex == null) {
				World.spawnNPC(13447, new WorldTile(2924, 5202, 0), -1, true,
						true);
				WorldTasksManager.schedule(new WorldTask() {
					private int count = 0;

					@Override
					public void run() {
						// synchronized(LOCK) {
						if (nex == null) {
							stop();
							return;
						}
						if (count == 1) {
							nex.setNextForceTalk(new ForceTalk("AT LAST!"));
							nex.setNextAnimation(new Animation(6355));
							nex.setNextGraphics(new Graphics(1217));
							nex.playSound(3295, 2);
						} else if (count == 3) {
							World.spawnNPC(13451, new WorldTile(2912, 5216, 0),
									-1, true, true);
							fumus.setDirection(Utils.getFaceDirection(1, -1));
							nex.setNextFaceWorldTile(new WorldTile(fumus
									.getCoordFaceX(fumus.getSize()), fumus
									.getCoordFaceY(fumus.getSize()), 0));
							nex.setNextForceTalk(new ForceTalk("Fumus!"));
							nex.setNextAnimation(new Animation(6987));
							World.sendProjectile(fumus, nex, 2244, 18, 18, 60,
									30, 0, 0);
							nex.playSound(3325, 2);
						} else if (count == 5) {
							World.spawnNPC(13452, new WorldTile(2937, 5216, 0),
									-1, true, true);
							umbra.setDirection(Utils.getFaceDirection(-1, -1));
							nex.setNextFaceWorldTile(new WorldTile(umbra
									.getCoordFaceX(umbra.getSize()), umbra
									.getCoordFaceY(umbra.getSize()), 0));
							nex.setNextForceTalk(new ForceTalk("Umbra!"));
							nex.setNextAnimation(new Animation(6987));
							World.sendProjectile(umbra, nex, 2244, 18, 18, 60,
									30, 0, 0);
							nex.playSound(3313, 2);
						} else if (count == 7) {
							World.spawnNPC(13453, new WorldTile(2937, 5190, 0),
									-1, true, true);
							cruor.setDirection(Utils.getFaceDirection(-1, 1));
							nex.setNextFaceWorldTile(new WorldTile(cruor
									.getCoordFaceX(cruor.getSize()), cruor
									.getCoordFaceY(cruor.getSize()), 0));
							nex.setNextForceTalk(new ForceTalk("Cruor!"));
							nex.setNextAnimation(new Animation(6987));
							World.sendProjectile(cruor, nex, 2244, 18, 18, 60,
									30, 0, 0);
							nex.playSound(3299, 2);
						} else if (count == 9) {
							World.spawnNPC(13454, new WorldTile(2912, 5190, 0),
									-1, true, true);
							glacies.setNextFaceWorldTile(new WorldTile(glacies
									.getCoordFaceX(glacies.getSize()), glacies
									.getCoordFaceY(glacies.getSize()), 0));
							glacies.setDirection(Utils.getFaceDirection(1, 1));
							nex.setNextFaceWorldTile(new WorldTile(glacies
									.getCoordFaceX(glacies.getSize()), glacies
									.getCoordFaceY(glacies.getSize()), 0));
							nex.setNextForceTalk(new ForceTalk("Glacies!"));
							nex.setNextAnimation(new Animation(6987));
							World.sendProjectile(glacies, nex, 2244, 18, 18,
									60, 30, 0, 0);
							nex.playSound(3304, 2);
						} else if (count == 11) {
							nex.setNextForceTalk(new ForceTalk(
									"Fill my soul with smoke!"));
							World.sendProjectile(fumus, nex, 2244, 18, 18, 60,
									30, 0, 0);

							nex.playSound(3310, 2);
						} else if (count == 13) {
							nex.setCantInteract(false);
							stop();
							return;
						}
						count++;
					}
					// }
				}, 0, 1);
			}
		}
	}

	private ZarosGodwars() {

	}
}
