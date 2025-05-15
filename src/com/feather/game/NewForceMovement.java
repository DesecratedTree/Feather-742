package com.feather.game;

public final class NewForceMovement extends ForceMovement {

	public NewForceMovement(Tile toFirstTile, int firstTileTicketDelay,
                            Tile toSecondTile, int secondTileTicketDelay, int direction) {
		super(toFirstTile, firstTileTicketDelay, toSecondTile, secondTileTicketDelay,
				direction);
	}

	@Override
	public int getDirection() {
		return direction;
	}
	
}
