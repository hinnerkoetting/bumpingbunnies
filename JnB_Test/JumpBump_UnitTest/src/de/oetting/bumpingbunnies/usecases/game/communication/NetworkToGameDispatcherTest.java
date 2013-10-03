package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

@RunWith(RobolectricTestRunner.class)
public class NetworkToGameDispatcherTest {

	private NetworkToGameDispatcher fixture;
	@Mock
	private NetworkListener listener;

	@Test(expected = NetworkToGameDispatcher.NoListenerFound.class)
	public void dispatchPlayerState_givenNoListenerIsRegistered_shouldThrowException() {
		this.fixture.dispatchPlayerState(createPlayerStateMessage());
	}

	private JsonWrapper createPlayerStateMessage() {
		return JsonWrapper.create(MessageId.SEND_PLAYER_STATE, "");
	}

	@Test
	public void dispatchPlayerState_givenListenerIsRegistered_thenListenerShouldReceiveState() {
		this.fixture.addObserver(MessageId.SEND_PLAYER_STATE, this.listener);
		this.fixture.dispatchPlayerState(createPlayerStateMessage());
		verify(this.listener).newMessage(any(JsonWrapper.class));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new NetworkToGameDispatcher();
	}
}
