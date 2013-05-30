package de.jumpnbump.usecases.game.model;

import android.graphics.Color;

public class Jumper extends FixedWorldObject {

	public Jumper(int id, int minX, int minY, int maxX, int maxY) {
		super(id, minX, minY, maxX, maxY, Color.YELLOW);
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
		p.setMovementY(ModelConstants.PLAYER_JUMP_SPEED_JUMPER);
		p.setAccelerationY(0);
		p.simulateNextStep();
	}

}
