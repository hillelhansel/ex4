package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;
import pepse.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;

public class Tree {
    private static final int FOLIAGE_SIZE = 8;

    private final ArrayList<GameObject> trunk;
    private final ArrayList<GameObject> leafs;
    private final ArrayList<GameObject> fruits;

    private final Random random;

    public Tree(float locationX, int groundHeight) {
        this.random = new Random(Objects.hash(locationX, 5));
        int treeHeight = random.nextInt(4) + 4;
        this.trunk = createTrunk(locationX, groundHeight, treeHeight);

        Vector2 FoliageStartingPosition = new Vector2((float) (locationX + 3.5 * Constants.BLOCK_SIZE), groundHeight - treeHeight * Constants.BLOCK_SIZE);
        this.leafs = createLeafs(FoliageStartingPosition);
        this.fruits = createFruits(FoliageStartingPosition);
    }

    public void create(BiConsumer<GameObject, Integer> addGameObjectFunc) {
        trunk.forEach(trunkBlock -> addGameObjectFunc.accept(trunkBlock, Layer.STATIC_OBJECTS));
        leafs.forEach(leaf -> addGameObjectFunc.accept(leaf, Layer.BACKGROUND));
        fruits.forEach(fruit -> addGameObjectFunc.accept(fruit, Layer.DEFAULT));
    }

    private ArrayList<GameObject> createLeafs(Vector2 FoliageStartingPosition) {
        ArrayList<GameObject> leafs = new ArrayList<>();

        for (int i =  0; i < FOLIAGE_SIZE; i++) {
            for (int j =  0; j < FOLIAGE_SIZE; j++) {
                if(random.nextFloat() < 0.5f){
                    Vector2 topLeft = FoliageStartingPosition.subtract(new Vector2(i * Constants.BLOCK_SIZE, j * Constants.BLOCK_SIZE));

                    GameObject leaf = new Leaf(topLeft);
                    leafs.add(leaf);
                }
            }
        }
        return leafs;
    }

    private ArrayList<GameObject> createFruits(Vector2 FoliageStartingPosition) {
        ArrayList<GameObject> fruits = new ArrayList<>();

        for (int i =  0; i < FOLIAGE_SIZE; i++) {
            for (int j =  0; j < FOLIAGE_SIZE; j++) {
                if(random.nextFloat() < 0.05f){
                    Vector2 topLeft = FoliageStartingPosition.subtract(new Vector2(i * Constants.BLOCK_SIZE, j * Constants.BLOCK_SIZE));

                    GameObject leafBlock = new Fruit(topLeft);
                    fruits.add(leafBlock);
                }
            }
        }
        return fruits;
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
