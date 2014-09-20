package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.usecases.game.configuration.TestConfigurationFactory;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class GameThreadFactoryTest {

	@Test
	public void create_shouldNotThrowException() {
		WorldObjectsParser builder = mock(WorldObjectsParser.class);
		when(builder.getAllSpawnPoints()).thenReturn(Arrays.asList(new SpawnPoint(0, 0)));
		World w = new World();
		GameThreadFactory.create(w, mock(Context.class), TestConfigurationFactory.createDummyHost(), null, new GameMain(null, new NetworkMessageDistributor(
				new RemoteConnectionFactory(mock(GameActivity.class), mock(SocketStorage.class))), new DummyNewClientsAccepter()), TestPlayerFactory
				.createOpponentPlayer(), null, null);
	}

}
