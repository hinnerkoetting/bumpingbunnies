package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.network.SearchingServerDevice;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class HostsListViewAdapter extends ArrayAdapter<Host> {

	private final ConnectToServerCallback callback;
	private final Activity activity;

	// should always be the last entry
	private final Host scanningFakeHost;

	public HostsListViewAdapter(Context context, ConnectToServerCallback callback, Activity activity) {
		super(context, -1);
		this.activity = activity;
		this.callback = callback;
		scanningFakeHost = new Host(new SearchingServerDevice(context.getResources().getString(R.string.searching)));
		super.add(scanningFakeHost);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = createOneEntryView(activity.getLayoutInflater(), parent, position);
		view.setOnClickListener(new TouchListener(getItem(position).getDevice()));
		view.setOnKeyListener(new KeyListener(getItem(position).getDevice()));
		return view;
	}

	private View createOneEntryView(LayoutInflater layoutInflater, ViewGroup parent, int position) {
		View entryView = layoutInflater.inflate(R.layout.room_host_entry, parent, false);
		fillName(position, entryView);
		fillIcon(position, entryView);
		return entryView;
	}

	private void fillIcon(int position, View entryView) {
		NetworkType networkType = getItem(position).getDevice().getNetworkType();
		findIconView(entryView).setImageDrawable(loadDrawableIcon(networkType));
	}
	
	private Drawable loadDrawableIcon(NetworkType networkType) {
		if (networkType.equals(NetworkType.WLAN)) {
			return activity.getResources().getDrawable(R.drawable.wlan_icon);
		} else if (networkType.equals(NetworkType.BLUETOOTH)) {
			return activity.getResources().getDrawable(R.drawable.bluetooth_icon);
		} else {
			return null; //necessary for searching host...
		}
		
	}

	private ImageView findIconView(View entryView) {
		return (ImageView) entryView.findViewById(R.id.room_host_type);
	}

	private void fillName(int position, View entryView) {
		TextView name = (TextView) entryView.findViewById(R.id.room_host_name);
		name.setText(getItem(position).getName());
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
		for (Host item : items)
			// do not use addAll because it does not exist on Android 10
			add(item);
		super.add(scanningFakeHost);
	}

	@Override
	public void addAll(Collection<? extends Host> collection) {
		super.remove(scanningFakeHost);
		for (Host host : collection)
			// do not use addAll because it does not exist on Android 10
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
