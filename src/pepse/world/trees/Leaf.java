package pepse.world.trees;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);

    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, null);

        RectangleRenderable leafRenderable = new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR));
        renderer().setRenderable(leafRenderable);
        setTag("leaf");

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
                (float)Block.SIZE,
                (float)Block.SIZE - 2f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                3f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

}
