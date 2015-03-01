package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.oetting.bumpingbunnies.core.network.room.RoomEntry;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

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

	public RoomArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public RoomEntry findEntry(ConnectionIdentifier opponent) {
		for (int i = 0; i < getCount(); i++) {
			if (getItem(i).getOponent().equals(opponent))
				return getItem(i);
		}
		throw new IllegalArgumentException("Player does not exist " + opponent);
	}

	public RoomEntry findEntry(int playerId) {
		for (int i = 0; i < getCount(); i++) {
			if (getItem(i).getPlayerId() == playerId)
				return getItem(i);
		}
		throw new IllegalArgumentException("Player does not exist " + playerId);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getItem(position) != me)
					remove(getItem(position));
			}
		});
		return view;
	}
}
