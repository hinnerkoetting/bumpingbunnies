package de.oetting.bumpingbunnies.usecases.game.model;

import android.graphics.Color;
import android.media.MediaPlayer;

public class Jumper extends FixedWorldObject {

	private final MediaPlayer mediaPlayer;

	public Jumper(int id, long minX, long minY, long maxX, long maxY,
			MediaPlayer mediaPlayer) {
		super(id, minX, minY, maxX, maxY, Color.YELLOW);
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
		this.mediaPlayer.start();
		p.setMovementY(ModelConstants.PLAYER_JUMP_SPEED_JUMPER);
		p.setAccelerationY(0);
		p.simulateNextStep();
	}
}
