package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private final static int SUN_RADIUS = 100;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Renderable renderable = new OvalRenderable(Color.YELLOW);

        GameObject sun = new GameObject(Vector2.ZERO, Vector2.ONES.mult(SUN_RADIUS), renderable);
        Vector2 initialSunCenter = new Vector2(windowDimensions.x() * 0.5f, windowDimensions.y() * 0.5f);
        sun.setCenter(initialSunCenter);

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        Vector2 cycleCenter = new Vector2(windowDimensions.x() * 0.5f, windowDimensions.y() * (2f/3));
        new Transition<Float>(sun,
                (angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}
