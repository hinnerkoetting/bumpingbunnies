package de.oetting.bumpingbunnies.core.networking.udp;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket.UdpException;
import de.oetting.bumpingbunnies.model.game.objects.OpponentTestFactory;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class UdpSocketTest {

	private UdpSocket fixture;
	@Mock
	private DatagramSocket socket;
	@Mock
	private InetAddress address;
	private int port = 12345;

	@Test
	public void sendMessage_thenDatagramIsSentViaSocket() throws IOException {
		sendMessage("message");
		verify(this.socket).send(datagramWithText("message"));
	}

	@Test(expected = UdpException.class)
	public void sendMessage_givenSocketFails_throwsUdpException() throws IOException {
		givenSocketThrowsException();
		sendMessage("message");
	}

	@Test
	public void sendMessage_shouldUsePortFromSocket() throws IOException {
		sendMessage("message");
		verify(this.socket).send(datagramWithPort(12345));
	}

	@Test
	public void sendMessage_shouldUseAdressFromSocket() throws IOException {
		sendMessage("message");
		verify(this.socket).send(datagramWithAddress(this.address));
	}

	private DatagramPacket datagramWithAddress(final InetAddress address) {
		return argThat(new BaseMatcher<DatagramPacket>() {

			@Override
			public boolean matches(Object item) {
				DatagramPacket packet = (DatagramPacket) item;
				InetAddress incomingAddress = packet.getAddress();
				return incomingAddress.equals(address);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(address.toString());
			}

		});
	}

	private DatagramPacket datagramWithPort(final int port) {
		return argThat(new BaseMatcher<DatagramPacket>() {

			@Override
			public boolean matches(Object item) {
				DatagramPacket packet = (DatagramPacket) item;
				int incomingPort = packet.getPort();
				return incomingPort == port;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(String.valueOf(port));
			}

		});
	}

	private void sendMessage(String message) {
		this.fixture.sendMessage(message);
	}

	private DatagramPacket datagramWithText(final String text) {
		return argThat(new BaseMatcher<DatagramPacket>() {

			@Override
			public boolean matches(Object item) {
				DatagramPacket packet = (DatagramPacket) item;
				String incomingMessage = new String(packet.getData());
				return text.equals(incomingMessage);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(text);
			}

		});

	}

	private void givenSocketThrowsException() throws IOException {
		doThrow(new IOException()).when(this.socket).send(any(DatagramPacket.class));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		UdpSocketSettings settings = new UdpSocketSettings(address, port, port);
		this.fixture = new UdpSocket(this.socket, OpponentTestFactory.create(), settings);
	}
}
