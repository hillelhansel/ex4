package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.ArrayList;

public class Tree {
    private ArrayList<GameObject> trunk;
    private ArrayList<GameObject> leafs;
    private ArrayList<GameObject> fruits;

    public Tree(float locationX, int groundHeight, int treeHeight) {
        this.trunk = createTrunk(locationX, groundHeight, treeHeight);
        this.leafs = createLeafs();
        this.fruits = createFruits();
    }

    private ArrayList<GameObject> createLeafs(float locationX, int groundHeight, int treeHeight) {

    }

    private ArrayList<GameObject> createFruits(float locationX, int groundHeight, int treeHeight) {

    }

    private ArrayList<GameObject> createTrunk(float locationX, int groundHeight, int treeHeight) {
        ArrayList<GameObject> trunk = new ArrayList<>();

        for (int i = 1; i <= treeHeight; i++) {
            float locationY = groundHeight - (i * Block.SIZE);
            Vector2 topLeft = new Vector2(locationX, locationY);

            GameObject trunkBlock = new Trunk(topLeft);
            trunkBlock.setTag("trunk");
            trunk.add(trunkBlock);
        }
        return trunk;
    }
}
