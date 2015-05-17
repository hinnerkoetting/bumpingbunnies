package de.oetting.bumpingbunnies.core.networking.receive;

import static org.mockito.Mockito.mock;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.oetting.bumpingbunnies.core.network.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.parser.GsonFactory;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class NetworkReceiveThreadTest {

	private NetworkReceiveThread fixture;
	@Mock
	private IncomingNetworkDispatcher networkDispatcher;
	private MySocket socket;
	@Mock
	private InputStream is;

	@Test(timeout = 1000)
	public void run_whenThreadIsCanceled_shouldReturn() {
		this.fixture.cancel();
		this.fixture.run();
	}
	
	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.socket = new TestSocket(null, this.is);
		this.fixture = new NetworkReceiveThread(new GsonFactory().create(), this.networkDispatcher, this.socket, mock(ThreadErrorCallback.class));
	}
}
