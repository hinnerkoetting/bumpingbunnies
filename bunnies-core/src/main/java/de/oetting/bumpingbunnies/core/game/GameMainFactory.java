package de.oetting.bumpingbunnies.core.game;

import java.util.List;

import de.oetting.bumpingbunnies.core.configuration.MakesGameVisibleFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.logic.CommonGameThreadFactory;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.CommonGameMainFactory;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.steps.ScoreboardSynchronisation;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControlFactory;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world,
			GameStartParameter parameter, Bunny myPlayer, ThreadErrorCallback errorCallback,
			BunniesMusicPlayerFactory musicPlayerFactory, GameStopper gameStopper,
			ScoreboardSynchronisation scoreSynchronisation, List<MakesGameVisibleFactory> broadcasterFactories,
			List<SocketFactory> socketFactories) {

		RemoteConnectionFactory factory = new RemoteConnectionFactory(errorCallback);
		NetworkMessageDistributor networkMessageDistributor = new NetworkMessageDistributor(factory);
		GameMain main = CommonGameMainFactory.createGameMain(errorCallback, parameter, world, musicPlayerFactory,
				networkMessageDistributor, parameter.getConfiguration(), broadcasterFactories, socketFactories);
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher(main);
		main.setGameThread(createGameThread(cameraPositionCalculator, world, errorCallback,
				parameter.getConfiguration(), networkDispatcher, networkMessageDistributor, main, musicPlayerFactory,
				gameStopper, scoreSynchronisation));
		factory.setDisconnectCallback(main);
		main.setWorld(world);
		main.setReceiveControl(createNetworkReceiveFactory(networkDispatcher, networkMessageDistributor,
				parameter.getConfiguration(), errorCallback, world));
		main.validateInitialised();

		main.addAllJoinListeners();
		main.addSocketListener();
		main.newEvent(myPlayer);
		addOtherPlayers(main, parameter);
		main.pause(parameter.getConfiguration().getGeneralSettings().isGameIsCurrentlyPaused());
		main.start();
		return main;
	}

	private void addOtherPlayers(GameMain gameMain, GameStartParameter parameter) {
		List<PlayerConfig> players = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());
		for (PlayerConfig config : players) {
			Bunny otherPlayer = config.getPlayer();
			gameMain.newEvent(otherPlayer);
		}
	}

	private NetworkReceiveControl createNetworkReceiveFactory(NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor networkMessageDistributor, Configuration configuration,
			ThreadErrorCallback errorCallback, World world) {
		return NetworkReceiveControlFactory.create(networkDispatcher, networkMessageDistributor, configuration,
				errorCallback, world);
	}

	private GameThread createGameThread(CameraPositionCalculation cameraPositionCalculator, World world,
			ThreadErrorCallback errorCallback, Configuration configuration, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor messageDistributor, GameMain gameMain,
			BunniesMusicPlayerFactory musicPlayerFactory, GameStopper gameStopper,
			ScoreboardSynchronisation scoreSynchronisation) {
		return CommonGameThreadFactory.create(world, errorCallback, configuration, cameraPositionCalculator,
				networkDispatcher, messageDistributor, gameMain, musicPlayerFactory, gameStopper, scoreSynchronisation);
	}

}
