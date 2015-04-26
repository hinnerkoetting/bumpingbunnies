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

import android.app.Activity;
import de.oetting.bumpingbunnies.communication.AndroidConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.communication.bluetooth.BluetoothClientsAccepter;
import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultClientAccepter;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class RemoteCommunicationFactoryTest {

	@Test
	public void create_forWlanPlayer_shouldreturnDefaultRemoteCommunication() {
		ClientAccepter rc = new AndroidConnectionEstablisherFactory(mock(Activity.class)).create(mock(AcceptsClientConnections.class), new ServerSettings(
				WorldConfiguration.CLASSIC, 1, NetworkType.WLAN), null);
		assertThat(rc, is(instanceOf(DefaultClientAccepter.class)));
	}

	@Test
	public void create_forBluetoothPlayer_shouldReturnBluetoothCommunication() {
		ClientAccepter rc = new AndroidConnectionEstablisherFactory(mock(Activity.class)).create(mock(AcceptsClientConnections.class), new ServerSettings(
				WorldConfiguration.CLASSIC, 1, NetworkType.BLUETOOTH), null);
		assertThat(rc, is(instanceOf(BluetoothClientsAccepter.class)));
	}

}
