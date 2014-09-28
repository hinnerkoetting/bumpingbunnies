package de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.communication.AndroidStateSenderFactory;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.DummyStateSender;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.StateSender;
import de.oetting.bumpingbunnies.core.networking.messaging.UdpAndTcpNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.GameNetworkSender;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class AndroidStateSenderFactoryTest {

	private AndroidStateSenderFactory factory;
	private NetworkMessageDistributor sendControl;
	private Player myPlayer;
	@Mock
	private SocketStorage sockets;
	@Mock
	private GameActivity activity;
	private List<NetworkSender> sendThreads;

	@Test
	public void create_givenThereExistsRemoteConnectionForPlayer_shouldCreateNetworkStateSender() {
		Player remotePlayer = TestPlayerFactory.createOpponentPlayer(OpponentType.WLAN);
		givenThereExistsRemoteConnection(remotePlayer);
		StateSender stateSender = this.factory.create(remotePlayer);
		assertThat(stateSender, is(instanceOf(GameNetworkSender.class)));
	}

	private void givenThereExistsRemoteConnection(Player remotePlayer) {
		this.sendThreads.add(new UdpAndTcpNetworkSender(null, null, remotePlayer.getOpponent()));
	}

	@Test
	public void create_givenThereExistsNoRemoteConnectionForPlayer_shouldCreateDummyNetworkSender() {
		StateSender stateSender = this.factory.create(TestPlayerFactory.createOpponentPlayer(OpponentType.AI));
		assertThat(stateSender, is(instanceOf(DummyStateSender.class)));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.myPlayer = createOpponentPlayer();
		this.sendThreads = new LinkedList<>();
		this.sendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(this.activity, this.sockets), this.sendThreads);
		this.factory = new AndroidStateSenderFactory(this.sendControl, this.myPlayer);
	}
}
