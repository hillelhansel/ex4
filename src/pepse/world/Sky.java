package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;

import java.awt.*;

public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    public static GameObject create(Vector2 windowDimensions){
        RectangleRenderable rectangleRenderable = new RectangleRenderable(BASIC_SKY_COLOR);
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(GameObjectsTags.SKY.toString());
        return sky;
    }
}
