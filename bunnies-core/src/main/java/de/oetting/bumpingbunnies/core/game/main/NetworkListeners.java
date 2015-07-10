package de.oetting.bumpingbunnies.core.game.main;

import de.oetting.bumpingbunnies.core.game.steps.ScoreboardSynchronisation;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.clientReceivedDeadBunny.BunnyIsDeadMessageReceivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.pause.GamePausedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected.PlayerDisconnectedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.OtherPlayerPropertiesReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerPropertiesReceiveListener;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.world.World;

public class NetworkListeners {

	public static void allNetworkListeners(NetworkToGameDispatcher networkDispatcher, World world, ThreadErrorCallback activity, GameMain main,
			Configuration configuration, GameStopper gameStopper, MusicPlayer deadPlayerMusic, ScoreboardSynchronisation socreboardSync) {
		new StopGameReceiver(networkDispatcher, gameStopper);
		new PlayerIsDeadReceiver(networkDispatcher, world, deadPlayerMusic, SocketStorage.getSingleton(),  main, configuration.isHost());
		new PlayerScoreReceiver(networkDispatcher, world, socreboardSync);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
		new PlayerDisconnectedReceiver(networkDispatcher, main);
		new BunnyIsDeadMessageReceivedReceiver(networkDispatcher, world);
		new GamePausedReceiver(networkDispatcher, main);
		addPlayerJoinListener(networkDispatcher, main, configuration);
	}

	private static OtherPlayerPropertiesReceiver addPlayerJoinListener(NetworkToGameDispatcher networkDispatcher, GameMain main, Configuration configuration) {
		PlayerPropertiesReceiveListener playerReceiveListener = new OtherPlayerJoinsGameListener(main, configuration);
		return new OtherPlayerPropertiesReceiver(networkDispatcher, playerReceiveListener);
	}
}
