package com.feather.game.npc.familiar;

import com.feather.game.Tile;
import com.feather.game.player.Player;
import com.feather.game.player.actions.Summoning.Pouches;

public class Desertwyrm extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 678861520073043877L;

	public Desertwyrm(Player owner, Pouches pouch, Tile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

	@Override
	public String getSpecialName() {
		return "Electric Lash";
	}

	@Override
	public String getSpecialDescription() {
		return "Attacks the player's opponent inflicting up to 50 damage instead of 40 damage. ";
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
		return SpecialAttack.ENTITY;
	}

	@Override
	public boolean submitSpecial(Object object) {
		return false;
	}

}
