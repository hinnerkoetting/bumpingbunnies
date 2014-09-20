package de.oetting.bumpingbunnies.communication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestOpponentFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;

@Category(UnitTests.class)
public class ServerConnectionTest {

	private UdpAndTcpNetworkSender fixture;
	@Mock
	private NetworkSendQueueThread tcpConnection;
	@Mock
	private NetworkSender udpConnection;

	@Test
	public void sendReliable_shouldSendMessageOverTcp() {
		this.fixture.sendMessage(MessageId.SPAWN_POINT, "1");
		thenMessageShouldBeSendOverTcp(MessageId.SPAWN_POINT, "1");
	}

	// @Ignore("wird momentan �ber tcp gesendet")
	@Test
	public void sendFast_shouldSendMessageOverUdp() {
		this.fixture.sendMessageFast(MessageId.SEND_PLAYER_STATE, "1");
		thenMessageShouldBeSendOverUdp(MessageId.SEND_PLAYER_STATE, "1");
	}

	@Test
	public void cancel_shouldCancelTcpAndUdpConnection() {
		this.fixture.cancel();
		verify(this.udpConnection).cancel();
		verify(this.tcpConnection).cancel();
	}

	@Test
	public void sendMessage_shouldSendReliableMessage() {
		JsonWrapper message = JsonWrapper.create(MessageId.SPAWN_POINT, "1");
		this.fixture.sendMessage(message);
		thenMessageShouldBeSendOverTcp(message);
	}

	@Test
	public void sendMessage2_shouldSendReliableMessage() {
		this.fixture.sendMessage(MessageId.SPAWN_POINT, "1");
		thenMessageShouldBeSendOverTcp(MessageId.SPAWN_POINT, "1");
	}

	@Test
	public void usesThisSocket_givenTcpConnectionUsesSocket_shouldReturnTrue() {
		givenTcpUsesSocket(true);
		assertTrue(this.fixture.usesThisSocket(mock(MySocket.class)));
	}

	@Test
	public void usesThisSocket_givenTcpConnectionDoesUseOthersocket_shouldReturnFalse() {
		givenTcpUsesSocket(false);
		assertFalse(this.fixture.usesThisSocket(mock(MySocket.class)));
	}

	private void givenTcpUsesSocket(boolean usesSocket) {
		when(this.tcpConnection.usesThisSocket(any(MySocket.class))).thenReturn(usesSocket);
	}

	private void thenMessageShouldBeSendOverUdp(MessageId messageId, Object message) {
		verify(this.udpConnection).sendMessage(messageId, message);
	}

	private void thenMessageShouldBeSendOverTcp(MessageId messageId, Object message) {
		verify(this.tcpConnection).sendMessage(messageId, message);
	}

	private void thenMessageShouldBeSendOverTcp(JsonWrapper json) {
		verify(this.tcpConnection).sendMessage(json);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new UdpAndTcpNetworkSender(this.tcpConnection, this.udpConnection, TestOpponentFactory.createDummyOpponent());
	}

}