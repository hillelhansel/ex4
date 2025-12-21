package pepse.world.avatar;

import danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;

public class JumpState implements AvatarState {

    private static final float JUMP_VELOCITY = -650;
    private boolean spaceWasPressed = false;
    public static final int ONE_JUMP_ENERGY_COST = 20;
    public static final int DOUBLE_JUMP_ENERGY_COST = 50;

    @Override
    public void onEnter(Avatar avatar) {
        avatar.transform().setVelocityX(0);

//        avatar.renderer().setRenderable(
//                AnimationLibrary.jump()
//        );
    }

    @Override
    public void update(UserInputListener input, Avatar avatar) {
        boolean spacePressed = input.isKeyPressed(KeyEvent.VK_SPACE);

        if (spacePressed && !spaceWasPressed) {

            if (avatar.isOnGround() && avatar.hasEnergy(ONE_JUMP_ENERGY_COST)) {
                avatar.consumeEnergy(ONE_JUMP_ENERGY_COST);
                avatar.transform().setVelocityY(JUMP_VELOCITY);
            }
            if (avatar.hasEnergy(DOUBLE_JUMP_ENERGY_COST) && avatar.isFalling()) {
                avatar.consumeEnergy(DOUBLE_JUMP_ENERGY_COST);
                avatar.transform().setVelocityY(JUMP_VELOCITY);
            }
        }

        spaceWasPressed = spacePressed;
    }

    @Override
    public String getAnimationName(Avatar avatar) {
        return "jump";
    }
}
