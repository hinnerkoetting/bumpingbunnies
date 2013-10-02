package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AllPlayerConfig {

	private final PlayerMovement myPlayerMovement;
	private final List<PlayerConfig> notControlledPlayers;

	public AllPlayerConfig(PlayerMovement tabletControlledPlayer,
			List<PlayerConfig> notControlledPlayers) {
		this.myPlayerMovement = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
	}

	public List<Player> getAllPlayers() {
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(this.myPlayerMovement.getPlayer());
		for (PlayerConfig config : this.notControlledPlayers) {
			allPlayers.add(config.getMovementController().getPlayer());
		}
		return allPlayers;
	}

	public List<OtherPlayerInputService> createOtherInputService(
			NetworkToGameDispatcher networkDispatcher) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<OtherPlayerInputService> resultReceiver = new ArrayList<OtherPlayerInputService>(
				allSockets.size());

		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		for (PlayerConfig config : this.notControlledPlayers) {
			OtherPlayerInputService is = config.createInputService();
			if (is instanceof PlayerFromNetworkInput) {
				PlayerFromNetworkInput inputservice = (PlayerFromNetworkInput) is;
				stateDispatcher.addInputService(config.getMovementController()
						.getPlayer().id(), inputservice);
			}

			resultReceiver.add(is);
		}
		return resultReceiver;
	}

}
