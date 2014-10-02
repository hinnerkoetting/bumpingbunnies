package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class BunniesBluetoothServerSocket implements ServerSocket {

	private BluetoothServerSocket socket;

	public BunniesBluetoothServerSocket(BluetoothServerSocket btSocket) {
		this.socket = btSocket;
	}

	@Override
	public MySocket accept() throws IOException {
		BluetoothSocket btSocket = this.socket.accept();
		return new MyBluetoothSocket(btSocket, Opponent.createOpponent("bluetoothserver", OpponentType.BLUETOOTH));
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}
}