package pepse.world.avatar;

public class Energy {
    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY = 0;

    private int currentEnergy = MAX_ENERGY;

    public void consumeEnergy(int energy) {
        if (currentEnergy > MIN_ENERGY) {
            currentEnergy -= energy;
        }
    }

    public void addEnergy(int energy) {
        if (currentEnergy < MAX_ENERGY) {
            currentEnergy += energy;
        }
    }

    public int getEnergy() {
        return currentEnergy;
    }
}
