package de.oetting.bumpingbunnies.usecases.game.model;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createDummyPlayer;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;

public class PlayerJoinObservableTest {

	private PlayerJoinObservable observable;

	@Mock
	private PlayerJoinListener listener;

	@Test
	public void playerJoins_givenObserverWasAddedBefore_shouldNotifyListener() {
		this.observable.addListener(this.listener);
		this.observable.playerJoined(createDummyPlayer());
		thenObserverShouldBeNotifiedAboutJoin();
	}

	@Test
	public void playerLeft_givenObserverWasAddedBefore_shouldNotifyListener() {
		this.observable.addListener(this.listener);
		this.observable.playerLeft(createDummyPlayer());
		thenObserverShouldBeNotifiedAboutLeaving();
	}

	private void thenObserverShouldBeNotifiedAboutLeaving() {
		verify(this.listener).playerLeftTheGame(any(Player.class));
	}

	private void thenObserverShouldBeNotifiedAboutJoin() {
		verify(this.listener).newPlayerJoined(any(Player.class));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.observable = new PlayerJoinObservable();
	}

}
