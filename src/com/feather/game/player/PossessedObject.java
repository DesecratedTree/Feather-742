package com.feather.game.player;

import com.feather.game.GameObject;

public class PossessedObject extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -543150569322118775L;

	private Player owner;

	public PossessedObject(Player owner, int id, int type, int rotation, int x,
			int y, int plane) {
		super(id, type, rotation, x, y, plane);
		setOwner(owner);
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
