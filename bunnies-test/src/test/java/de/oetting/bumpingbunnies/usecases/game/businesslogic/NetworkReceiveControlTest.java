package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

@Category(UnitTests.class)
public class NetworkReceiveControlTest {

	private NetworkReceiveControl fixture;
	private List<NetworkReceiver> receiveThreads = new LinkedList<>();
	@Mock
	private NetworkReceiveThread thread1;
	@Mock
	private NetworkReceiveThread thread2;
	@Mock
	private NetworkReceiveThreadFactory factory;

	@Test
	public void shutdown_shouldShutdownAllThreads() {
		givenTwoThreadsExist();
		whenThreadsShouldBeShutDown();
		thenAllThreadsShouldGetShutDown();
	}

	@Test
	public void playerJoins_givenFactoryCreatesOneThread_thenThereShouldBeOneReceiveThreadAfterwards() {
		Player player = TestPlayerFactory.createMyPlayer();
		when(this.factory.create(player)).thenReturn(Arrays.asList(mock(NetworkReceiveThread.class)));
		whenPlayerJoins(player);
		assertThat(this.receiveThreads, hasSize(1));
	}

	@Test
	public void playerLeaves_givenOneReceiveThreadBelongsToThisPlayer_shouldRemoveThisThread() {
		Player player = TestPlayerFactory.createMyPlayer();
		givenThereExistsOneReceiveThreadForPlayer(player);
		assertThat(this.receiveThreads, hasSize(1));
		whenPlayerLeaves(player);
		assertThat(this.receiveThreads, hasSize(0));
	}

	private void givenThereExistsOneReceiveThreadForPlayer(Player player) {
		NetworkReceiveThread nrt = mock(NetworkReceiveThread.class);
		when(nrt.belongsToPlayer(player)).thenReturn(true);
		this.receiveThreads.add(nrt);

	}

	@Test
	public void playerLeaves_givenNoReceiveThreadBelongsToPlayer_shouldNotRemoveAnyThread() {
		givenThereExistsOneReceiveThread();
		assertThat(this.receiveThreads, hasSize(1));
		whenPlayerLeaves(TestPlayerFactory.createMyPlayer());
		assertThat(this.receiveThreads, hasSize(1));
	}

	@Test
	public void playerJoins_shouldStartNewThread() {
		Player player = TestPlayerFactory.createMyPlayer();
		NetworkReceiveThread thread = mock(NetworkReceiveThread.class);
		when(this.factory.create(player)).thenReturn(Arrays.asList(thread));
		whenPlayerJoins(player);
		verify(thread).start();
	}

	private void whenPlayerLeaves(Player player) {
		this.fixture.playerLeftTheGame(player);
	}

	private void givenThereExistsOneReceiveThread() {
		this.receiveThreads.add(mock(NetworkReceiveThread.class));
	}

	private void whenPlayerJoins(Player player) {
		this.fixture.newPlayerJoined(player);
	}

	private void thenAllThreadsShouldGetShutDown() {
		verify(this.thread1).cancel();
		verify(this.thread2).cancel();
	}

	private void whenThreadsShouldBeShutDown() {
		this.fixture.shutDownThreads();
	}

	private void givenTwoThreadsExist() {
		this.receiveThreads.addAll(Arrays.asList(this.thread1, this.thread2));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new NetworkReceiveControl(this.factory, this.receiveThreads);
	}
}
