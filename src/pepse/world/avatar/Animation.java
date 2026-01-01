package pepse.world.avatar;

import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;

import java.util.HashMap;

/**
 * Manages the visual animations for the avatar.
 * Loads and stores the renderable assets for different avatar states
 */
public class Animation {
    private static final float ANIMATION_DELAY = 0.5f;
    private final HashMap<AnimationType, Renderable> animations;

    Animation(ImageReader imageReader) {
        this.animations = new HashMap<>();
        String[] idleAssetsPath = {"assets/idle_0.png", "assets/idle_1.png",
                "assets/idle_2.png", "assets/idle_3.png"};
        String[] runAssetsPath = {"assets/run_0.png", "assets/run_1.png",
                "assets/run_2.png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};
        String[] jumpAssetsPath= {"assets/jump_0.png", "assets/jump_1.png",
                "assets/jump_2.png", "assets/jump_3.png"};

        AnimationRenderable idleAnimation = new AnimationRenderable(idleAssetsPath,
                imageReader,
                true,
                ANIMATION_DELAY);
        AnimationRenderable runAnimation = new AnimationRenderable(runAssetsPath,
                imageReader,
                true,
                ANIMATION_DELAY);
        AnimationRenderable jumpAnimation = new AnimationRenderable(jumpAssetsPath,
                imageReader,
                true,
                ANIMATION_DELAY);

        animations.put(AnimationType.RUN, runAnimation);
        animations.put(AnimationType.IDLE, idleAnimation);
        animations.put(AnimationType.JUMP, jumpAnimation);
    }

    /**
     * Retrieves the animation renderable corresponding to the specified type.
     * @param animationType The type of animation to retrieve (e.g., RUN, IDLE).
     * @return The renderable object associated with the requested animation type.
     */
    public Renderable getAnimation(AnimationType animationType) {
        return animations.get(animationType);
    }

    /**
     * Defines the different types of animations available for the avatar.
     */
    public enum AnimationType {
        /**
         * Represents the run state.
         */
        RUN,
        /**
         * Represents the idle state.
         */
        IDLE,
        /**
         * Represents the jump state.
         */
        JUMP
    }
}