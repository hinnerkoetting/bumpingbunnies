package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothAdapter;
import de.jumpnbump.usecases.networkRoom.RoomActivity;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter,
			RoomActivity origin) {
		return new BluetoothCommunication(origin, btAdapter);
	}
}
