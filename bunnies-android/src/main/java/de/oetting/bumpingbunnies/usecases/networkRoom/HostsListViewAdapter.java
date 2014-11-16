package de.oetting.bumpingbunnies.usecases.networkRoom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.network.room.Host;

public class HostsListViewAdapter extends ArrayAdapter<Host> {

	private final ConnectToServerCallback callback;

	public HostsListViewAdapter(Context context, ConnectToServerCallback callback) {
		super(context, -1);
		this.callback = callback;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView view = new TextView(getContext());
		Host bt = getItem(position);
		view.setText(bt.getName());
		view.setTextSize(40);
		TouchListener touchListener = new TouchListener(getItem(position).getDevice());
		layout.setOnTouchListener(touchListener);
		view.setOnTouchListener(touchListener);
		return view;
	}

	private class TouchListener implements OnTouchListener {

		private final ServerDevice device;

		public TouchListener(ServerDevice device) {
			this.device = device;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			v.performClick();
			callback.startConnectToServer(device);
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
