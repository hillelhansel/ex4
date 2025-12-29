package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.utils.Constants;
import pepse.utils.GameObjectsTags;
import pepse.utils.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the generation and creation of the game's terrain.
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;

    /**
     * Initializes the terrain generator.
     *
     * @param windowsDimensions The dimensions of the game window.
     * @param seed              A seed value for the random noise generator.
     */
    public Terrain(Vector2 windowsDimensions, int seed) {
        this.groundHeightAtX0 =  windowsDimensions.y() * (2f / 3f);
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Calculates the ground height at a specific x-coordinate.
     * Uses noise to determine the height relative to the baseline.
     * @param x The x-coordinate to check.
     * @return The y-coordinate of the ground surface at the given x.
     */
    public float getGroundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Constants.BLOCK_SIZE * 10);
        float groundHeight = groundHeightAtX0 + noise;
        return (float) Math.floor(groundHeight / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
    }

    /**
     * Creates a list of ground blocks within a specified horizontal range.
     * Generates a column of blocks at each x-coordinate step
     * @param minX The starting x-coordinate of the range
     * @param maxX The ending x-coordinate of the range
     * @return A list of Block objects representing the terrain in the given range.
     */
    public List<Block> createInRange(int minX, int maxX){
        List<Block> blocks = new ArrayList<>();

        int normalizedMinX = normalize(minX);
        int normalizedMaxX = normalize(maxX);

        for (int x = normalizedMinX; x <= normalizedMaxX; x += Constants.BLOCK_SIZE) {
            int normalizedMinY = (int) getGroundHeightAt(x);

            for(int i = 0; i < TERRAIN_DEPTH; i++){
                Color groundColor = ColorSupplier.approximateColor(BASE_GROUND_COLOR);
                RectangleRenderable renderable = new RectangleRenderable(groundColor);
                int y = normalizedMinY + (i * Constants.BLOCK_SIZE);
                Vector2 topLeftCorner = new Vector2(x, y);

                Block block = new Block(topLeftCorner, renderable);
                block.setTag(GameObjectsTags.GROUND.toString());
                blocks.add(block);
            }
        }
        return blocks;
    }

    private int normalize(int x) {
        return (int) Math.floor((double) x / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
    }
}