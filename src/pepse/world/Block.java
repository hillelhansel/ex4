package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.Constants;

/**
 * Represents a fundamental static building block in the game world.
 */
public class Block extends GameObject {

    /**
     * Constructs a new Block instance.
     * Initializes the block with a fixed size and sets its
     * physics properties to be immovable and solid.
     * @param topLeftCorner The top-left coordinate where the block will be placed.
     * @param renderable    The visual representation of the block (e.g., color or texture).
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}