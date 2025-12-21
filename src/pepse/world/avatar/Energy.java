package pepse.world.avatar;

import java.util.function.Consumer;

public class Energy {
    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY = 0;

    private final Consumer<Float> onEnergyChange;

    private float currentEnergy = MAX_ENERGY;

    public Energy(Consumer<Float> onEnergyChange) {
        this.onEnergyChange = onEnergyChange;
    }
    public void consumeEnergy(float energy) {
        if (currentEnergy > MIN_ENERGY) {
            currentEnergy -= energy;
            onEnergyChange.accept(currentEnergy);
        }
    }

    public void addEnergy(float energy) {
        if (currentEnergy < MAX_ENERGY) {
            currentEnergy += energy;
            onEnergyChange.accept(currentEnergy);
        }
    }

    public float getEnergy() {
        return currentEnergy;
    }
}
