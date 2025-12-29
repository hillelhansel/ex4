package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;

import java.awt.*;

public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        RectangleRenderable rectangleRenderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(GameObjectsTags.NIGHT.toString());
        new Transition<>(night,
                            night.renderer()::setOpaqueness,
                            0f,
                            MIDNIGHT_OPACITY,
                            Transition.CUBIC_INTERPOLATOR_FLOAT,
                            cycleLength/2,
                            Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                            null);
        return night;
    }
}
