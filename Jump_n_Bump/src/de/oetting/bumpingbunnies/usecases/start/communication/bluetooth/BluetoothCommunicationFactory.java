package de.oetting.bumpingbunnies.usecases.start.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.start.communication.RemoteCommunicationImpl;
import de.oetting.bumpingbunnies.usecases.start.communication.SocketFactory;

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
