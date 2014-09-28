package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.communication.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothCommunication;
import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class RemoteCommunicationFactoryTest {

	@Test
	public void create_forWlanPlayer_shouldreturnDefaultRemoteCommunication() {
		ConnectionEstablisher rc = ConnectionEstablisherFactory.create(mock(AcceptsClientConnections.class), new GeneralSettings(WorldConfiguration.CASTLE, 1,
				NetworkType.WLAN));
		assertThat(rc, is(instanceOf(ConnectionEstablisher.class)));
	}

	@Test
	public void create_forBluetoothPlayer_shouldReturnBluetoothCommunication() {
		ConnectionEstablisher rc = ConnectionEstablisherFactory.create(mock(AcceptsClientConnections.class), new GeneralSettings(WorldConfiguration.CASTLE, 1,
				NetworkType.BLUETOOTH));
		assertThat(rc, is(instanceOf(BluetoothCommunication.class)));
	}

}
