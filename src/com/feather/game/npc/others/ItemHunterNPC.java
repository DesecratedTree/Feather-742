package com.feather.game.npc.others;

import java.util.List;

import com.feather.game.World;
import com.feather.game.GameObject;
import com.feather.game.Tile;
import com.feather.game.npc.NPC;
import com.feather.game.player.OwnedObjectManager;
import com.feather.game.player.Player;
import com.feather.game.player.OwnedObjectManager.ConvertEvent;
import com.feather.game.player.actions.BoxAction.HunterNPC;
import com.feather.game.player.content.Hunter;
import com.feather.game.player.content.Hunter.DynamicFormula;
import com.feather.utils.Utils;

@SuppressWarnings("serial")
public class ItemHunterNPC extends NPC {

	public ItemHunterNPC(int id, Tile tile, int mapAreaNameHash,
						 boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
	}

	@Override
	public void processNPC() {
		super.processNPC();
		List<GameObject> objects = World.getRegion(getRegionId()).getSpawnedObjects();
		if (objects != null) {
			final HunterNPC info = HunterNPC.forId(getId());
			int objectId = info.getEquipment().getObjectId();
			for (final GameObject object : objects) {
				if (object.getId() == objectId) {
					if (OwnedObjectManager.convertIntoObject(object,
							new GameObject(info.getSuccessfulTransformObjectId(), 10, 0,
									this.getX(), this.getY(), this.getPlane()),
									new ConvertEvent() {
						@Override
						public boolean canConvert(Player player) {
							if (player == null || player.getLockDelay() > Utils.currentTimeMillis())
								return false;
							if (Hunter.isSuccessful(player, info.getLevel(), new DynamicFormula() {
								@Override
								public int getExtraProbablity(Player player) {
									//bait here
									return 1;
								}
							})) {
								failedAttempt(object, info);
								return false;
							} else {
								setNextAnimation(info.getSuccessCatchAnim());
								return true;
							}
						}
					})) {
						setRespawnTask(); // auto finishes
					}
				}
			}
		}
	}

	private void failedAttempt(GameObject object, HunterNPC info) {
		setNextAnimation(info.getFailCatchAnim());
		if (OwnedObjectManager.convertIntoObject(object,
				new GameObject(info.getFailedTransformObjectId(), 10, 0,
						this.getX(), this.getY(), this.getPlane()),
						new ConvertEvent() {
			@Override
			public boolean canConvert(Player player) {
				//if(larupia)
				//blablabla
				return true;
			}
		})) {
		}
	}
}