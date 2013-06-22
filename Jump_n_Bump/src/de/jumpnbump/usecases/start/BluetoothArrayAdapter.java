package de.jumpnbump.usecases.start;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.jumpnbump.usecases.networkRoom.ManagesConnectionsToServer;
import de.jumpnbump.usecases.start.communication.bluetooth.BluetoothServerDevice;

public class BluetoothArrayAdapter extends ArrayAdapter<BluetoothDevice> {

	private ManagesConnectionsToServer callback;

	public BluetoothArrayAdapter(Context context,
			ManagesConnectionsToServer callback) {
		super(context, -1);
		this.callback = callback;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView view = new TextView(getContext());
		BluetoothDevice bt = getItem(position);
		view.setText(bt.getName());
		view.setTextSize(40);
		TouchListener touchListener = new TouchListener(getItem(position));
		layout.setOnTouchListener(touchListener);
		view.setOnTouchListener(touchListener);
		return view;
	}

	private class TouchListener implements OnTouchListener {

		private final BluetoothDevice device;

		public TouchListener(BluetoothDevice device) {
			this.device = device;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			BluetoothServerDevice abstractDevice = new BluetoothServerDevice(
					this.device);
			BluetoothArrayAdapter.this.callback
					.startConnectToServer(abstractDevice);
			return true;
		}

	}

}
