package de.jumpnbump.usecases.game.android.input.dispatcher;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.touch.TouchService;

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
	public void dispatchViewTouch(View v, MotionEvent motion) {
	}

}
