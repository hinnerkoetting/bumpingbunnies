package de.oetting.bumpingbunnies.usecases.start.android;

public class ProgressToIntValueConverter implements ProgressToValueConverter {
	private int min;

	public ProgressToIntValueConverter(int min) {
		super();
		this.min = min;
	}

	@Override
	public String getValue(int progress) {
		return Integer.toString(this.min + progress);
	}

}
