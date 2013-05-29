package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class TouchInputDispatcher extends InputDispatcher<TouchService> {

	public TouchInputDispatcher(TouchService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
		TouchService touchService = getInputService();
		touchService.onMotionEvent(motion);
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
	}

}
