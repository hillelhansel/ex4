package pepse.world.trees;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.utils.Constants;
import pepse.utils.GameObjectsTags;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

/**
 * Represents a single leaf block in a tree.
 */
class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);

    /**
     * Constructs a new Leaf block.
     * Initializes the leaf with a color variation and schedules a task to start
     * the wind animation after a random delay.
     * @param topLeftCorner The top-left coordinate where the leaf will be placed.
     */
    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, null);
        Color leafColor = ColorSupplier.approximateColor(LEAF_COLOR);

        RectangleRenderable leafRenderable = new RectangleRenderable(leafColor);
        renderer().setRenderable(leafRenderable);
        setTag(GameObjectsTags.LEAF.toString());

        Random random = new Random();
        float waitTime = random.nextFloat(1.5f);
        ScheduledTask scheduledTask = new ScheduledTask(this,
                waitTime,
                false,
                this::windMovement);
    }

    private void windMovement(){
        new Transition<>(this,
                renderer()::setRenderableAngle,
                -7f,
                7f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                2f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        new Transition<>(
                this,
                (Float val) -> this.setDimensions(new Vector2(val, val)),
                (float) Constants.BLOCK_SIZE,
                (float)Constants.BLOCK_SIZE - 2f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                3f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}