package de.oetting.bumpingbunnies.usecases.game.model;

public interface ModelConstants {

	int MAX_VALUE = 10000000;

	int WALL_WIDTH = (int) (0.025 * MAX_VALUE);
	int WALL_HEIGHT = (int) (0.025 * MAX_VALUE);

	int PLAYER_WIDTH = (int) (0.04 * MAX_VALUE);
	int PLAYER_HEIGHT = (int) (0.045 * MAX_VALUE);

	int BLOOD_WIDTH = (int) (0.01 * MAX_VALUE);
	int BLOOD_HEIGHT = (int) (0.01 * MAX_VALUE);

	// X movement
	int MOVEMENT = (int) (0.00004f * MAX_VALUE);

	// y Movement

	int PLAYER_JUMP_SPEED = (int) (+0.00150 * MAX_VALUE);
	int PLAYER_JUMP_SPEED_JUMPER = (int) (+0.00175 * MAX_VALUE);

	// y gravitity
	int PLAYER_GRAVITY = (int) (-0.00001 * MAX_VALUE);
	int PLAYER_GRAVITY_WHILE_JUMPING = (int) (-0.000004 * MAX_VALUE);

	int ACCELERATION_X_WALL = 40;
	int ACCELERATION_X_AIR = 20;
	int ACCELERATION_X_ICE = 5;

}
