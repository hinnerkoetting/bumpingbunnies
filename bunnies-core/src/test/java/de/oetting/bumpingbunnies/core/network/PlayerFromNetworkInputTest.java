package de.oetting.bumpingbunnies.core.network;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class PlayerFromNetworkInputTest {

	private PlayerFromNetworkInput fixture;
	@Mock
	private Player player;

	@Test
	public void existsNewMessage_givenThereIsNoMessage_shouldReturnFalse() {
		assertFalse(this.fixture.existsNewMessage());
	}

	@Test
	public void sendNewMessage_thenThereShouldExistNewMessage() {
		this.fixture.sendNewMessage(createMessage());
		assertTrue(this.fixture.existsNewMessage());
	}

	@Test
	public void executeUserInput_thenThereShouldNotBeANewMessage() {
		givenThereExistsNewMessage();
		this.fixture.executeNextStep();
		assertFalse(this.fixture.existsNewMessage());
	}

	@Test
	public void executeUserInput_givenAnOldMessageComesAfterNewerMessage_ThenOnlyTheNewerStateShouldBeApplied() {
		PlayerState newPlayerState = new PlayerState(0);
		PlayerState oldPlayerState = new PlayerState(0);
		sendNewMessageWithCounter(2, newPlayerState);
		sendNewMessageWithCounter(1, oldPlayerState);
		this.fixture.executeNextStep();
		verify(this.player).applyState(newPlayerState);
	}

	private void sendNewMessageWithCounter(int counter, PlayerState state) {
		this.fixture.sendNewMessage(new PlayerStateMessage(counter, state));
	}

	private void givenThereExistsNewMessage() {
		this.fixture.sendNewMessage(createMessage());
	}

	private PlayerStateMessage createMessage() {
		return createMessageWithCounter(0);
	}

	private PlayerStateMessage createMessageWithCounter(int counter) {
		PlayerState state = new PlayerState(this.player.id());
		return new PlayerStateMessage(counter, state);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new PlayerFromNetworkInput(this.player);
	}
}
