package de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.communication.DividedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyStateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.factories.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidStateSenderFactoryTest {

	private AndroidStateSenderFactory factory;
	private GameMain main;
	private Player myPlayer;
	@Mock
	private SocketStorage sockets;
	@Mock
	private GameActivity activity;

	@Test
	public void create_givenThereExistsRemoteConnectionForPlayer_shouldCreateNetworkStateSender() {
		Player remotePlayer = TestPlayerFactory.createOpponentPlayer(OpponentType.WLAN);
		givenThereExistsRemoteConnection(remotePlayer);
		StateSender stateSender = this.factory.create(remotePlayer);
		assertThat(stateSender, is(instanceOf(GameNetworkSender.class)));
	}

	private void givenThereExistsRemoteConnection(Player remotePlayer) {
		this.main.getSendThreads().add(new DividedNetworkSender(null, null, remotePlayer.getOpponent()));
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
		this.main = new GameMain(mock(GameActivity.class), mock(SocketStorage.class), new NetworkSendControl(new RemoteConnectionFactory(
				this.activity, this.sockets)));
		this.factory = new AndroidStateSenderFactory(this.main, this.myPlayer);
	}
}
