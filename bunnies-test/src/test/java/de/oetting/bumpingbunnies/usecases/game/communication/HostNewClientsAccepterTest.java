package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.server.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.tests.UnitTests;

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
		this.fixture = new HostNewClientsAccepter(this.broadcaster, this.remoteCommunication, this.world, new GeneralSettings(WorldConfiguration.CASTLE, 1,
				NetworkType.WLAN), mock(PlayerDisconnectedCallback.class));
	}
}
