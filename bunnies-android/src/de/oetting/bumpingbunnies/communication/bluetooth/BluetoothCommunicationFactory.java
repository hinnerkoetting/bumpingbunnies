package de.oetting.bumpingbunnies.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.communication.RemoteCommunicationImpl;
import de.oetting.bumpingbunnies.communication.SocketFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter,
			RoomActivity origin) {
		SocketFactory serverSocketFactory = new BluetoothSocketFactory(
				btAdapter);
		RemoteCommunicationImpl communication = new RemoteCommunicationImpl(origin, origin,
				origin, serverSocketFactory);
		return new BluetoothCommunication(origin, btAdapter, communication);
	}
}
