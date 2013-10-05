package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OpponentInputFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OpponentInput;

public class UserInputStepTest {

	private UserInputStep fixture;
	private List<OpponentInput> inputServices = new LinkedList<OpponentInput>();
	@Mock
	private OpponentInputFactory factory;

	@Test
	public void playerJoins_shouldAddNewInputService() {
		whenPlayerJoins();
		thenThereShouldOneInputService();
	}

	private void thenThereShouldOneInputService() {
		assertThat(this.inputServices, hasSize(1));

	}

	private void whenPlayerJoins() {
		this.fixture.newPlayerJoined(TestPlayerFactory.createOpponentPlayer());
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new UserInputStep(this.inputServices, this.factory);
	}
}
