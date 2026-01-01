package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

/**
 * Represents a single tree structure in the game world.
 */
public class Tree implements Iterable<GameObject>{
    private static final int FOLIAGE_SIZE = 8;
    private static final float LEAF_PROBABILITY = 0.4f;
    private static final float FRUIT_PROBABILITY = 0.05f;

    private final ArrayList<GameObject> trunk;
    private final ArrayList<GameObject> leafs;
    private final ArrayList<GameObject> fruits;

    private final Random random;

    /**
     * Constructs a new Tree instance.
     * @param locationX    The x-coordinate of the tree's base.
     * @param groundHeight The y-coordinate of the ground where the tree is planted.
     * @param random       A random number generator used for height and foliage distribution.
     */
    public Tree(float locationX, int groundHeight, Random random) {
        this.random = random;
        int treeHeight = random.nextInt(4) + 4;
        this.trunk = createTrunk(locationX, groundHeight, treeHeight);
        Vector2 foliageStartingPosition = new Vector2((float) (locationX + 3.5 * Constants.BLOCK_SIZE),
                groundHeight - treeHeight * Constants.BLOCK_SIZE);
        this.leafs = createFoliageObjects(foliageStartingPosition, LEAF_PROBABILITY, Leaf::new);
        this.fruits = createFoliageObjects(foliageStartingPosition, FRUIT_PROBABILITY, Fruit::new);
    }

    /**
     * create an iterator thar iterate over trunk array, leaf array and fruit array
     * @return the iterator
     */
    @Override
    public Iterator<GameObject> iterator() {
        ArrayList<GameObject> allParts = new ArrayList<>();

        allParts.addAll(trunk);
        allParts.addAll(leafs);
        allParts.addAll(fruits);

        return allParts.iterator();
    }

    private ArrayList<GameObject> createFoliageObjects(Vector2 startPos,
                                                       float probability,
                                                       Function<Vector2,
                                                               GameObject> objectFactory) {
        ArrayList<GameObject> objects = new ArrayList<>();

        for (int i = 0; i < FOLIAGE_SIZE; i++) {
            for (int j = 0; j < FOLIAGE_SIZE; j++) {
                if (random.nextFloat() < probability) {
                    Vector2 topLeft = startPos.subtract(new Vector2(i * Constants.BLOCK_SIZE,
                            j * Constants.BLOCK_SIZE));

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