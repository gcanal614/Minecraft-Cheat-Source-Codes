package org.newdawn.slick.util.pathfinding.heuristics;

import org.newdawn.slick.util.pathfinding.*;

public class ClosestHeuristic implements AStarHeuristic
{
    @Override
    public float getCost(final TileBasedMap map, final Mover mover, final int x, final int y, final int tx, final int ty) {
        final float dx = (float)(tx - x);
        final float dy = (float)(ty - y);
        final float result = (float)Math.sqrt(dx * dx + dy * dy);
        return result;
    }
}
