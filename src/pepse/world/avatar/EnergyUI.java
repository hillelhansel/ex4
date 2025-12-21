package pepse.world.avatar;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class EnergyUI extends GameObject{
    private static final Vector2 ENERGY_UI_DIMENSIONS = new Vector2(50, 50);
    private static final Vector2 POSITION = new Vector2(20, 20);
    private final TextRenderable energyRenderable;

    public EnergyUI() {
        super(POSITION, ENERGY_UI_DIMENSIONS, null);
        this.energyRenderable = new TextRenderable("100%");
        energyRenderable.setColor(Color.BLACK);
        setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.renderer().setRenderable(energyRenderable);
    }

    public void updateEnergy(Float newEnergy) {
        energyRenderable.setString(String.format("%.0f%%", newEnergy));
    }
}
