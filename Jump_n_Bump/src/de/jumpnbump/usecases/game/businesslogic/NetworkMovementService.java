package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;
import de.jumpnbump.usecases.game.network.GameNetworkReceiveThread;

public class NetworkMovementService implements MovementService {

	private final GameNetworkReceiveThread thread;
	private final Player player;

	public NetworkMovementService(GameNetworkReceiveThread thread, Player player) {
		this.thread = thread;
		this.player = player;
	}

	@Override
	public void executeUserInput() {
		PlayerState latestState = this.thread.getLatestCoordinates();
		this.player.setCenterX(latestState.getCenterX());
		this.player.setCenterY(latestState.getCenterY());
		this.player.setMovementX(latestState.getMovementX());
		this.player.setMovementY(latestState.getMovementY());
		this.player.setAccelerationX(latestState.getAccelerationX());
		this.player.setAccelerationY(latestState.getAccelerationY());
	}

}
