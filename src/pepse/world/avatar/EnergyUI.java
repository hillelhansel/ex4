package pepse.world.avatar;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.utils.GameObjectsTags;

import java.awt.*;

/**
 * A user interface object that displays the avatar's current energy level.
 */
public class EnergyUI extends GameObject{
    private static final Vector2 ENERGY_UI_DIMENSIONS = new Vector2(50, 50);
    private static final Vector2 POSITION = new Vector2(20, 20);
    private final TextRenderable energyRenderable;

    /**
     * Constructs a new EnergyUI instance.
     * Initializes the text display at a fixed position on the screen with a default value of 100%.
     */
    public EnergyUI() {
        super(POSITION, ENERGY_UI_DIMENSIONS, null);
        this.energyRenderable = new TextRenderable("100%");
        energyRenderable.setColor(Color.BLACK);
        setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.renderer().setRenderable(energyRenderable);
        setTag(String.valueOf(GameObjectsTags.ENERGYUI));
    }

    /**
     * Updates the displayed energy text based on the provided value.
     * Formats the value as a percentage string
     * @param newEnergy The current energy level to display.
     */
    public void updateEnergy(Float newEnergy) {
        energyRenderable.setString(String.format("%.0f%%", newEnergy));
    }
}