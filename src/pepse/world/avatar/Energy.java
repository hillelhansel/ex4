package pepse.world.avatar;

import java.util.function.Consumer;

/**
 * Manages the logic for the avatar's energy system.
 */
public class Energy {
    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY = 0;

    private final Consumer<Float> onEnergyChange;

    private float currentEnergy = MAX_ENERGY;

    /**
     * Initializes the energy manager.
     * @param onEnergyChange A callback to be invoked whenever the energy level changes,
     * typically used to update the UI.
     */
    public Energy(Consumer<Float> onEnergyChange) {
        this.onEnergyChange = onEnergyChange;
    }

    /**
     * Decreases the current energy level by the specified amount.
     * @param energy The amount of energy to consume.
     */
    public void consumeEnergy(float energy) {
        if (currentEnergy > MIN_ENERGY) {
            currentEnergy -= energy;
            onEnergyChange.accept(currentEnergy);
        }
    }

    /**
     * Increases the current energy level by the specified amount, up to the maximum limit.
     * @param energy The amount of energy to restore.
     */
    public void addEnergy(float energy) {
        if (currentEnergy < MAX_ENERGY) {
            currentEnergy = Math.min(currentEnergy + energy, MAX_ENERGY);
            onEnergyChange.accept(currentEnergy);
        }
    }

    /**
     * Checks if there is enough energy available for a specific action.
     * @param energy The required amount of energy.
     * @return True if the current energy is greater than or equal to the required amount.
     */
    public boolean hasEnoughEnergy(float energy) {
        return energy <= currentEnergy;
    }
}