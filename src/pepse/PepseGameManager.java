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
import pepse.utils.Constants;
import pepse.utils.GameObjectsTags;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.avatar.Avatar;
import pepse.world.avatar.EnergyUI;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main game manager for the Pepse game.
 */
public class PepseGameManager extends GameManager {
    private final static int DAY_LENGTH = 30;
    private final static int BUFFER = 300;
    private final static int SEED = 10;

    private final Map<Integer, List<GameObject>> worldObjects = new HashMap<>();

    private Terrain terrain;
    private Flora flora;

    private float lastLeftBound;
    private float lastRightBound;

    /**
     * Initializes the game settings and objects.
     * @param imageReader      Used to read images from the disk.
     * @param soundReader      Used to read sound files.
     * @param inputListener    Listener for user input.
     * @param windowController Controls the game window and its dimensions.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        createBackground(windowDimensions);
        createInitialWorld(windowDimensions);
        createAvatar(windowDimensions, inputListener, imageReader);
    }

    private void infiniteWorld(boolean right) {
        if (right){
            lastLeftBound += Constants.CHUNK_SIZE;
            lastRightBound += Constants.CHUNK_SIZE;
            createWorld((int) lastRightBound - Constants.CHUNK_SIZE, (int) lastRightBound);
            removeWorld((int) lastLeftBound - Constants.CHUNK_SIZE, (int) lastLeftBound);
        }

        else {
            lastLeftBound -= Constants.CHUNK_SIZE;
            lastRightBound -= Constants.CHUNK_SIZE;
            createWorld((int) lastLeftBound , (int) lastLeftBound + Constants.CHUNK_SIZE);
            removeWorld((int) lastRightBound , (int) lastRightBound + Constants.CHUNK_SIZE);
        }
    }

    private void createWorld(int minX, int maxX) {
        List<Block> blocks = terrain.createInRange(minX, maxX);
        blocks.forEach(this::addWorldObjectToGame);

        ArrayList<GameObject> vegetation = flora.createInRange(minX, maxX);
        vegetation.forEach(this::addWorldObjectToGame);
    }

    private void addWorldObjectToGame(GameObject gameObject) {
        int layer = getLayerForObject(gameObject);
        gameObjects().addGameObject(gameObject, layer);
        addToMap(gameObject);
    }

    private void removeWorld(int minX, int maxX) {
        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            int key = (int) Math.floor((double) x / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;

            if (worldObjects.containsKey(key)) {
                for (GameObject gameObject : worldObjects.get(key)) {
                    int layer = getLayerForObject(gameObject);
                    gameObjects().removeGameObject(gameObject, layer);
                }
                worldObjects.remove(key);
            }
        }
    }

    private int getLayerForObject(GameObject gameObject) {
        try {
            GameObjectsTags tag = GameObjectsTags.valueOf(gameObject.getTag());

            switch (tag) {
                case LEAF, SKY, SUN, SUNHALO:
                    return Layer.BACKGROUND;

                case TRUNK, GROUND:
                    return Layer.STATIC_OBJECTS;

                case NIGHT:
                    return Layer.FOREGROUND;

                case ENERGYUI:
                    return Layer.UI;

                case FRUIT, AVATAR:
                default:
                    return Layer.DEFAULT;
            }
        }
        catch (IllegalArgumentException e) {
            return Layer.DEFAULT;
        }
    }

    private void addToMap(GameObject gameObject){
        int x = (int) gameObject.getCenter().x();
        int key = (int) Math.floor((double) x / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
        worldObjects.putIfAbsent(key, new ArrayList<>());
        worldObjects.get(key).add(gameObject);
    }

    private void createBackground(Vector2 windowDimensions){
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, getLayerForObject(sky));

        GameObject night = Night.create(windowDimensions, DAY_LENGTH);
        gameObjects().addGameObject(night, getLayerForObject(night));

        GameObject sun = Sun.create(windowDimensions, DAY_LENGTH);
        gameObjects().addGameObject(sun, getLayerForObject(sun));

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, getLayerForObject(sunHalo));
    }

    private void createInitialWorld(Vector2 windowDimensions){
        this.terrain  = new Terrain(windowDimensions, SEED);
        this.flora = new Flora(terrain::getGroundHeightAt, SEED);

        createWorld(-BUFFER, (int) windowDimensions.x() + BUFFER);
        this.lastLeftBound = -BUFFER;
        this.lastRightBound = (int) windowDimensions.x() + BUFFER;
    }

    private void createAvatar(Vector2 windowDimensions,
                              UserInputListener inputListener,
                              ImageReader imageReader){
        float startingPointX = windowDimensions.x() / 2f;
        Vector2 startingPoint = new Vector2(startingPointX, terrain.getGroundHeightAt(startingPointX));

        EnergyUI energyUI = new EnergyUI();
        gameObjects().addGameObject(energyUI, getLayerForObject(energyUI));

        GameObject avatar = new Avatar(startingPoint,
                inputListener,
                imageReader,
                energyUI::updateEnergy,
                this::infiniteWorld);
        gameObjects().addGameObject(avatar, getLayerForObject(avatar));

        Vector2 offset = windowDimensions.mult(0.5f).subtract(startingPoint);
        setCamera(new Camera(avatar, offset, windowDimensions, windowDimensions));
    }

    /**
     * Entry point for the game application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}