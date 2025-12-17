package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.utils.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private final NoiseGenerator noiseGenerator;
    private int groundHeightAtX0;

    public Terrain(Vector2 windowsDimensions, int seed) {
        this.groundHeightAtX0 = (int) (windowsDimensions.y() * (2f / 3f));
        noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
    }

    public float getGroundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * 7);
        return groundHeightAtX0 + noise;
    }

    public List<Block> createInRange(int minX, int maxX){
        List<Block> blocks = new ArrayList<>();

        int normalizedMinX = normalize(minX);
        int normalizedMaxX = normalize(maxX);

        for (int x = normalizedMinX; x <= normalizedMaxX; x += Block.SIZE) {
            int normalizedMinY = (int) (Math.floor(getGroundHeightAt(x)/ Block.SIZE) * Block.SIZE);

            for(int i = 0; i < TERRAIN_DEPTH; i++){
                RectangleRenderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                int y = normalizedMinY + (i * Block.SIZE);
                Vector2 topLeftCorner = new Vector2(x, y);

                Block block = new Block(topLeftCorner, renderable);
                block.setTag("ground");
                blocks.add(block);
            }
        }
        return blocks;
    }

    private int normalize(int x) {
        return (int) Math.floor((double) x / Block.SIZE) * Block.SIZE;
    }
}
