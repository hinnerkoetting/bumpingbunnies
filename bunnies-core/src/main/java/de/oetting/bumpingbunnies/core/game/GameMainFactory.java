package de.oetting.bumpingbunnies.core.game;

import java.util.List;

import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.logic.CommonGameThreadFactory;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.CommonGameMainFactory;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControlFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world, GameStartParameter parameter, Player myPlayer,
			ThreadErrorCallback errorCallback, BunniesMusicPlayerFactory musicPlayerFactory, ConnectionEstablisherFactory connectionEstablisherFactory,
			GameStopper gameStopper) {

		RemoteConnectionFactory factory = new RemoteConnectionFactory(errorCallback);
		NetworkMessageDistributor networkMessageDistributor = new NetworkMessageDistributor(factory);
		GameMain main = CommonGameMainFactory.createGameMain(errorCallback, parameter, world, musicPlayerFactory, connectionEstablisherFactory,
				networkMessageDistributor, parameter.getConfiguration());
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher(main);
		main.setGameThread(createGameThread(cameraPositionCalculator, world, errorCallback, parameter.getConfiguration(), networkDispatcher,
				networkMessageDistributor, main, musicPlayerFactory, gameStopper));
		factory.setDisconnectCallback(main);
		main.setWorld(world);
		main.setReceiveControl(createNetworkReceiveFactory(networkDispatcher, networkMessageDistributor, parameter.getConfiguration(), errorCallback, world));
		main.validateInitialised();

		main.addAllJoinListeners();
		main.addSocketListener();
		main.newEvent(myPlayer);
		addOtherPlayers(main, parameter);
		main.start();
		return main;
	}

	private void addOtherPlayers(GameMain gameMain, GameStartParameter parameter) {
		List<PlayerConfig> players = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());
		for (PlayerConfig config : players) {
			Player otherPlayer = config.getPlayer();
			gameMain.newEvent(otherPlayer);
		}
	}

	private NetworkReceiveControl createNetworkReceiveFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor networkMessageDistributor,
			Configuration configuration, ThreadErrorCallback errorCallback, World world) {
		return NetworkReceiveControlFactory.create(networkDispatcher, networkMessageDistributor, configuration, errorCallback, world);
	}

	private GameThread createGameThread(CameraPositionCalculation cameraPositionCalculator, World world, ThreadErrorCallback errorCallback,
			Configuration configuration, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor messageDistributor, GameMain gameMain,
			BunniesMusicPlayerFactory musicPlayerFactory, GameStopper gameStopper) {
		return CommonGameThreadFactory.create(world, errorCallback, configuration, cameraPositionCalculator, networkDispatcher, messageDistributor, gameMain,
				musicPlayerFactory, gameStopper);
	}

}
