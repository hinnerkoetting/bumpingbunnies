package de.oetting.bumpingbunnies.usecases.networkRoom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.room.Host;

public class HostsListViewAdapter extends ArrayAdapter<Host> {

	private final ConnectToServerCallback callback;

	public HostsListViewAdapter(Context context, ConnectToServerCallback callback) {
		super(context, R.layout.room_player_entry);
		this.callback = callback;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		view.setOnTouchListener(new TouchListener(getItem(position).getDevice()));
		return view;
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
				callback.startConnectToServer(device);
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

}