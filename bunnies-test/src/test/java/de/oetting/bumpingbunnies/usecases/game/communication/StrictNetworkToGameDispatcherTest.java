package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.core.network.NetworkListener;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class StrictNetworkToGameDispatcherTest {

	private NetworkToGameDispatcher fixture;
	@Mock
	private NetworkListener listener;

	@Test(expected = NetworkToGameDispatcher.NoListenerFound.class)
	public void dispatchPlayerState_givenNoListenerIsRegistered_shouldThrowException() {
		this.fixture.dispatchMessage(createPlayerStateMessage());
	}

	private JsonWrapper createPlayerStateMessage() {
		return JsonWrapper.create(MessageId.SEND_PLAYER_STATE, "");
	}

	@Test
	public void dispatchPlayerState_givenListenerIsRegistered_thenListenerShouldReceiveState() {
		this.fixture.addObserver(MessageId.SEND_PLAYER_STATE, this.listener);
		this.fixture.dispatchMessage(createPlayerStateMessage());
		verify(this.listener).newMessage(any(JsonWrapper.class));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new StrictNetworkToGameDispatcher();
	}
}
