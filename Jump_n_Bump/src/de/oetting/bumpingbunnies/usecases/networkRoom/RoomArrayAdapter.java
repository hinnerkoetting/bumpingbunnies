package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class RoomArrayAdapter extends ArrayAdapter<RoomEntry> {

	public RoomEntry getMyself() {
		return getItem(0);
	}

	public List<RoomEntry> getAllOtherPlayers() {
		List<RoomEntry> list = new ArrayList<RoomEntry>(getCount() - 1);
		for (int i = 1; i < getCount(); i++) {
			list.add(getItem(i));
		}
		return list;
	}

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
