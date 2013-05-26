package de.jumpnbump.usecases.game.android;

import android.content.Context;
import android.util.AttributeSet;

public class SquaredGameView extends GameView {

	public SquaredGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SquaredGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquaredGameView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int min = minimum(width, height);
		setMeasuredDimension(min, min);
	}

	private int minimum(int i1, int i2) {
		return i1 < i2 ? i1 : i2;
	}

}
