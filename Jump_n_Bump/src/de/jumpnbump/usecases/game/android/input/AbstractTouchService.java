package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;

public interface AbstractTouchService extends InputService {

	public abstract void onMotionEvent(MotionEvent motionEvent);
}
