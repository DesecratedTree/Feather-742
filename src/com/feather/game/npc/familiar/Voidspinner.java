package com.feather.game.npc.familiar;

import com.feather.game.Animation;
import com.feather.game.Graphics;
import com.feather.game.Tile;
import com.feather.game.player.Player;
import com.feather.game.player.actions.Summoning.Pouches;

public class Voidspinner extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1639238550551778316L;

	public Voidspinner(Player owner, Pouches pouch, Tile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

	@Override
	public String getSpecialName() {
		return "Call To Arms";
	}

	@Override
	public String getSpecialDescription() {
		return "Teleports the player to Void Outpost.";
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
		return SpecialAttack.CLICK;
	}

	@Override
	public boolean submitSpecial(Object object) {
		Player player = (Player) object;
		player.setNextGraphics(new Graphics(1316));
		player.setNextAnimation(new Animation(7660));
		// Magic.sendTeleportSpell(player, upEmoteId, downEmoteId, upGraphicId,
		// downGraphicId, 0, 0, tile, 3, true, Magic.OBJECT_TELEPORT);
		return true;
	}
}
