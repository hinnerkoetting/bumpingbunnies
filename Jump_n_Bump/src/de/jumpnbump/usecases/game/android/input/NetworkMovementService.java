package de.jumpnbump.usecases.game.android.input;

import de.jumpnbump.usecases.game.communication.NetworkReceiveThread;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class NetworkMovementService implements InputService {

	private final NetworkReceiveThread thread;
	private final Player player;

	public NetworkMovementService(NetworkReceiveThread thread, Player player) {
		this.thread = thread;
		this.player = player;
	}

	@Override
	public void executeUserInput() {
		PlayerState latestNetworkState = this.thread.getLatestCoordinates();
		PlayerState playerState = this.player.getState();
		playerState.setCenterX(latestNetworkState.getCenterX());
		playerState.setCenterY(latestNetworkState.getCenterY());
		playerState.setMovementX(latestNetworkState.getMovementX());
		playerState.setMovementY(latestNetworkState.getMovementY());
		playerState.setAccelerationX(latestNetworkState.getAccelerationX());
		playerState.setAccelerationY(latestNetworkState.getAccelerationY());
		playerState.setScore(latestNetworkState.getScore());
	}

	@Override
	public void destroy() {
		this.thread.cancel();
	}

}
