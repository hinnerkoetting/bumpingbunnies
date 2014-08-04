package de.oetting.bumpingbunnies.usecases.game.model;

import java.awt.Color;

import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;

public class Jumper extends FixedWorldObject {

	private final MusicPlayer mediaPlayer;

	public Jumper(int id, long minX, long minY, long maxX, long maxY,
			MusicPlayer mediaPlayer) {
		super(id, minX, minY, maxX, maxY, Color.YELLOW.getRGB());
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
