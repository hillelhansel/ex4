package pepse.world.avatar;

import danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;

public class JumpState implements AvatarState {

    private static final float JUMP_VELOCITY = -650;
    private boolean spaceWasPressed = false;

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

            if (avatar.isOnGround() && avatar.hasEnergy(20)) {
                avatar.consumeEnergy(20);
                avatar.transform().setVelocityY(JUMP_VELOCITY);
            }
            else if (!avatar.isOnGround() && avatar.hasEnergy(50)) {
                avatar.consumeEnergy(50);
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
