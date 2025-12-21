package pepse.world.avatar;

import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;

import java.util.HashMap;

public class Animation {
    private final HashMap<String, Renderable> animations;

    Animation(ImageReader imageReader) {
        this.animations = new HashMap<>();
        String[] idleAssetsPath = {"assets/idle_0.png", "assets/idle_1.png",
                "assets/idle_2.png", "assets/idle_3.png"};
        String[] runAssetsPath = {"assets/run_0.png", "assets/run_1.png",
                "assets/run_2.png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};
        String[] jumpAssetsPath= {"assets/jump_0.png", "assets/jump_1.png",
                "assets/jump_2.png", "assets/jump_3.png"};

        AnimationRenderable idleAnimation = new AnimationRenderable(idleAssetsPath, imageReader, true, 0.5f);
        AnimationRenderable runAnimation = new AnimationRenderable(runAssetsPath, imageReader, true, 0.5f);
        AnimationRenderable jumpAnimation = new AnimationRenderable(jumpAssetsPath, imageReader, true, 0.5f);

        animations.put("run", runAnimation);
        animations.put("idle", idleAnimation);
        animations.put("jump", jumpAnimation);
    }

    public Renderable getAnimation(String name) {
        return animations.get(name);
    }
}
