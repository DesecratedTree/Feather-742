package com.feather.game.route.strategy;

import com.feather.game.WorldObject;
import com.feather.game.route.RouteStrategy;

public class ObjectStrategy extends RouteStrategy {

	/**
	 * Contains object pos x.
	 */
	private final int x;
	/**
	 * Contains object pos y.
	 */
	private final int y;
	/**
	 * Contains object route type.
	 */
	private final int routeType;
	/**
	 * Contains object type.
	 */
	private final int type;
	/**
	 * Contains object rotation.
	 */
	private final int rotation;
	/**
	 * Contains object size X.
	 */
	private final int sizeX;
	/**
	 * Contains object size Y.
	 */
	private final int sizeY;
	/**
	 * Contains block flag.
	 */
	private int accessBlockFlag;

	public ObjectStrategy(WorldObject object) {
		this.x = object.getX();
		this.y = object.getY();
		this.routeType = getType(object);
		this.type = object.getType();
		this.rotation = object.getRotation();
		this.sizeX = rotation == 0 || rotation == 2 ? object.getDefinitions()
				.getSizeX() : object.getDefinitions().getSizeY();
				this.sizeY = rotation == 0 || rotation == 2 ? object.getDefinitions()
						.getSizeY() : object.getDefinitions().getSizeX();
						this.accessBlockFlag = object.getDefinitions().getAccessBlockFlag();
						if (rotation != 0)
							accessBlockFlag = ((accessBlockFlag << rotation) & 0xF)
							+ (accessBlockFlag >> (4 - rotation));
	}

	@Override
	public boolean canExit(int currentX, int currentY, int sizeXY,
			int[][] clip, int clipBaseX, int clipBaseY) {
		switch (routeType) {
		case 0:
			return checkWallInteract(clip, currentX - clipBaseX,
					currentY - clipBaseY, sizeXY, x - clipBaseX, y - clipBaseY,
					type, rotation);
		case 1:
			return checkWallDecorationInteract(clip, currentX
					- clipBaseX, currentY - clipBaseY, sizeXY, x - clipBaseX, y
					- clipBaseY, type, rotation);
		case 2:
			return checkFilledRectangularInteract(clip, currentX
					- clipBaseX, currentY - clipBaseY, sizeXY, sizeXY, x
					- clipBaseX, y - clipBaseY, sizeX, sizeY, accessBlockFlag);
		case 3:
			return currentX == x && currentY == y;
		}
		return false;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ObjectStrategy))
			return false;
		final ObjectStrategy strategy = (ObjectStrategy) other;
		return x == strategy.x && y == strategy.y
				&& routeType == strategy.routeType && type == strategy.type
				&& rotation == strategy.rotation && sizeX == strategy.sizeX
				&& sizeY == strategy.sizeY
				&& accessBlockFlag == strategy.accessBlockFlag;
	}

	@Override
	public int getApproxDestinationSizeX() {
		return sizeX;
	}

	@Override
	public int getApproxDestinationSizeY() {
		return sizeY;
	}

	@Override
	public int getApproxDestinationX() {
		return x;
	}

	@Override
	public int getApproxDestinationY() {
		return y;
	}

	private int getType(WorldObject object) {
		final int type = object.getType();
		if ((type >= 0 && type <= 3) || type == 9)
			return 0; // wall
		else if (type < 9)
			return 1; // deco
		else if (type == 10 || type == 11 || type == 22)
			return 2; // ground
		else
			return 3; // misc
	}

}
