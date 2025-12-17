package pepse.world.avatar;

public class Energy {
    private static final int MAX_ENERGY = 100;
    private int currentEnergy = MAX_ENERGY;

    public void reduceEnergy(int energy) {
        if (currentEnergy > 0) {
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
