package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;
import pepse.world.Block;

import java.awt.*;

/**
 * Represents the trunk segment of a tree.
 */
class Trunk extends Block {
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final RectangleRenderable TRUNK_RENDERABLE = new RectangleRenderable(TRUNK_COLOR);

    /**
     * Constructs a new Trunk block instance.
     * @param topLeft The top-left corner coordinates for the block.
     */
    public Trunk(Vector2 topLeft) {
        super(topLeft, TRUNK_RENDERABLE);
        setTag(GameObjectsTags.TRUNK.toString());
    }
}