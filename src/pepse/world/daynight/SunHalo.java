package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;

import java.awt.*;

public class SunHalo {
    private static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    private final static int SUN_HALO_RADIUS = 110;

    public static GameObject create(GameObject sun){
        Renderable renderable = new OvalRenderable(SUN_HALO_COLOR);
        GameObject sunHalo = new GameObject(Vector2.ZERO, Vector2.ONES.mult(SUN_HALO_RADIUS), renderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        sunHalo.setTag(GameObjectsTags.SUNHALO.toString());
        return sunHalo;
    }
}
