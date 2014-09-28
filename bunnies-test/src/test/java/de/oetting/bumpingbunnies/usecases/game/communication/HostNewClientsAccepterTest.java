package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.server.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;

@Category(UnitTests.class)
public class HostNewClientsAccepterTest {

	private NewClientsAccepter fixture;
	@Mock
	private ConnectionEstablisher remoteCommunication;
	@Mock
	private NetworkBroadcaster broadcaster;
	@Mock
	private World world;

	@Test
	public void test() {
		this.fixture.clientConnectedSucessfull(new TestSocket());
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new HostNewClientsAccepter(this.broadcaster, this.remoteCommunication, this.world, new GeneralSettings(
				WorldConfiguration.CASTLE, 1, NetworkType.WLAN));
	}
}
