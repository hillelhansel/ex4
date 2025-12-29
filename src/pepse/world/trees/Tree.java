package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;
import pepse.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Tree {
    private static final int FOLIAGE_SIZE = 8;
    private static final float LEAF_PROBABILITY = 0.5f;
    private static final float FRUIT_PROBABILITY = 0.05f;

    private final ArrayList<GameObject> trunk;
    private final ArrayList<GameObject> leafs;
    private final ArrayList<GameObject> fruits;

    private final Random random;

    public Tree(float locationX, int groundHeight) {
        this.random = new Random(Objects.hash(locationX, 5));
        int treeHeight = random.nextInt(4) + 4;
        this.trunk = createTrunk(locationX, groundHeight, treeHeight);

        Vector2 foliageStartingPosition = new Vector2((float) (locationX + 3.5 * Constants.BLOCK_SIZE), groundHeight - treeHeight * Constants.BLOCK_SIZE);
        this.leafs = createFoliageObjects(foliageStartingPosition, LEAF_PROBABILITY, Leaf::new);
        this.fruits = createFoliageObjects(foliageStartingPosition, FRUIT_PROBABILITY, Fruit::new);
    }

    public void create(BiConsumer<GameObject, Integer> addGameObjectFunc) {
        trunk.forEach(trunkBlock -> addGameObjectFunc.accept(trunkBlock, Layer.STATIC_OBJECTS));
        leafs.forEach(leaf -> addGameObjectFunc.accept(leaf, Layer.BACKGROUND));
        fruits.forEach(fruit -> addGameObjectFunc.accept(fruit, Layer.DEFAULT));
    }

    private ArrayList<GameObject> createFoliageObjects(Vector2 startPos,
                                                       float probability,
                                                       Function<Vector2,
                                                       GameObject> objectFactory) {
        ArrayList<GameObject> objects = new ArrayList<>();

        for (int i = 0; i < FOLIAGE_SIZE; i++) {
            for (int j = 0; j < FOLIAGE_SIZE; j++) {
                if (random.nextFloat() < probability) {
                    Vector2 topLeft = startPos.subtract(new Vector2(i * Constants.BLOCK_SIZE, j * Constants.BLOCK_SIZE));

                    GameObject obj = objectFactory.apply(topLeft);
                    objects.add(obj);
                }
            }
        }
        return objects;
    }

    private ArrayList<GameObject> createTrunk(float locationX, int groundHeight, int treeHeight) {
        ArrayList<GameObject> trunk = new ArrayList<>();

        for (int i = 1; i <= treeHeight; i++) {
            float locationY = groundHeight - (i * Constants.BLOCK_SIZE);
            Vector2 topLeft = new Vector2(locationX, locationY);

            GameObject trunkBlock = new Trunk(topLeft);
            trunk.add(trunkBlock);
        }
        return trunk;
    }
}
