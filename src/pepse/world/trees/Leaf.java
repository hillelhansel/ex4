package pepse.world.trees;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final RectangleRenderable LEAF_RENDERABLE = new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR));

    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, LEAF_RENDERABLE);
        ScheduledTask scheduledTask = new ScheduledTask(this, 1.5f, false, this::windMovement);
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
                (float)Block.SIZE,
                (float)Block.SIZE - 2f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                3f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

}
