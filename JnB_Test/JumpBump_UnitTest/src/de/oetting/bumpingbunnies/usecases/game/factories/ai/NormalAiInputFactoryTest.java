package de.oetting.bumpingbunnies.usecases.game.factories.ai;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NormalAiInputFactoryTest {

	private NormalAiInputFactory fixture;

	@Mock
	private World world;

	@Test
	public void create_thenInputServiceshouldNotBeNull() {
		OtherPlayerInputService service = this.fixture.create(new PlayerMovement(TestPlayerFactory.createDummyPlayer()), mock(World.class));
		assertNotNull(service);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new NormalAiInputFactory();
	}
}
