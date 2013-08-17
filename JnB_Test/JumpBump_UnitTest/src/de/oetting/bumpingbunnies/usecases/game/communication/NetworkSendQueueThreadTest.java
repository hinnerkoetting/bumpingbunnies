package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

@RunWith(RobolectricTestRunner.class)
public class NetworkSendQueueThreadTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSendQueueThreadTest.class);

	private NetworkSendQueueThread fixture;
	@Mock
	private MySocket socket;
	@Mock
	private Writer writer;
	private MessageParser parser = new MessageParser(new Gson());
	@Mock
	private GameActivity origin;

	@Test
	public void sendNextMessage_givenOneMessageInQueue_shouldWriteThisMessage() throws IOException, InterruptedException {
		addMessageToQueue();
		whenRun();
		thenMessageIsWritten("{\"id\":\"SEND_PLAYER_STATE\",\"message\":\"\\\"1\\\"\"}");
	}

	@Test
	public void runOnce_givenExceptionIsThrown_shouldNotifyGame() throws IOException, InterruptedException {
		addMessageToQueue();
		givenWriteFails();
		this.fixture.runOnce();
		thenGameIsNotified();
	}

	@Test(timeout = 1000)
	public void run_givenThreadIsCanceled_shouldReturn() {
		this.fixture.cancel();
		this.fixture.run();
	}

	private void thenGameIsNotified() {
		verify(this.origin).runOnUiThread(any(Runnable.class));
	}

	private void givenWriteFails() throws IOException {
		doThrow(new IOException()).when(this.writer).write(anyString());
	}

	private void whenRun() throws IOException, InterruptedException {
		this.fixture.sendNextMessage();
	}

	private void addMessageToQueue() {
		this.fixture.sendMessage(MessageId.SEND_PLAYER_STATE, "1");
	}

	private void thenMessageIsWritten(String message) throws IOException {
		verify(this.writer).write(message);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new NetworkSendQueueThread(this.socket, this.writer, this.parser, this.origin);
	}
}
