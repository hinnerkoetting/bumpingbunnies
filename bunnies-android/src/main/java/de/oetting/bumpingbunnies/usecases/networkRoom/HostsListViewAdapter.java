package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.Collection;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.network.SearchingServerDevice;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class HostsListViewAdapter extends ArrayAdapter<Host> {

	private final ConnectToServerCallback callback;
	
	//should always be the last entry
	private final Host scanningFakeHost;

	public HostsListViewAdapter(Context context, ConnectToServerCallback callback) {
		super(context, R.layout.room_player_entry);
		this.callback = callback;
		scanningFakeHost = new Host(new SearchingServerDevice(context.getResources().getString(R.string.searching)));
		super.add(scanningFakeHost);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		view.setOnClickListener(new TouchListener(getItem(position).getDevice()));
		view.setOnKeyListener(new KeyListener(getItem(position).getDevice()));
		return view;
	}

	private class KeyListener implements OnKeyListener {

		private ServerDevice device;

		public KeyListener(ServerDevice device) {
			this.device = device;
		}

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				onItemClick(device);
				v.setEnabled(false);
				return true;
			}
			return false;
		}

	}

	private class TouchListener implements OnClickListener {

		private final ServerDevice device;

		public TouchListener(ServerDevice device) {
			this.device = device;
		}

		@Override
		public void onClick(View v) {
			onItemClick(device);
			v.setEnabled(false);
		}

	}

	public boolean contains(Host object) {
		for (int i = 0; i < getCount(); i++) {
			if (getItem(i).equals(object)) {
				return true;
			}
		}
		return false;
	}

	public void onItemClick(int position) {
		Host item = getItem(position);
		onItemClick(item.getDevice());
	}

	public void onItemClick(ServerDevice device) {
		if (device.canConnectToServer())
			callback.startConnectToServer(device);
	}

	@Override
	public void add(Host object) {
		if (!contains(object)) {
			super.remove(scanningFakeHost);
			super.add(object);
			super.add(scanningFakeHost);
		}
	}

	@Override
	public void addAll(Host... items) {
		super.remove(scanningFakeHost);
		for (Host item: items) //do not use addAll because it does not exist on Android 10
			add(item);
		super.add(scanningFakeHost);
	}

	@Override
	public void addAll(Collection<? extends Host> collection) {
		super.remove(scanningFakeHost);
		for (Host host: collection) //do not use addAll because it does not exist on Android 10
			add(host);
		super.add(scanningFakeHost);
	}

	@Override
	public void clear() {
		super.clear();
		super.add(scanningFakeHost);
	}

	public void clearBluetoothDevices() {
		for (int i = 0; i < getCount(); i++) {
			Host host = getItem(i);
			ServerDevice device = host.getDevice();
			if (device.getNetworkType().equals(NetworkType.BLUETOOTH)) {
				remove(host);
				i--;
			}
		}
	}

}
