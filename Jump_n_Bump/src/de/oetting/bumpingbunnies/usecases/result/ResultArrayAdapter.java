package de.oetting.bumpingbunnies.usecases.result;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.result.model.ResultEntry;

public class ResultArrayAdapter extends ArrayAdapter<ResultEntry> {

	public ResultArrayAdapter(Context context, List<ResultEntry> objects) {
		super(context, -1, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity context = (Activity) getContext();
		LayoutInflater layoutInflater = context.getLayoutInflater();
		View entryView = layoutInflater.inflate(R.layout.result_screen_one_entry, parent,
				false);
		TextView id = (TextView) entryView
				.findViewById(R.id.result_player_score);
		TextView name = (TextView) entryView
				.findViewById(R.id.result_player_name);
		ResultEntry item = getItem(position);
		id.setTextColor(item.getPlayerColor());
		name.setTextColor(item.getPlayerColor());
		id.setText(Integer.toString(item.getPlayerScore()));
		name.setText(item.getPlayerName());
		return entryView;
	}
}
