package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.networking.MessageParser;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;

@Category(UnitTests.class)
public class SimpleNetworkSenderTest {

	private SimpleNetworkSender fixture;
	private MessageParser parser;
	private MySocket socket;
	@Mock
	private OutputStream os;

	@Test
	public void sendMessage_thenMessageIsSendAsString() throws IOException {
		this.fixture.sendMessage(SimpleMessageConsts.WRAPPER);
		String msg = SimpleMessageConsts.CONVERTED_MESSAGE2;
		verify(this.os).write(byteArrayThatStartsWith(msg), eq(0), eq(msg.length() + 1));
	}

	private byte[] byteArrayThatStartsWith(final String msg) {
		return argThat(new ByteArrayStartMatcher(msg));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.parser = MessageParserFactory.create();
		this.socket = new TestSocket(this.os, null);
		this.fixture = new SimpleNetworkSender(this.parser, this.socket);
	}
}
