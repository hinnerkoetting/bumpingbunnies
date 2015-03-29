package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.oetting.bumpingbunnies.core.game.TestPlayerFactory;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.BunnyMovementStep;
import de.oetting.bumpingbunnies.core.game.steps.FixPlayerPosition;
import de.oetting.bumpingbunnies.core.world.PlayerDoesNotExist;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class BunnyMovementStepTest {

	private BunnyMovementStep fixture;
	private Player movedPlayer;
	@Mock
	private BunnyKillChecker killChecker;
	@Mock
	private PlayerMovementCalculationFactory calculationFactory;

	@Test
	public void executeNextStep_shouldExecuteMovementCalculation() {
		whenExecutingNextStep();
		thenPlayerIsMoved(this.movedPlayer);
	}

	@Test
	public void executeNextStep_shouldShouldCheckForKilledPlayers() {
		whenExecutingNextStep();
		verify(this.killChecker).checkForJumpedPlayers();
	}

	@Test
	public void executeNextStep_shouldCheckForPlayersOutsideOfJumpZone() {
		whenExecutingNextStep();
		verify(this.killChecker).checkForPlayerOutsideOfGameZone();
	}

	@Test
	public void addPlayer_thenPlayerMovementShouldBeCalcuatedDuringExecutestep() {
		Player newPlayer = createOpponentPlayer();
		whenAddingNewPlayer(newPlayer);
		whenExecutingNextStep();
		thenPlayerIsMoved(newPlayer);
	}

	@Test(expected = PlayerDoesNotExist.class)
	public void removePlayer_givenPlayerDoesNotExist_shouldthrowException() {
		Player p = createOpponentPlayer();
		whenRemovingPlayer(p);
	}

	@Test
	public void playerLeft_thenPlayerMovementShouldNotBeCalculatedDuringNextStep() {
		whenRemovingPlayer(this.movedPlayer);
		whenExecutingNextStep();
		thenMovementCalculationIsNotCalled();
	}

	private void thenMovementCalculationIsNotCalled() {
		assertThat(this.movedPlayer.getCenterX(), is(equalTo(0L)));
		assertThat(this.movedPlayer.getCenterY(), is(equalTo(0L)));
	}

	private void whenRemovingPlayer(Player p) {
		this.fixture.removeEvent(p);
	}

	private void thenPlayerIsMoved(Player p) {
		// the fake player movement sets the player coordinate to this value
		assertThat(p.getCenterX(), is(equalTo(100L)));
		assertThat(p.getCenterY(), is(equalTo(100L)));
	}

	private void whenAddingNewPlayer(Player p) {
		this.fixture.newEvent(p);
	}

	private void whenExecutingNextStep() {
		this.fixture.executeNextStep(1);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		initMovementFactory();
		this.movedPlayer = TestPlayerFactory.createMyPlayer();
		this.fixture = new BunnyMovementStep(this.killChecker, this.calculationFactory, new FixPlayerPosition());
		this.fixture.newEvent(this.movedPlayer);
	}

	private void initMovementFactory() {
		when(this.calculationFactory.create(any(Player.class))).thenAnswer(new Answer<PlayerMovementCalculation>() {

			@Override
			public PlayerMovementCalculation answer(InvocationOnMock invocation) throws Throwable {
				return new FixedPositionPlayerMovementCalculation((Player) invocation.getArguments()[0]);
			}
		});
	}
}
