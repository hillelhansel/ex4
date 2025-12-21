package pepse.world.trees;

import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.*;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);

    public Leaf(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, renderable);
    }
}
