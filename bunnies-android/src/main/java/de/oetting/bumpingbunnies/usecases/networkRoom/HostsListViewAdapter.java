package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.Collection;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.network.SearchingServerDevice;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.room.Host;

public class HostsListViewAdapter extends ArrayAdapter<Host> {

	private final ConnectToServerCallback callback;
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
		view.setOnTouchListener(new TouchListener(getItem(position).getDevice()));
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

	private class TouchListener implements OnTouchListener {

		private final ServerDevice device;

		public TouchListener(ServerDevice device) {
			this.device = device;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				onItemClick(device);
				v.setEnabled(false);
				break;
			case MotionEvent.ACTION_UP:
				v.performClick();
				break;
			default:
				break;
			}
			return true;
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
		super.remove(scanningFakeHost);
		super.add(object);
		super.add(scanningFakeHost);
	}

	@Override
	public void addAll(Host... items) {
		super.remove(scanningFakeHost);
		super.addAll(items);
		super.add(scanningFakeHost);
	}
	
	@Override
	public void addAll(Collection<? extends Host> collection) {
		super.remove(scanningFakeHost);
		super.addAll(collection);
		super.add(scanningFakeHost);
	}

	@Override
	public void clear() {
		super.clear();
		super.add(scanningFakeHost);
	}

}
