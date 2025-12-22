package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.*;

public class Fruit extends GameObject {
    private final static int FRUIT_RADIUS = Block.SIZE;
    private static final OvalRenderable renderable = new OvalRenderable(Color.RED);
    private boolean isEaten = false;

    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_RADIUS), renderable);
        setTag("fruit");
    }

    public boolean eat(){
        if(!isEaten){
            renderer().setOpaqueness(0f);
            new ScheduledTask(this, 30f, false, () -> respawn());
            isEaten = true;
            return true;
        }
        return false;
    }

    private void respawn(){
        isEaten = false;
        renderer().setOpaqueness(1f);
    }


}
