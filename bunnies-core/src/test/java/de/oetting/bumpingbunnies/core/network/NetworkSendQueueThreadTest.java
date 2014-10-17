package de.oetting.bumpingbunnies.core.network;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.ByteArrayStartMatcher;
import de.oetting.bumpingbunnies.core.networking.SimpleMessageConsts;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
public class NetworkSendQueueThreadTest {

	private NetworkSendQueueThread fixture;
	private MySocket socket;
	private MessageParser parser = MessageParserFactory.create();
	@Mock
	private ThreadErrorCallback origin;
	@Mock
	private OutputStream os;

	@Test
	public void sendNextMessage_givenOneMessageInQueue_shouldWriteThisMessage() throws IOException, InterruptedException {
		addMessageToQueue();
		whenRun();
		thenMessageIsWritten(SimpleMessageConsts.CONVERTED_MESSAGE);
	}

	@Ignore
	@Test
	public void runOnce_givenExceptionIsThrown_shouldNotifyGame() throws IOException, InterruptedException {
		addMessageToQueue();
		givenWriteFails();
		this.fixture.run();
		thenGameIsNotified();
	}

	@Test(timeout = 1000)
	public void run_givenThreadIsCanceled_shouldReturn() {
		this.fixture.cancel();
		this.fixture.run();
	}

	private void thenGameIsNotified() {
		verify(this.origin).onThreadError();
	}

	private void givenWriteFails() throws IOException {
		doThrow(new IOException()).when(this.os).write(any(byte[].class));
	}

	private void whenRun() throws IOException, InterruptedException {
		this.fixture.sendNextMessage();
	}

	private void addMessageToQueue() {
		this.fixture.sendMessage(SimpleMessageConsts.ID, SimpleMessageConsts.MSG);
	}

	private void thenMessageIsWritten(String message) throws IOException {
		verify(this.os).write(byteArrayThatStartsWith(message), eq(0), eq(message.length() + 1)); // newline
																									// character
	}

	private byte[] byteArrayThatStartsWith(final String msg) {
		return argThat(new ByteArrayStartMatcher(msg));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.socket = new TestSocket(this.os, null);
		this.fixture = new NetworkSendQueueThread(this.socket, this.parser, this.origin, mock(PlayerDisconnectedCallback.class));
	}
}
