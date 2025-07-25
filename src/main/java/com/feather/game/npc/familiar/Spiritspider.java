package com.feather.game.npc.familiar;

import com.feather.game.*;
import com.feather.game.item.Item;
import com.feather.game.player.Player;
import com.feather.game.player.actions.Summoning.Pouches;
import com.feather.utils.Utils;

public class Spiritspider extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5995661005749498978L;

	public Spiritspider(Player owner, Pouches pouch, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

	@Override
	public String getSpecialName() {
		return "Egg Spawn";
	}

	@Override
	public String getSpecialDescription() {
		return "Spawns a random amount of red eggs around the familiar.";
	}

	@Override
	public int getBOBSize() {
		return 0;
	}

	@Override
	public int getSpecialAmount() {
		return 6;
	}

	@Override
	public SpecialAttack getSpecialAttack() {
		return SpecialAttack.CLICK;
	}

	@Override
	public boolean submitSpecial(Object object) {
		Player player = (Player) object;
		setNextAnimation(new Animation(8267));
		player.setNextAnimation(new Animation(7660));
		player.setNextGraphics(new Graphics(1316));
		WorldTile tile = this;
		// attemps to randomize tile by 4x4 area
		for (int trycount = 0; trycount < Utils.getRandom(10); trycount++) {
			tile = new WorldTile(this, 2);
			if (World.canMoveNPC(this.getPlane(), tile.getX(), tile.getY(),
					player.getSize()))
				return true;
			for (Entity entity : this.getPossibleTargets()) {
				if (entity instanceof Player) {
					Player players = (Player) entity;
					players.getPackets().sendGraphics(new Graphics(1342), tile);
				}
				World.addGroundItem(new Item(223, 1), tile, player, false, 120,
						true);
			}
		}
		return true;
	}
}
