package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

@Category(UnitTests.class)
public class PlayerStateDispatcherTest {

	private PlayerStateDispatcher fixture;
	@Mock
	private NetworkToGameDispatcher dispatcher;
	@Mock
	private PlayerFromNetworkInput inputService;

	@Ignore("disabled because it does not work")
	@Test(expected = PlayerStateDispatcher.InputserviceDoesNotExist.class)
	public void onReceiveMessage_givenPlayerIsNotRegistered_shouldthrowException() {
		whenMessageIsReceivedForPlayer(0);
	}

	@Test
	public void onReceiveMessage_givenPlayerIsRegistered_shouldDispatchMessageToPlayer() {
		givenPlayerIsRegistered(1);
		whenMessageIsReceivedForPlayer(1);
		thenOneMessageShouldBeDispatchedToInputService();
	}

	private void thenOneMessageShouldBeDispatchedToInputService() {
		verify(this.inputService).sendNewMessage(any(PlayerStateMessage.class));
	}

	private void whenMessageIsReceivedForPlayer(int playerId) {
		this.fixture.onReceiveMessage(createMessageForPlayer(playerId));
	}

	private void givenPlayerIsRegistered(int playerId) {
		this.fixture.addInputService(playerId, this.inputService);
	}

	private PlayerStateMessage createMessageForPlayer(int playerId) {
		PlayerState state = new PlayerState(playerId);
		return new PlayerStateMessage(0, state);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new PlayerStateDispatcher(this.dispatcher);
	}
}
