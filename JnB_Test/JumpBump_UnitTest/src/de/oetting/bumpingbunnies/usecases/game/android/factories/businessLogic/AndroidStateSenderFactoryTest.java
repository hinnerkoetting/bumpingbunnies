package de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createDummyPlayer;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.oetting.bumpingbunnies.communication.RemoteConnection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyStateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidStateSenderFactoryTest {

	private AndroidStateSenderFactory factory;
	private GameMain main;
	private Player myPlayer;

	@Test
	public void create_givenThereExistsRemoteConnectionForPlayer_shouldCreateNetworkStateSender() {
		Player remotePlayer = TestPlayerFactory.createDummyPlayer();
		givenThereExistsRemoteConnection(remotePlayer);
		StateSender stateSender = this.factory.create(createDummyPlayer());
		assertThat(stateSender, is(instanceOf(GameNetworkSender.class)));
	}

	private void givenThereExistsRemoteConnection(Player remotePlayer) {
		this.main.getSendThreads().add(new RemoteConnection(null, null, remotePlayer.getOpponent()));
	}

	@Test
	public void create_givenThereExistsNoRemoteConnectionForPlayer_shouldCreateDummyNetworkSender() {
		StateSender stateSender = this.factory.create(createDummyPlayer());
		assertThat(stateSender, is(instanceOf(DummyStateSender.class)));
	}

	@Before
	public void beforeEveryTest() {
		this.myPlayer = createDummyPlayer();
		this.main = new GameMain();
		this.factory = new AndroidStateSenderFactory(this.main, this.myPlayer);
	}
}
