package de.oetting.bumpingbunnies.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter, RoomActivity origin) {
		SocketFactory serverSocketFactory = new BluetoothSocketFactory(btAdapter);
		DefaultConnectionEstablisher communication = new DefaultConnectionEstablisher(origin, serverSocketFactory);
		return new BluetoothCommunication(origin, btAdapter, communication, new BluetoothActivatation(origin));
	}

	public static ClientAccepter createClientAccepter(BluetoothAdapter btAdapter, RoomActivity origin, ThreadErrorCallback errorCallback) {
		SocketFactory serverSocketFactory = new BluetoothSocketFactory(btAdapter);
		DefaultClientAccepter communication = new DefaultClientAccepter(serverSocketFactory, origin, errorCallback);
		return new BluetoothClientsAccepter(new BluetoothActivatation(origin), origin, communication);
	}
}
