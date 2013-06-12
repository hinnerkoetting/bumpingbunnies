package de.jumpnbump.usecases.start.communication;

import android.bluetooth.BluetoothAdapter;
import de.jumpnbump.usecases.start.AcceptThread;
import de.jumpnbump.usecases.start.StartActivity;

public class BluetoothCommunicationFactory {

	public static BluetoothCommunication create(BluetoothAdapter btAdapter,
			StartActivity origin) {
		AcceptThread acceptThread = new AcceptThread(btAdapter, origin);
		return new BluetoothCommunication(origin, acceptThread, btAdapter);
	}
}
