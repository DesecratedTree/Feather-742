package com.feather.game.npc.familiar;

import com.feather.game.Animation;
import com.feather.game.Graphics;
import com.feather.game.Tile;
import com.feather.game.player.Player;
import com.feather.game.player.actions.Summoning.Pouches;

public class Spiritwolf extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2691875962052924796L;

	public Spiritwolf(Player owner, Pouches pouch, Tile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

	@Override
	public String getSpecialName() {
		return "Howl";
	}

	@Override
	public String getSpecialDescription() {
		return "Scares non-player opponents, causing them to retreat. However, this lasts for only a few seconds.";
	}

	@Override
	public int getBOBSize() {
		return 0;
	}

	@Override
	public int getSpecialAmount() {
		return 3;
	}

	@Override
	public SpecialAttack getSpecialAttack() {
		return SpecialAttack.ENTITY;
	}

	@Override
	public boolean submitSpecial(Object object) {
		Player player = (Player) object;
		player.setNextAnimation(new Animation(7660));
		player.setNextGraphics(new Graphics(1316));
		return true;
	}
}
