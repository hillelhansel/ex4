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
import pepse.utils.NoiseGenerator;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.avatar.Avatar;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.util.List;

public class PepseGameManager extends GameManager {
    private final static int DAY_LENGTH = 30;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 1);
        List<Block> blocks = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        for  (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        GameObject night = Night.create(windowController.getWindowDimensions(), DAY_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        GameObject sun = Sun.create(windowController.getWindowDimensions(), DAY_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND + 1);

        GameObject avatar = new Avatar(new Vector2(windowController.getWindowDimensions().x()/2, terrain.getGroundHeightAt(windowController.getWindowDimensions().x()/2)), inputListener, imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        setCamera(new Camera(avatar, Vector2.ZERO,
                windowController.getWindowDimensions(), windowController.getWindowDimensions()));
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
