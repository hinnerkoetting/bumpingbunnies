package de.oetting.bumpingbunnies.core.networking;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import de.oetting.bumpingbunnies.core.network.NetworkConstants;

public class FreePortFinderTest {

	private List<DatagramSocket> usedSockets = new ArrayList<DatagramSocket>();

	@Test
	public void findFreePort_noPortUsed_findsDefaultPort() {
		int freeport = whenFindingFreeport();
		assertThat(freeport, is(NetworkConstants.UDP_PORT));
	}

	@Test
	public void findFreePort_defaultPortUsed_findsNextPort() {
		givenDefaultPortIsUsed();
		int freeport = whenFindingFreeport();
		assertThat(freeport, is(NetworkConstants.UDP_PORT + 1));
	}

	@Test(expected = FreePortFinder.CouldNotFindPortException.class)
	public void findFreePort_maxNumberOfPortsAreUsed_findsNoPort() {
		given10PortsAreUsed();
		whenFindingFreeport();
	}

	private void given10PortsAreUsed() {
		for (int i = 0; i < 10; i++)
			givenPortIsUsed(NetworkConstants.UDP_PORT + i);
	}

	private void givenDefaultPortIsUsed() {
		givenPortIsUsed(NetworkConstants.UDP_PORT);
	}

	private void givenPortIsUsed(int port) {
		try {
			DatagramSocket socket = new DatagramSocket(port);
			usedSockets.add(socket);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
	}

	private int whenFindingFreeport() {
		return new FreePortFinder().findFreePort();
	}

	@After
	public void after() {
		for (DatagramSocket socket : usedSockets) {
			try {
				socket.close();
			} catch (Exception e) {
				// do not report
			}
		}
	}
}
