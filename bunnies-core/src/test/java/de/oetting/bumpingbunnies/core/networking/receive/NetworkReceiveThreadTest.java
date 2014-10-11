package de.oetting.bumpingbunnies.core.networking.receive;

import static de.oetting.bumpingbunnies.core.networking.SimpleMessageConsts.CONVERTED_MESSAGE2;
import static de.oetting.bumpingbunnies.core.networking.SimpleMessageConsts.WRAPPER;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.oetting.bumpingbunnies.core.network.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.parser.GsonFactory;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class NetworkReceiveThreadTest {

	private NetworkReceiveThread fixture;
	@Mock
	private IncomingNetworkDispatcher networkDispatcher;
	private MySocket socket;
	@Mock
	private InputStream is;

	@Test
	public void readMessage_givenMessageIsIncoming_shouldConvertToObject() throws IOException {
		givenMessageIsRead(CONVERTED_MESSAGE2);
		this.fixture.oneRun();
		thenDispatcherIsCalledWithObject(WRAPPER);
	}

	private void givenMessageIsRead(final String msg) throws IOException {
		when(this.is.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(new Answer<Integer>() {

			@Override
			public Integer answer(InvocationOnMock invocation) {
				byte[] bytes = (byte[]) invocation.getArguments()[0];
				String message = msg + '\n';
				System.arraycopy(message.getBytes(), 0, bytes, 0, message.length());
				return message.length();
			}
		});
	}

	@Test(timeout = 1000)
	public void run_whenThreadIsCanceled_shouldReturn() {
		this.fixture.cancel();
		this.fixture.run();
	}

	private void thenDispatcherIsCalledWithObject(JsonWrapper wrapper) {
		verify(this.networkDispatcher).dispatchMessage(wrapper);
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.socket = new TestSocket(null, this.is);
		this.fixture = new NetworkReceiveThread(new GsonFactory().create(), this.networkDispatcher, this.socket);
	}
}
