package de.oetting.bumpingbunnies.communication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter, RoomActivity origin) {
		return new BluetoothCommunication(origin, btAdapter, new BluetoothActivatation(origin));
	}

}
