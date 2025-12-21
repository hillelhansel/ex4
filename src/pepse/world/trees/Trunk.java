package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Trunk extends Block {
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final RectangleRenderable TRUNK_RENDERABLE = new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR));

    public Trunk(Vector2 topLeft) {
        super(topLeft, TRUNK_RENDERABLE);
        setTag("trunk");
    }
}
