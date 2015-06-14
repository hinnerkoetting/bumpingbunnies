package de.oetting.bumpingbunnies.core.game.main;

import java.util.List;

import de.oetting.bumpingbunnies.core.configuration.MakesGameVisibleFactory;
import de.oetting.bumpingbunnies.core.configuration.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.factory.NetworksendThreadFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.world.World;

public class CommonGameMainFactory {

	public static GameMain createGameMain(ThreadErrorCallback gameStopper, GameStartParameter parameter, World world,
			BunniesMusicPlayerFactory musicPlayerFactory, NetworkMessageDistributor sendControl,
			Configuration configuration, List<MakesGameVisibleFactory> broadcasterFactories,
			List<SocketFactory> socketFactories) {
		RemoteConnectionFactory connectionFactory = new RemoteConnectionFactory(gameStopper);
		NetworkPlayerStateSenderThread networkSendThread = NetworksendThreadFactory.create(world, connectionFactory,
				gameStopper);
		GameMain main = new GameMain(SocketStorage.getSingleton(), createMusic(musicPlayerFactory,
				parameter.getConfiguration()), networkSendThread, sendControl, configuration);
		connectionFactory.setDisconnectCallback(main);
		NewClientsAccepter newClientsAccepter = createClientAccepter(parameter, world, main, gameStopper,
				broadcasterFactories, socketFactories);
		newClientsAccepter.setMain(main);
		main.setNewClientsAccepter(newClientsAccepter);
		return main;
	}

	private static MusicPlayer createMusic(BunniesMusicPlayerFactory musicPlayerFactory, Configuration configuration) {
		if (configuration.getLocalSettings().isPlayMusic())
			return musicPlayerFactory.createBackground();
		return new DummyMusicPlayer();
	}

	public static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories, List<SocketFactory> socketFactories) {
		return NewClientsAccepterFactory.create(parameter, world, callback, errorCallback, broadcasterFactories,
				socketFactories);
	}
}
