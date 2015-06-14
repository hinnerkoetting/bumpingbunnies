package de.oetting.bumpingbunnies.communication.bluetooth;

import android.app.Activity;
import de.oetting.bumpingbunnies.core.configuration.MakesGameVisibleFactory;
import de.oetting.bumpingbunnies.core.networking.server.MakesGameVisible;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class BluetoothDiscoverableFactory  implements MakesGameVisibleFactory {

	private final Activity activity;
	
	
	public BluetoothDiscoverableFactory(Activity activity) {
		this.activity = activity;
	}

	@Override
	public NetworkType forNetworkType() {
		return NetworkType.BLUETOOTH;
	}

	@Override
	public MakesGameVisible create(ThreadErrorCallback callback) {
		return new MakesBluetoothDiscoverable(new BluetoothActivation(activity), activity);
	}

}
