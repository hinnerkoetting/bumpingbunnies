package de.oetting.bumpingbunnies.core.graphics;

public class Paint {

	public static final int BLACK = 0x00000000;
	public static final int LIGHT_GRAY = 0xAAAAAA00;

	private int color;
	private int alpha;
	private float textSize;

	public Paint(int color) {
		this.color = color;
	}

	public Paint() {
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getAlpha() {
		return alpha;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

}
