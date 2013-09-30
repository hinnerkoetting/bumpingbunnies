package de.oetting.bumpingbunnies.usecases.game.android.input.network;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class PlayerFromNetworkInputTest {

	private PlayerFromNetworkInput fixture;
	private Player player;

	@Test
	public void sendNewMessage_thenThereShouldExistNewMessage() {
		this.fixture.newMessage(createMessage());
		assertTrue(this.fixture.existsNewMessage());
	}

	@Test
	public void onReceiveMessage_givenThereExistsMoreUpToDateMessage_thenOldShouldNotBeDispatched() {
	}

	private PlayerStateMessage createMessage() {
		PlayerState state = new PlayerState(this.player.id());
		return new PlayerStateMessage(0, state);
	}

	@Before
	public void beforeEveryTest() {
		this.player = new Player(0, "test", 1);
		this.fixture = new PlayerFromNetworkInput(this.player);
	}
}
