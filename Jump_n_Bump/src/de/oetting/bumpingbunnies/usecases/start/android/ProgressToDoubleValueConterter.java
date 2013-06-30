package de.oetting.bumpingbunnies.usecases.start.android;

/**
 * Not tested
 * 
 * 
 */
public class ProgressToDoubleValueConterter implements ProgressToValueConverter {

	private double min;
	private double max;

	private double maxProgress;

	public ProgressToDoubleValueConterter(double min, double max,
			int maxProgress) {
		super();
		this.min = min;
		this.max = max;
		this.maxProgress = maxProgress;
	}

	@Override
	public String getValue(int progress) {
		double diff = this.max - this.min;
		double diffProgress = progress / this.maxProgress;
		return Double.toString(this.min + diffProgress * diff);
	}

}
