package de.oetting.bumpingbunnies.model.game.objects;

public interface ModelConstants {

	int MAX_VALUE = 100000000;
	/**
	 * this constant can be used for constant values. For example movement speed
	 * or outside of game zone. All of these constants should be some
	 * multiple/divider of this constant. <br>
	 * This constant may change in another version.
	 * 
	 */
	int STANDARD_WORLD_SIZE = 100000000;

	int WALL_WIDTH = (int) (0.025 * STANDARD_WORLD_SIZE);
	int WALL_HEIGHT = (int) (0.025 * STANDARD_WORLD_SIZE);

	int PLAYER_WIDTH = (int) (0.04 * STANDARD_WORLD_SIZE);
	int PLAYER_HEIGHT = (int) (0.045 * STANDARD_WORLD_SIZE);

	int MOVEMENT_LIMIT = 1;
	// X movement
	int MAX_X_MOVEMENT = (int) (0.00004f * STANDARD_WORLD_SIZE);

	// y Movement

	int PLAYER_JUMP_SPEED = (int) (+0.000225 * STANDARD_WORLD_SIZE);
	int PLAYER_JUMP_SPEED_WATER = (int) (+0.000175 * STANDARD_WORLD_SIZE);
	int PLAYER_JUMP_SPEED_JUMPER = (int) (+0.00028 * STANDARD_WORLD_SIZE);

	// y gravitity
	int PLAYER_GRAVITY = (int) (-0.00002 * STANDARD_WORLD_SIZE) / 100;
	int PLAYER_GRAVITY_WHILE_JUMPING = (int) (-0.000008 * STANDARD_WORLD_SIZE) / 100;

	int PLAYER_GRAVITY_WATER = -PLAYER_GRAVITY_WHILE_JUMPING / 2;
	int PLAYER_SPEED_WATER = -PLAYER_JUMP_SPEED_WATER * 7;

	int ACCELERATION_X_WALL = 40;
	int ACCELERATION_X_AIR = 3;
	int ACCELERATION_X_WATER = 5;
	int ACCELERATION_X_ICE = 2;
	int ACCELERATION_X_JUMPER = 40;

}
