package com.feather.game.item;

import com.feather.game.Tile;
import com.feather.game.player.Player;

@SuppressWarnings("serial")
public class GroundItem extends Item {

	private Tile tile;
	private Player owner;
	private boolean invisible;
	private boolean grave;

	public GroundItem(int id) {
		super(id);
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public GroundItem(Item item, Tile tile, Player owner,
					  boolean underGrave, boolean invisible) {
		super(item.getId(), item.getAmount());
		this.tile = tile;
		this.owner = owner;
		grave = underGrave;
		this.invisible = invisible;
	}

	public Tile getTile() {
		return tile;
	}

	public boolean isGrave() {
		return grave;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public Player getOwner() {
		return owner;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

}
