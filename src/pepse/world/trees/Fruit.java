package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Fruit extends GameObject {
    private final static int FRUIT_RADIUS = 30;
    private static final OvalRenderable renderable = new OvalRenderable(Color.RED);

    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_RADIUS), renderable);
    }
}
