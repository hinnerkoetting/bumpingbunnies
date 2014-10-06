package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.RoomEntry;
import android.content.Context;
import android.widget.ArrayAdapter;

public class RoomArrayAdapter extends ArrayAdapter<RoomEntry> {

	private RoomEntry me;

	public RoomEntry getMyself() {
		if (this.me == null) {
			throw new IllegalStateException("You were not yet added");
		}
		return this.me;
	}

	public void addMe(RoomEntry entry) {
		add(entry);
		this.me = entry;
	}

	public List<RoomEntry> getAllOtherPlayers() {
		List<RoomEntry> list = new ArrayList<RoomEntry>(getCount() - 1);
		for (int i = 0; i < getCount(); i++) {
			RoomEntry entry = getItem(i);
			if (entry != this.me) {
				list.add(getItem(i));
			}
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
