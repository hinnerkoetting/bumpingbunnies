package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class OpponentInputFactoryImplTest {

	private OpponentInputFactoryImpl fixture;
	@Mock
	private GameMain main;
	@Mock
	private World world;
	@Mock
	private PlayerStateDispatcher dispatcher;

	@Test
	public void create_givenRemotePlayer_shouldCreateNetworkInputService() {
		givenIsRemotePlayer();
		OpponentInput inputService = this.fixture.create(TestPlayerFactory.createOpponentPlayer());
		assertThat(inputService, is(instanceOf(PlayerFromNetworkInput.class)));
	}

	@Test
	public void create_givenLocalPlayer_shouldCreateAiInput() {
		OpponentInput inputService = this.fixture.create(TestPlayerFactory.createOpponentPlayer());
		assertThat(inputService, is(instanceOf(AiInputService.class)));
	}

	@Test
	public void create_givenIsMyPlayer_shouldCreateDummyInput() {
		Player p = TestPlayerFactory.createMyPlayer();
		OpponentInput inputService = this.fixture.create(p);
		assertThat(inputService, is(instanceOf(DummyInputService.class)));
	}

	private void givenIsRemotePlayer() {
		when(this.main.existsRemoteConnection(any(Opponent.class))).thenReturn(true);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new OpponentInputFactoryImpl(this.main, this.world, this.dispatcher);
	}
}
