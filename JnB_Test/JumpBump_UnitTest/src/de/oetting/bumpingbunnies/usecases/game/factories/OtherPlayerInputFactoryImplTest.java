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
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class OtherPlayerInputFactoryImplTest {

	private OtherPlayerInputFactoryImpl fixture;
	@Mock
	private GameMain main;
	@Mock
	private World world;

	@Test
	public void create_givenRemotePlayer_shouldCreateNetworkInputService() {
		givenIsRemotePlayer();
		OtherPlayerInputService inputService = this.fixture.create(TestPlayerFactory.createDummyPlayer());
		assertThat(inputService, is(instanceOf(PlayerFromNetworkInput.class)));
	}

	@Test
	public void create_givenLocalPlayer_shouldCreateAiInput() {
		OtherPlayerInputService inputService = this.fixture.create(TestPlayerFactory.createDummyPlayer());
		assertThat(inputService, is(instanceOf(AiInputService.class)));
	}

	private void givenIsRemotePlayer() {
		when(this.main.existsRemoteConnection(any(Opponent.class))).thenReturn(true);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new OtherPlayerInputFactoryImpl(this.main, this.world);
	}
}
