package de.jumpnbump.usecases;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

public class MyApplication extends Application {

	private BluetoothSocket socket;

	public BluetoothSocket getSocket() {
		return this.socket;
	}

	public void setSocket(BluetoothSocket socket) {
		this.socket = socket;
	}

}
