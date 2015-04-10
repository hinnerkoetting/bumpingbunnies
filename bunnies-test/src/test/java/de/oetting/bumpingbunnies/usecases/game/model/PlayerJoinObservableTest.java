package de.oetting.bumpingbunnies.usecases.game.model;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.game.player.PlayerJoinObservable;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class PlayerJoinObservableTest {

	private PlayerJoinObservable observable;

	@Mock
	private PlayerJoinListener listener;

	@Test
	public void playerJoins_givenObserverWasAddedBefore_shouldNotifyListener() {
		this.observable.addListener(this.listener);
		this.observable.playerJoined(createOpponentPlayer());
		thenObserverShouldBeNotifiedAboutJoin();
	}

	@Test
	public void playerLeft_givenObserverWasAddedBefore_shouldNotifyListener() {
		this.observable.addListener(this.listener);
		this.observable.playerLeft(createOpponentPlayer());
		thenObserverShouldBeNotifiedAboutLeaving();
	}

	private void thenObserverShouldBeNotifiedAboutLeaving() {
		verify(this.listener).removeEvent(any(Bunny.class));
	}

	private void thenObserverShouldBeNotifiedAboutJoin() {
		verify(this.listener).newEvent(any(Bunny.class));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.observable = new PlayerJoinObservable();
	}

}
