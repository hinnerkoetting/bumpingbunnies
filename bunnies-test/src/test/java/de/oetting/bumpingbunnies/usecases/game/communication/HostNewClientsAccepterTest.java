package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.BroadcastService;

@Category(UnitTests.class)
public class HostNewClientsAccepterTest {

	private NewClientsAccepter fixture;
	@Mock
	private RemoteCommunication remoteCommunication;
	@Mock
	private BroadcastService broadcaster;
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
