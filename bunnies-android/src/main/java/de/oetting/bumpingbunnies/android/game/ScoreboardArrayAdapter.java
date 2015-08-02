package de.oetting.bumpingbunnies.android.game;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class ScoreboardArrayAdapter extends ArrayAdapter<Bunny> {

	private static final int MAX_NAME_LENGTH = 8;
	
	public ScoreboardArrayAdapter(Context context) {
		super(context, -1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = createRow(parent);
		Bunny item = getItem(position);
		fillName(view, item);
		fillScore(view, item);
		return view;
	}

	private void fillScore(View view, Bunny item) {
		TextView scoreView = (TextView) view.findViewById(R.id.game_scoreboard_bunnyscore);
		scoreView.setTextColor(item.getColor());
		scoreView.setText(Integer.toString(item.getScore()));
	}

	private void fillName(View view, Bunny item) {
		TextView nameView = (TextView) view.findViewById(R.id.game_scoreboard_bunnyname);
		nameView.setTextColor(item.getColor());
		int maxCharacterIndex = item.getName().length() > MAX_NAME_LENGTH ? MAX_NAME_LENGTH : item.getName().length();
		nameView.setText(item.getName().substring(0, maxCharacterIndex));
	}

	private View createRow(ViewGroup parent) {
		LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
		View view = layoutInflater.inflate(R.layout.game_scoreboard_one_entry, parent, false);
		return view;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
