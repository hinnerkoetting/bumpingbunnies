package de.oetting.bumpingbunnies.communication.wlan;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.communication.exceptions.TimeoutRuntimeException;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;

public class WlanDevice implements ServerDevice {

	private final String address;

	public WlanDevice(String address) {
		this.address = address;
	}

	@Override
	public MySocket createClientSocket() {
		FutureTask<MySocket> task = new FutureTask<MySocket>(new Callable<MySocket>() {

			@Override
			public MySocket call() throws Exception {
				String adress = WlanDevice.this.address;
				try {
					Socket socket = new Socket();
					SocketAddress address = new InetSocketAddress(adress,
							NetworkConstants.WLAN_PORT);
					return new WlanSocket(socket, address);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		try {
			return task.get(1, TimeUnit.SECONDS);
		} catch (TimeoutException te) {
			throw new TimeoutRuntimeException();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
