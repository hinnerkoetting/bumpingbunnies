package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.model.color.Color;

public class Paint {

	public static final int BLACK = 0xFF000000;
	public static final int LIGHT_GRAY = 0xFFAAAAAA;

	private int color;
	private float textSize;

	public Paint(int color) {
		this.color = color;
	}

	public Paint() {
		this(Color.BLACK);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setAlpha(int alpha) {
		color |= (alpha << 24);
	}

	public int getAlpha() {
		return color >>> 24;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

}
