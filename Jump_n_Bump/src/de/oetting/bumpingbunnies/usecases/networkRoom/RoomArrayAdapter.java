package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class RoomArrayAdapter extends ArrayAdapter<RoomEntry> {

	public RoomArrayAdapter(Context context, int resource,
			int textViewResourceId, List<RoomEntry> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public RoomArrayAdapter(Context context, int resource,
			int textViewResourceId, RoomEntry[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public RoomArrayAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public RoomArrayAdapter(Context context, int textViewResourceId,
			List<RoomEntry> objects) {
		super(context, textViewResourceId, objects);
	}

	public RoomArrayAdapter(Context context, int textViewResourceId,
			RoomEntry[] objects) {
		super(context, textViewResourceId, objects);
	}

	public RoomArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

}
