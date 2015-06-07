package de.oetting.bumpingbunnies.usecases.resultScreen;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.AndroidPlayerImagesProvider;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesColoror;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;

public class ResultArrayAdapter extends ArrayAdapter<ResultPlayerEntry> {

	public ResultArrayAdapter(Context context, List<ResultPlayerEntry> objects) {
		super(context, -1, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity context = (Activity) getContext();
		LayoutInflater layoutInflater = context.getLayoutInflater();
		return createOneEntryView(layoutInflater, parent, position);
	}
	
	private View createOneEntryView(LayoutInflater layoutInflater, ViewGroup parent, int position) {
		View entryView = layoutInflater.inflate(R.layout.result_screen_one_entry, parent,
				false);
		styleView(entryView, position);
		modifyPlayerPlace(entryView, position);
		modifyPlayerImage(entryView, position);
		modifyScore(entryView, position);
		modifyName(entryView, position);
		return entryView;
	}

	private void modifyPlayerPlace(View entryView, int position) {
		TextView placeView = (TextView) entryView
				.findViewById(R.id.result_player_place);
		placeView.setTextColor(getItem(position).getPlayerColor());
		placeView.setText(Integer.toString(position + 1));		
	}

	private void modifyPlayerImage(View entryView, int position) {
		ImageView imageView = (ImageView) entryView
				.findViewById(R.id.result_player_image);
		ImageWrapper image = new AndroidPlayerImagesProvider(new BunnyImagesReader()).loadOneImage(64, 64);
		ImageWrapper coloredImage = new AndroidImagesColoror().colorImage(image, getItem(position).getPlayerColor());
		imageView.setImageBitmap((Bitmap)coloredImage.getBitmap());
				
	}

	private void styleView(View entryView, int position) {
		if (isEven(position)) 
			entryView.setBackgroundColor(getContext().getResources().getColor(R.color.table_row_even));
		else 
			entryView.setBackgroundColor(getContext().getResources().getColor(R.color.table_row_odd));
	}

	private boolean isEven(int position) {
		return position % 2 == 0;
	}

	private void modifyName(View entryView, int position) {
		TextView name = (TextView) entryView
				.findViewById(R.id.result_player_name);
		name.setTextColor(getItem(position).getPlayerColor());
		name.setText(getItem(position).getPlayerName());
	}

	private void modifyScore(View entryView, int position) {
		TextView scoreEntry = (TextView) entryView
				.findViewById(R.id.result_player_score);
		scoreEntry.setTextColor(getItem(position).getPlayerColor());
		scoreEntry.setText(Integer.toString(getItem(position).getPlayerScore()));
	}
}
