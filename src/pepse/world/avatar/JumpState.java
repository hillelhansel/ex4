package pepse.world.avatar;

import danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;

public class JumpState implements AvatarState {

    public static final float ONE_JUMP_ENERGY_COST = 20f;
    public static final float DOUBLE_JUMP_ENERGY_COST = 50f;
    private static final float JUMP_VELOCITY = -650;
    private boolean spaceWasPressed;

    @Override
    public void onEnter(Avatar avatar) {
        avatar.transform().setVelocityX(0);
        avatar.renderer().setRenderable(avatar.getAnimation(Animation.AnimationType.JUMP.toString()));
        spaceWasPressed = false;
    }

    @Override
    public void update(UserInputListener input, Avatar avatar) {
        boolean spacePressed = input.isKeyPressed(KeyEvent.VK_SPACE);

        if (spacePressed && !spaceWasPressed) {

            if (avatar.isOnGround() && avatar.hasEnergy(ONE_JUMP_ENERGY_COST)) {
                avatar.consumeEnergy(ONE_JUMP_ENERGY_COST);
                avatar.transform().setVelocityY(JUMP_VELOCITY);
            }

            else if (avatar.hasEnergy(DOUBLE_JUMP_ENERGY_COST) && avatar.isFalling()) {
                avatar.consumeEnergy(DOUBLE_JUMP_ENERGY_COST);
                avatar.transform().setVelocityY(JUMP_VELOCITY);
            }
        }

        spaceWasPressed = spacePressed;
    }
}
