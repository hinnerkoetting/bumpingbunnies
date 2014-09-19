package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.networking.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

@Category(UnitTests.class)
public class OpponentInputFactoryImplTest {

	private OpponentInputFactoryImpl fixture;
	@Mock
	private World world;
	@Mock
	private PlayerStateDispatcher dispatcher;

	@Test
	public void create_givenRemotePlayer_shouldCreateNetworkInputService() {
		OpponentInput inputService = this.fixture.create(TestPlayerFactory.createOpponentPlayer(OpponentType.WLAN));
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

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new OpponentInputFactoryImpl(this.world, this.dispatcher);
	}
}
