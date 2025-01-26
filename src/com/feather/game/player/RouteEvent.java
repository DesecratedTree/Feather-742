package com.feather.game.player;

import com.feather.game.WorldObject;
import com.feather.game.WorldTile;
import com.feather.game.route.RouteFinder;
import com.feather.game.route.RouteStrategy;
import com.feather.game.route.strategy.EntityStrategy;
import com.feather.game.route.strategy.ObjectStrategy;
import com.feather.game.Entity;
import com.feather.game.npc.NPC;
import com.feather.game.item.FloorItem;
import com.feather.net.decoders.handlers.ObjectHandler;
import com.feather.net.decoders.handlers.NPCHandler;


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

    public boolean processEvent(final Player player) {
        if (!simpleCheck(player)) {
            player.getPackets().sendGameMessage("You can't reach that.");
            player.resetWalkSteps();
            return true;
        }
        RouteStrategy[] strategies = generateStrategies();
        if (last != null && match(strategies, last) && player.hasWalkSteps()) {
            return false;
        } else if (last != null && match(strategies, last)
                && !player.hasWalkSteps()) {
            for (int i = 0; i < strategies.length; i++) {
                RouteStrategy strategy = strategies[i];
                int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER,
                        player.getX(), player.getY(), player.getPlane(),
                        player.getSize(), strategy,
                        i == (strategies.length - 1));
                if (steps == -1)
                    continue;
                /**
                 * Required because if a player stops running and isn't near the object,
                 * it will still process the click
                 */
                boolean exception = false;
                if (object instanceof WorldObject)
                    exception = //player.getControlerManager().getControler() instanceof StealingCreationController && 
                            !player.withinDistance(
                                    ObjectHandler.findObjectTile((WorldObject)object, player), ObjectHandler.getMinDistance(player, (WorldObject)object));
                else if (object instanceof NPC)
                    exception = //player.getControlerManager().getControler() instanceof StealingCreationController && 
                            !player.withinDistance(
                                    NPCHandler.findNPCTile(((NPC)object), player), NPCHandler.getMinDistance(player, (NPC)object));
                else if (object instanceof FloorItem) {
                    exception = //player.getControlerManager().getControler() instanceof StealingCreationController && 
                            !player.withinDistance(
                                    ((FloorItem)object).getTile(), 2);
                } else
                    exception = //player.getControlerManager().getControler() instanceof StealingCreationController && 
                            !player.withinDistance(
                                    (WorldTile)object, 2);
                if (exception)
                    continue;
                /**
                 * Required because if a player stops running and isn't near the object,
                 * it will still process the click
                 */
                if ((!RouteFinder.lastIsAlternative() && steps <= 0)
                        || alternative) {
                    if (alternative)
                        player.getPackets().sendResetMinimapFlag();
                    event.run();//it thinks that it's reached the npc
                    return true;
                }
            }
            player.getPackets().sendGameMessage("You can't reach that.");
            player.resetWalkSteps();
            return true;
        } else {
            last = strategies;

            for (int i = 0; i < strategies.length; i++) {
                RouteStrategy strategy = strategies[i];
                int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER,
                        player.getX(), player.getY(), player.getPlane(),
                        player.getSize(), strategy,
                        i == (strategies.length - 1));
                if (steps == -1)
                    continue;
                if ((!RouteFinder.lastIsAlternative() && steps <= 0)) {
                    if (alternative)
                        player.getPackets().sendResetMinimapFlag();
                    event.run();
                    return true;
                }
                int[] bufferX = RouteFinder.getLastPathBufferX();
                int[] bufferY = RouteFinder.getLastPathBufferY();

                WorldTile last = new WorldTile(bufferX[0], bufferY[0],
                        player.getPlane());
                player.resetWalkSteps();
                /*player.getPackets().sendMinimapFlag(
                        last.getLocalX(player.getLastLoadedMapRegionTile(),
                                player.getMapSize()),
                        last.getLocalY(player.getLastLoadedMapRegionTile(),
                                player.getMapSize()));*/
                for (int step = steps - 1; step >= 0; step--) {
                    if (!player.addWalkSteps(bufferX[step], bufferY[step], 25,
                            true))
                        break;
                }
                return false;
            }
            player.getPackets().sendResetMinimapFlag();
            return true;
        }
    }

    private boolean simpleCheck(Player player) {
        if (object == null)
            return false;
        if (object instanceof Entity) {
            return player.getPlane() == ((Entity) object).getPlane();
        } else if (object instanceof WorldObject) {
            return player.getPlane() == ((WorldObject) object).getPlane();
        } else if (object instanceof FloorItem) {
            return player.getPlane() == ((FloorItem) object).getTile()
                    .getPlane();
        } else if (object instanceof WorldTile) {
            return player.getPlane() == ((WorldTile) object).getPlane();
        } else {
            throw new RuntimeException(object
                    + " is not instanceof any reachable entity.");
        }
    }

    private RouteStrategy[] generateStrategies() {
        if (object instanceof Entity) {//
            return new RouteStrategy[] { new EntityStrategy((Entity) object) };
        } else if (object != null) {
            return new RouteStrategy[] { new ObjectStrategy(
                    (WorldObject) object) };
        } else {
            throw new RuntimeException(object
                    + " is not instanceof any reachable entity.");
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
