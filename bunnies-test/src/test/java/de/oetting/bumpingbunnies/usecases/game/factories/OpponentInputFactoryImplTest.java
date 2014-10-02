package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.input.ai.AiInputService;
import de.oetting.bumpingbunnies.core.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.core.input.factory.OpponentInputFactoryImpl;
import de.oetting.bumpingbunnies.core.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
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
