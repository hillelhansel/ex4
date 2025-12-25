package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.utils.GameObjectsTags;
import pepse.utils.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;

    public Terrain(Vector2 windowsDimensions, int seed) {
        this.groundHeightAtX0 =  windowsDimensions.y() * (2f / 3f);
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    public float getGroundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * 10);
        float groundHeight = groundHeightAtX0 + noise;
        return (float) Math.floor(groundHeight / Block.SIZE) * Block.SIZE;
    }

    public List<Block> createInRange(int minX, int maxX){
        List<Block> blocks = new ArrayList<>();

        int normalizedMinX = normalize(minX);
        int normalizedMaxX = normalize(maxX);

        for (int x = normalizedMinX; x <= normalizedMaxX; x += Block.SIZE) {
            int normalizedMinY = (int) getGroundHeightAt(x);

            for(int i = 0; i < TERRAIN_DEPTH; i++){
                RectangleRenderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                int y = normalizedMinY + (i * Block.SIZE);
                Vector2 topLeftCorner = new Vector2(x, y);

                Block block = new Block(topLeftCorner, renderable);
                block.setTag(GameObjectsTags.GROUND.toString());
                blocks.add(block);
            }
        }
        return blocks;
    }

    private int normalize(int x) {
        return (int) Math.floor((double) x / Block.SIZE) * Block.SIZE;
    }
}
