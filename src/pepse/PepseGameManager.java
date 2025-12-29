package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.EnergyUI;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PepseGameManager extends GameManager {
    private final static int DAY_LENGTH = 30;
    public final static int CHUNK_SIZE = 90;
    private final static int BUFFER = 120;

    private final Map<Integer, List<GameObject>> worldObjects = new HashMap<>();

    private Terrain terrain;
    private Flora flora;

    private float lastLeftBound;
    private float lastRightBound;

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        int windowDimensionX = (int) windowDimensions.x();

        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        GameObject night = Night.create(windowDimensions, DAY_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        EnergyUI energyUI = new EnergyUI();
        gameObjects().addGameObject(energyUI, Layer.UI);

        GameObject sun = Sun.create(windowDimensions, DAY_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND + 1);

        Terrain terrain = new Terrain(windowDimensions, 1);
        this.terrain = terrain;

        Flora flora = new Flora(terrain::getGroundHeightAt);
        this.flora = flora;

        createWorld(-BUFFER, windowDimensionX + BUFFER);
        this.lastLeftBound = -BUFFER;
        this.lastRightBound = windowDimensionX + BUFFER;

        float startingPointX = windowDimensionX / 2f;
        Vector2 startingPoint = new Vector2(startingPointX, terrain.getGroundHeightAt(startingPointX));

        GameObject avatar = new Avatar(startingPoint, inputListener, imageReader, energyUI::updateEnergy, this::infiniteWorld);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        Vector2 offset = windowController.getWindowDimensions().mult(0.5f).subtract(startingPoint);
        setCamera(new Camera(avatar, offset,
                windowDimensions, windowDimensions));
    }

    public void infiniteWorld(boolean right) {
        if (right){
            lastLeftBound += CHUNK_SIZE;
            lastRightBound += CHUNK_SIZE;
            createWorld((int) lastRightBound - CHUNK_SIZE, (int) lastRightBound);
            removeWorld((int) lastLeftBound - CHUNK_SIZE, (int) lastLeftBound);
        }

        else if (!right){
            lastLeftBound -= CHUNK_SIZE;
            lastRightBound -= CHUNK_SIZE;
            createWorld((int) lastLeftBound , (int) lastLeftBound+CHUNK_SIZE);
            removeWorld((int) lastRightBound , (int) lastRightBound+CHUNK_SIZE);
        }
    }

    private void createWorld(int minX, int maxX){
        List<Block> blocks = terrain.createInRange(minX, maxX);
        blocks.forEach(block -> {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
            addToMap(block);
        });

        ArrayList<Tree> forrest = flora.createInRange(minX, maxX);
        forrest.forEach(tree -> {
            tree.getTrunk().forEach(trunk -> {
                gameObjects().addGameObject(trunk, Layer.STATIC_OBJECTS);
                addToMap(trunk);});
            tree.getLeafs().forEach(leaf -> {
                gameObjects().addGameObject(leaf, Layer.BACKGROUND);
                addToMap(leaf);
            });
            tree.getFruits().forEach(fruit -> {
                gameObjects().addGameObject(fruit, Layer.DEFAULT);
                addToMap(fruit);
            });
        });
    }

    private void removeWorld(int minX, int maxX){
        for (int x = minX; x < maxX; x+=Block.SIZE) {
            int key = (x/Block.SIZE) * Block.SIZE;

            if (worldObjects.containsKey(key)) {
                for(GameObject gameObject : worldObjects.get(key)) {
                    gameObjects().removeGameObject(gameObject,  Layer.DEFAULT);
                    gameObjects().removeGameObject(gameObject,  Layer.STATIC_OBJECTS);
                    gameObjects().removeGameObject(gameObject,  Layer.BACKGROUND);
                }
                worldObjects.remove(key);
            }
        }
    }

    private void addToMap(GameObject gameObject){
        int position = (int) gameObject.getCenter().x();
        position = (position/Block.SIZE)*Block.SIZE;

        worldObjects.putIfAbsent(position, new ArrayList<>());
        worldObjects.get(position).add(gameObject);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
