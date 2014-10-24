package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.logic.CommonGameThreadFactory;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameThreadFactory {

	public static GameThread create(World world, MusicPlayer jumpMusicPlayer, Configuration configuration, CameraPositionCalculation cameraPositionCalculator,
			GameMain main, Player myPlayer, NetworkMessageDistributor sendControl, PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback) {
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher(callback);
		return CommonGameThreadFactory.create(world, errorCallback, configuration, cameraPositionCalculator, myPlayer, networkDispatcher, sendControl, main,
				jumpMusicPlayer);
	}

}
