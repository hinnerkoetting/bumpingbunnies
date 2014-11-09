package de.oetting.bumpingbunnies.usecases.start.android;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ProgressBarValueChanger implements OnSeekBarChangeListener {

	private final TextView controlledTextview;
	private final ProgressToValueConverter converter;

	public ProgressBarValueChanger(TextView controlledTextview, ProgressToValueConverter converter, int startValue) {
		super();
		this.controlledTextview = controlledTextview;
		this.converter = converter;
		setNewValueToTextfield(startValue);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		setNewValueToTextfield(progress);
	}

	private void setNewValueToTextfield(int progress) {
		String text = this.converter.getValue(progress);
		this.controlledTextview.setText(text);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
