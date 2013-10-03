package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createDummyPlayer;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.factories.businessLogic.StateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class SendingCoordinatesStepTest {

	private SendingCoordinatesStep fixture;
	@Mock
	private StateSender stateSender;
	@Mock
	private StateSenderFactory factory;

	@Test
	public void playerJoins_shouldAddNewStateSender() {
		this.fixture.newPlayerJoined(createDummyPlayer());
		assertThat(this.fixture.getStateSender(), hasSize(1));
	}

	@Test
	public void playerLeaves_shouldRemoveStateSender() {
		Player player = createDummyPlayer();
		givenStateSenderForPlayerDoesExist(player);
		whenPlayerLeaves(player);
		assertThat(this.fixture.getStateSender(), hasSize(0));
	}

	@Test(expected = SendingCoordinatesStep.PlayerDoesNotExist.class)
	public void playerLeaves_givenPlayerDoesNotExist_shouldThrowException() {
		whenPlayerLeaves(createDummyPlayer());
	}

	private void whenPlayerLeaves(Player player) {
		this.fixture.playerLeftTheGame(player);
	}

	private void givenStateSenderForPlayerDoesExist(Player player) {
		this.fixture.newPlayerJoined(player);
		when(this.stateSender.sendsStateToPlayer(player)).thenReturn(true);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new SendingCoordinatesStep(this.factory);
		when(this.factory.create(any(Player.class))).thenReturn(this.stateSender);
	}
}
