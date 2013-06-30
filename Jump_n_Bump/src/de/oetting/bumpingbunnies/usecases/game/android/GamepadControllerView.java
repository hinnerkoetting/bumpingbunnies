package de.oetting.bumpingbunnies.usecases.game.android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GamepadControllerView extends RelativeLayout {

	public GamepadControllerView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		addViews();
	}

	public GamepadControllerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		addViews();
	}

	public GamepadControllerView(Context context) {
		super(context);
		addViews();
	}

	private void addViews() {
		addButtonDown();
		addButtonUp();
		addButtonRight();
		addButtonLeft();
	}

	private void addButtonLeft() {
		Button v = createButton();
		v.setText("\u2190");
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_LEFT);
		addView(v);
	}

	private void addButtonUp() {
		TextView v = createButton();
		v.setText("\u2191");
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_TOP | CENTER_HORIZONTAL);
		addView(v);
	}

	private void addButtonRight() {
		TextView v = createButton();
		v.setText("\u2192");
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_RIGHT);
		addView(v);
	}

	private void addButtonDown() {
		TextView v = createButton();
		v.setText("\u2193");
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(ALIGN_BOTTOM | CENTER_HORIZONTAL);
		addView(v);
	}

	private Button createButton() {
		Button view = new Button(getContext());
		// view.setTextSize(getContext().getResources().getDimension(
		// R.dimen.gamepad_buttons));
		return view;
	}

}
