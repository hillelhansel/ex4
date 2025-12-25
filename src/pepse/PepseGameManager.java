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
import java.util.List;

public class PepseGameManager extends GameManager {
    private final static int DAY_LENGTH = 30;


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
        List<Block> blocks = terrain.createInRange(-100, windowDimensionX + 100);
        for  (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        Flora flora = new Flora(terrain::getGroundHeightAt);
        ArrayList<Tree> forrest = flora.createInRange(-100, windowDimensionX + 100);
        for (Tree tree : forrest) {
            for (GameObject object : tree.getTrunk()){
                gameObjects().addGameObject(object, Layer.STATIC_OBJECTS);
            }

            for (GameObject object : tree.getLeafs()){
                gameObjects().addGameObject(object, Layer.BACKGROUND);
            }

            for (GameObject object : tree.getFruits()){
                gameObjects().addGameObject(object, Layer.DEFAULT);
            }
        }
        float startingPointX = windowDimensionX / 2f;
        Vector2 startingPoint = new Vector2(startingPointX, terrain.getGroundHeightAt(startingPointX));

        GameObject avatar = new Avatar(startingPoint, inputListener, imageReader, energyUI::updateEnergy);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        Vector2 offset = windowController.getWindowDimensions().mult(0.5f).subtract(startingPoint);
        setCamera(new Camera(avatar, offset,
                windowDimensions, windowDimensions));

//        if (avatar.getCenter().x() > startingPointX + 30 ||  avatar.getCenter().x() < startingPointX - 30) {
//            terrain.createInRange();
//        }
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
