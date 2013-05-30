package de.jumpnbump.usecases.game.model;

public interface ModelConstants {

	int MAX_VALUE = 1000000;
	int WALL_WIDTH = (int) (0.025 * 1000000);
	int WALL_HEIGHT = (int) (0.025 * 1000000);
	int PLAYER_WIDTH = (int) (0.04 * 1000000);
	int PLAYER_HEIGHT = (int) (0.045 * 1000000);
	int PLAYER_GRAVITY = (int) (-0.00001 * 1000000);
	int PLAYER_GRAVITY_WHILE_JUMPING = (int) (-0.000004 * 1000000);
	int PLAYER_JUMP_SPEED = (int) (+0.00150 * 1000000);
	int MOVEMENT = (int) (0.00040f * 1000000);
}
