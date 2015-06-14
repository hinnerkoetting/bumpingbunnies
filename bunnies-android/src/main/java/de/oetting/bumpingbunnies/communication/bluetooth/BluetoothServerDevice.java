package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class BluetoothServerDevice implements ServerDevice {

	public BluetoothDevice device;

	public BluetoothServerDevice(BluetoothDevice device) {
		super();
		this.device = device;
	}

	@Override
	public MySocket createClientSocket() {
		try {
			BluetoothSocket socket = this.device.createRfcommSocketToServiceRecord(NetworkConstants.MY_UUID_3);
			return new MyBluetoothSocket(socket, ConnectionIdentifierFactory.createRemoteOpponent(device.getAddress(), OpponentType.BLUETOOTH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return device.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getName().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BluetoothServerDevice other = (BluetoothServerDevice) obj;
		if (other.getName() != null) //can happen if bluetooth when bluetooth gets disable
			return other.getName().equals(getName());
		return false;
	}
	
	@Override
	public boolean canConnectToServer() {
		return true;
	}

	@Override
	public NetworkType getNetworkType() {
		return NetworkType.BLUETOOTH;
	}

}
