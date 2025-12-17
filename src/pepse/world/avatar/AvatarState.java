package pepse.world.avatar;

import danogl.gui.UserInputListener;

public interface AvatarState {
    void onEnter(Avatar avatar);
    void update(UserInputListener input, Avatar avatar);
    String getAnimationName(Avatar avatar);
}
