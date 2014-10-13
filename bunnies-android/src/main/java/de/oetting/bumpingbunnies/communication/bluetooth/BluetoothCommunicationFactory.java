package de.oetting.bumpingbunnies.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.OnThreadErrorCallback;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter, RoomActivity origin, OnThreadErrorCallback errorCallback) {
		SocketFactory serverSocketFactory = new BluetoothSocketFactory(btAdapter);
		DefaultConnectionEstablisher communication = new DefaultConnectionEstablisher(origin, origin, serverSocketFactory, errorCallback);
		return new BluetoothCommunication(origin, btAdapter, communication);
	}
}
