package pepse.utils;

/**
 * Holds immutable constant values used across the game for configuration and physics calculations.
 * This includes energy costs, dimensions, and world generation parameters.
 */
public class Constants {

    /** The energy consumed when performing a regular jump from the ground. */
    public static final float ONE_JUMP_ENERGY_COST = 20f;

    /** The energy consumed when performing a double jump. */
    public static final float DOUBLE_JUMP_ENERGY_COST = 50f;

    /** The energy consumed per frame while the avatar is running. */
    public static final float RUN_ENERGY_COST = 2f;

    /** The energy restored per frame while the avatar is idle. */
    public static final float IDLE_ENERGY_RESTORE = 1f;

    /** The dimension (width and height) of a single square block in pixels. */
    public static final int BLOCK_SIZE = 30;

    /** The width of a terrain chunk, determining the granularity of world generation and loading. */
    public final static int CHUNK_SIZE = BLOCK_SIZE * 3;

}