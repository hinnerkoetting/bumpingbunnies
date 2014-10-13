package de.oetting.bumpingbunnies.core.game.main;

import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected.PlayerDisconnectedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.OtherPlayerPropertiesReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerPropertiesReceiveListener;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;

public class NetworkListeners {

	public static void allNetworkListeners(NetworkToGameDispatcher networkDispatcher, World world, OnThreadErrorCallback activity, GameMain main,
			Configuration configuration) {
		new StopGameReceiver(networkDispatcher, activity);
		new PlayerIsDeadReceiver(networkDispatcher, world);
		new PlayerScoreReceiver(networkDispatcher, world);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
		new PlayerDisconnectedReceiver(networkDispatcher, main);
		addPlayerJoinListener(networkDispatcher, main, configuration);
	}

	private static OtherPlayerPropertiesReceiver addPlayerJoinListener(NetworkToGameDispatcher networkDispatcher, GameMain main, Configuration configuration) {
		PlayerPropertiesReceiveListener playerReceiveListener = new OtherPlayerJoinsGameListener(main, configuration);
		return new OtherPlayerPropertiesReceiver(networkDispatcher, playerReceiveListener);
	}
}
