package pepse.world.trees;

import danogl.GameObject;
import pepse.utils.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

/**
 * Responsible for generating trees within the game world based on terrain height.
 */
public class Flora {
    private final Random random;
    private final Function<Float, Float> groundHeightAt;

    /**
     * Initializes the vegetation generator.
     * @param groundHeightAt Function to determine ground height at a given x-coordinate.
     * @param seed           Seed for the random number generator to ensure consistent placement.
     */
    public Flora(Function<Float, Float> groundHeightAt, int seed) {
        this.groundHeightAt = groundHeightAt;
        this.random = new Random(seed);
    }

    /**
     * Generates a list of trees within the specified x-coordinate range.
     * @param minX The starting x-coordinate.
     * @param maxX The ending x-coordinate.
     * @return A list of generated trees.
     */
    public ArrayList<GameObject> createInRange(int minX, int maxX){
        ArrayList<GameObject> vegetation = new ArrayList<>();

        int normalizedMinX = normalize(minX);
        int normalizedMaxX = normalize(maxX);

        for (int x = normalizedMinX; x <= normalizedMaxX; x += Constants.BLOCK_SIZE) {
            if (random.nextInt(10) == 0) {
                float groundHeight = groundHeightAt.apply((float) x);

                Tree tree = new Tree(x, (int) groundHeight, random);
                for (GameObject part : tree) {
                    vegetation.add(part);
                }
            }
        }
        return vegetation;
    }

    private int normalize(int x) {
        return (int) Math.floor((double) x / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
    }
}