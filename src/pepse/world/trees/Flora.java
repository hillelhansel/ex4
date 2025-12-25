package pepse.world.trees;

import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

public class Flora {
    private final Function<Float, Float> groundHeightAt;

    public Flora(Function<Float, Float> groundHeightAt) {
        this.groundHeightAt = groundHeightAt;
    }

    public ArrayList<Tree> createInRange(int minX, int maxX){
        ArrayList<Tree> trees = new ArrayList<>();

        int normalizedMinX = normalize(minX);
        int normalizedMaxX = normalize(maxX);

        for (int locationX = normalizedMinX; locationX <= normalizedMaxX; locationX += Block.SIZE) {
            Random random = new Random(Objects.hash(locationX, 5));
            if(random.nextFloat() < 0.1f){
                int groundHeight = (int) Math.floor(groundHeightAt.apply((float) locationX));
                trees.add(new Tree(locationX, groundHeight));
                locationX += 3 * Block.SIZE;
            }
        }
        return trees;
    }

    private int normalize(int x) {
        return (int) Math.floor((double) x / Block.SIZE) * Block.SIZE;
    }
}
