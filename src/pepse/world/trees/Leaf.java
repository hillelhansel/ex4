package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final RectangleRenderable LEAF_RENDERABLE = new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR));

    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, LEAF_RENDERABLE);
    }
}
