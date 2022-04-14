package org.newdawn.slick;

import org.newdawn.slick.geom.*;

public interface ShapeFill
{
    Color colorAt(final Shape p0, final float p1, final float p2);
    
    Vector2f getOffsetAt(final Shape p0, final float p1, final float p2);
}
