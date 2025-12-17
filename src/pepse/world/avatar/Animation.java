package pepse.world.avatar;

import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;

import java.util.HashMap;

public class Animation {
    ImageReader imageReader;
    Renderable runAnimation = new AnimationRenderable(new Renderable[]{imageReader.readImage("src/assets/avatar", true)}, 0.5f);
    HashMap<String, Renderable> animations = new HashMap<>();

    Animation(ImageReader imageReader) {

    }

    public Renderable getAnimation(String name) {
        return animations.get(name);
    }
}
