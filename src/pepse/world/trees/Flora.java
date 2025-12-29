package pepse.world.trees;

import pepse.utils.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

/**
 * Responsible for generating trees within the game world based on terrain height.
 */
public class Flora {
    private static final float TREE_PROBABILITY = 0.1f;

    private final int seed;
    private final Function<Float, Float> groundHeightAt;

    /**
     * Initializes the vegetation generator.
     * @param groundHeightAt Function to determine ground height at a given x-coordinate.
     * @param seed           Seed for the random number generator to ensure consistent placement.
     */
    public Flora(Function<Float, Float> groundHeightAt, int seed) {
        this.groundHeightAt = groundHeightAt;
        this.seed = seed;
    }

    /**
     * Generates a list of trees within the specified x-coordinate range.
     * @param minX The starting x-coordinate.
     * @param maxX The ending x-coordinate.
     * @return A list of generated trees.
     */
    public ArrayList<Tree> createInRange(int minX, int maxX){
        ArrayList<Tree> trees = new ArrayList<>();

        int normalizedMinX = normalize(minX);
        int normalizedMaxX = normalize(maxX);

        for (int locationX = normalizedMinX; locationX < normalizedMaxX; locationX += Constants.BLOCK_SIZE) {
            Random random = new Random(Objects.hash(locationX * 3, seed));
            if(random.nextFloat() < TREE_PROBABILITY){
                int groundHeight = (int) Math.floor(groundHeightAt.apply((float) locationX));
                trees.add(new Tree(locationX, groundHeight, random));
                locationX += 4 * Constants.BLOCK_SIZE;
            }
        }
        return trees;
    }

    private int normalize(int x) {
        return (int) Math.floor((double) x / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
    }
}