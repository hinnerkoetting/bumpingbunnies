package de.jumpnbump.usecases.start.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.jumpnbump.usecases.networkRoom.RoomActivity;
import de.jumpnbump.usecases.start.communication.RemoteCommunicationImpl;
import de.jumpnbump.usecases.start.communication.SocketFactory;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter,
			RoomActivity origin) {
		SocketFactory serverSocketFactory = new BluetoothServerSocketFactory(
				btAdapter);
		RemoteCommunicationImpl communication = new RemoteCommunicationImpl(
				origin, serverSocketFactory);
		return new BluetoothCommunication(origin, btAdapter, communication);
	}
}
