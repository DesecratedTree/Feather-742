package com.feather.game.npc.familiar;

import com.feather.game.Animation;
import com.feather.game.Graphics;
import com.feather.game.Tile;
import com.feather.game.player.Player;
import com.feather.game.player.Skills;
import com.feather.game.player.actions.Summoning.Pouches;

public class Firetitan extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8645666414867813973L;

	public Firetitan(Player owner, Pouches pouch, Tile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

	@Override
	public String getSpecialName() {
		return "Titan's Constitution ";
	}

	@Override
	public String getSpecialDescription() {
		return "Defence by 12.5%, and it can also increase a player's Life Points 80 points higher than their max Life Points.";
	}

	@Override
	public int getBOBSize() {
		return 0;
	}

	@Override
	public int getSpecialAmount() {
		return 20;
	}

	@Override
	public SpecialAttack getSpecialAttack() {
		return SpecialAttack.CLICK;
	}

	@Override
	public boolean submitSpecial(Object object) {
		int newLevel = getOwner().getSkills().getLevel(Skills.DEFENCE)
				+ (getOwner().getSkills().getLevelForXp(Skills.DEFENCE) / (int) 12.5);
		if (newLevel > getOwner().getSkills().getLevelForXp(Skills.DEFENCE)
				+ (int) 12.5)
			newLevel = getOwner().getSkills().getLevelForXp(Skills.DEFENCE)
					+ (int) 12.5;
		getOwner().setNextGraphics(new Graphics(2011));
		getOwner().setNextAnimation(new Animation(7660));
		getOwner().getSkills().set(Skills.DEFENCE, newLevel);
		return true;
	}

}
