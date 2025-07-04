package com.feather.game.player;

import com.feather.game.Entity;
import com.feather.game.GameObject;
import com.feather.game.Tile;
import com.feather.game.item.GroundItem;
import com.feather.game.npc.NPC;
import com.feather.game.route.RouteFinder;
import com.feather.game.route.RouteStrategy;
import com.feather.game.route.strategy.EntityStrategy;
import com.feather.game.route.strategy.FixedTileStrategy;
import com.feather.game.route.strategy.FloorItemStrategy;
import com.feather.game.route.strategy.ObjectStrategy;
import com.feather.utils.Utils;

public class RouteEvent {

    /**
     * Object to which we are finding the route.
     */
    private Object object;
    /**
     * The event instance.
     */
    private Runnable event;
    /**
     * Whether we also run on alternative.
     */
    private boolean alternative;
    /**
     * Contains last route strategies.
     */
    private RouteStrategy[] last;

    public RouteEvent(Object object, Runnable event) {
        this(object, event, false);
    }

    public RouteEvent(Object object, Runnable event, boolean alternative) {
        this.object = object;
        this.event = event;
        this.alternative = alternative;
    }

    public boolean instanceOfWorldObject() {
        return object instanceof GameObject;
    }

    public boolean instanceOfEntity() {
        return object instanceof Entity;
    }

    public boolean instanceOfFloorItem() {
        return object instanceof GroundItem;
    }

    public Object getObject() {
        return object;
    }

    private void completePath(Player player) {
        if (this.instanceOfEntity())
            player.faceEntity((Entity) object);
        else if (this.instanceOfWorldObject())
            player.faceObject((GameObject) object);
        else if (this.instanceOfFloorItem())
            player.setNextFaceWorldTile(((GroundItem) object).getTile());
        player.setNextFaceEntity(null);
        player.getAppearence().getAppearanceBlock();
    }

    public boolean processEvent(final Player player) {
        if (player.isLocked())
            return true;

        if (!simpleCheck(player)) {
            player.getPackets().sendGameMessage("You can't reach that.");
            player.getPackets().sendResetMinimapFlag();
            completePath(player);
            return true;
        }
        // else if (EffectsManager.isBound(player)){
        // if ((instanceOfEntity() || instanceOfRSObject()) && Utils.getDistance(player, instanceOfEntity() ? (Entity) object : (RSObject) object) >= 2){
        // player.getPackets().sendGameMessage("You can't reach that.");
        // player.getPackets().sendResetMinimapFlag();
        // completePath(player);
        // return true;
        // }
        // else if (instanceOfFloorItem()){
        // if (Utils.getDistance(player, ((FloorItem) object).getTile()) == 1){
        // player.setNextAnimation(new Animation(832));
        // completePath(player);
        // event.run();
        // return true;
        // }
        // else if (Utils.getDistance(player, ((FloorItem) object).getTile()) >= 2){
        // player.getPackets().sendGameMessage("You can't reach that.");
        // player.getPackets().sendResetMinimapFlag();
        // completePath(player);
        // return true;
        // }
        // }
        // }
        RouteStrategy[] strategies = generateStrategies();
        if (strategies == null)
            return false;
        else if (last != null && match(strategies, last) && player.hasWalkSteps())
            return false;
        if (this.instanceOfEntity())
            player.setNextFaceEntity((Entity) object);
        else
            player.setNextFaceEntity(null);
        if (last != null && match(strategies, last) && !player.hasWalkSteps()) {
            for (int i = 0; i < strategies.length; i++) {
                RouteStrategy strategy = strategies[i];
                int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, player.getX(), player.getY(), player.getPlane(), player.getSize(), strategy, i == (strategies.length - 1));
                if (steps == -1)
                    continue;
                if (this.instanceOfEntity() && this.object instanceof NPC) {
                    if (Utils.getDistance(((NPC) object), player) <= 3)
                        ((NPC) object).resetWalkSteps();
                }
                if ((!RouteFinder.lastIsAlternative() && steps <= 0) || alternative) {
                    if (alternative)
                        player.getPackets().sendResetMinimapFlag();
                    event.run();
                    completePath(player);
                    return true;
                }
            }

            player.getPackets().sendGameMessage("You can't reach that.");
            player.getPackets().sendResetMinimapFlag();
            completePath(player);
            return true;
        } else {
            last = strategies;

            for (int i = 0; i < strategies.length; i++) {
                RouteStrategy strategy = strategies[i];
                int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, player.getX(), player.getY(), player.getPlane(), player.getSize(), strategy, i == (strategies.length - 1));
                if (steps == -1)
                    continue;
                if (this.instanceOfEntity() && this.object instanceof NPC) {
                    if (Utils.getDistance(((NPC) object), player) <= 3)
                        ((NPC) object).resetWalkSteps();
                }
                if ((!RouteFinder.lastIsAlternative() && steps <= 0)) {
                    if (alternative)
                        player.getPackets().sendResetMinimapFlag();
                    event.run();
                    completePath(player);
                    return true;
                }
                int[] bufferX = RouteFinder.getLastPathBufferX();
                int[] bufferY = RouteFinder.getLastPathBufferY();

                Tile last = new Tile(bufferX[0], bufferY[0], player.getPlane());
                player.resetWalkSteps();
                //player.getPackets().sendMinimapFlag(last.getLocalX(player.getLastLoadedMapRegionTile(), player.getMapSize()), last.getLocalY(player.getLastLoadedMapRegionTile(), player.getMapSize()));
                if (player.getFreezeDelay() > Utils.currentTimeMillis())
                    return false;
                for (int step = steps - 1; step >= 0; step--) {
                    if (!player.addWalkSteps(bufferX[step], bufferY[step], 25, true))
                        break;
                }

                return false;
            }

            player.getPackets().sendGameMessage("You can't reach that.");
            player.getPackets().sendResetMinimapFlag();
            completePath(player);
            return true;
        }
    }

    private boolean simpleCheck(Player player) {
        if (object == null) {
            return false;
        } else if (object instanceof Entity) {
            return player.getPlane() == ((Entity) object).getPlane();
        } else if (object instanceof GameObject) {
            return player.getPlane() == ((GameObject) object).getPlane();
        } else if (object instanceof GroundItem) {
            return player.getPlane() == ((GroundItem) object).getTile().getPlane();
        }

        else {
            throw new RuntimeException(object + " is not instanceof any reachable entity.");
        }
    }

    private RouteStrategy[] generateStrategies() {
        if (object == null)
            return last;
        if (object instanceof Entity) {
            return new RouteStrategy[] { new EntityStrategy((Entity) object) };
        } else if (object instanceof GameObject) {
            return new RouteStrategy[] { new ObjectStrategy((GameObject) object) };
        } else if (object instanceof GroundItem) {
            GroundItem item = (GroundItem) object;
            return new RouteStrategy[] { new FixedTileStrategy(item.getTile().getX(), item.getTile().getY()), new FloorItemStrategy(item) };
        } else {
            throw new RuntimeException(object + " is not instanceof any reachable entity.");
        }
    }

    private boolean match(RouteStrategy[] a1, RouteStrategy[] a2) {
        if (a1.length != a2.length)
            return false;
        for (int i = 0; i < a1.length; i++)
            if (!a1[i].equals(a2[i]))
                return false;
        return true;
    }

}
