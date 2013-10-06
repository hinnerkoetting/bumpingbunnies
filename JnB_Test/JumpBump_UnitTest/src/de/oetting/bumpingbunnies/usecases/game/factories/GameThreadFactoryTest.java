package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.usecases.game.configuration.TestConfigurationFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

@RunWith(RobolectricTestRunner.class)
public class GameThreadFactoryTest {

	@Test
	public void create_shouldNotThrowException() {
		WorldObjectsBuilder builder = mock(WorldObjectsBuilder.class);
		when(builder.createSpawnPoints()).thenReturn(Arrays.asList(new SpawnPoint(0, 0)));
		World w = new World(builder);
		w.buildWorld(mock(Context.class));
		GameThreadFactory.create(w, mock(Context.class),
				TestConfigurationFactory.createDummyHost(),
				mock(CoordinatesCalculation.class), null, new GameMain(
						null,
						new NetworkSendControl(new RemoteConnectionFactory(mock(GameActivity.class), mock(SocketStorage.class))),
						new DummyNewClientsAccepter()),
				TestPlayerFactory.createOpponentPlayer(),
				null, null);
	}

}
