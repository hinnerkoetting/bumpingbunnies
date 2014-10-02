package de.oetting.bumpingbunnies.model.game.objects;

import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;

public class Jumper extends FixedWorldObject {

	private final MusicPlayer mediaPlayer;

	public Jumper(int id, long minX, long minY, long maxX, long maxY, MusicPlayer mediaPlayer) {
		super(id, minX, minY, maxX, maxY, Color.YELLOW);
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_JUMPER;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
		this.mediaPlayer.start();
		p.setMovementY(ModelConstants.PLAYER_JUMP_SPEED_JUMPER);
		p.setAccelerationY(0);
		p.simulateNextStep();
	}
}
