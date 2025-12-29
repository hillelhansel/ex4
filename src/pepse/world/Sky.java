package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;

import java.awt.*;

/**
 * Represents the sky background in the game world.
 */
public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * Creates a new GameObject representing the sky.
     * The sky is a solid-colored rectangle fixed to the camera coordinates, ensuring
     * it always remains in the background regardless of camera movement.
     * @param windowDimensions The dimensions of the game window.
     * @return A GameObject representing the sky.
     */
    public static GameObject create(Vector2 windowDimensions){
        RectangleRenderable rectangleRenderable = new RectangleRenderable(BASIC_SKY_COLOR);
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(GameObjectsTags.SKY.toString());
        return sky;
    }
}