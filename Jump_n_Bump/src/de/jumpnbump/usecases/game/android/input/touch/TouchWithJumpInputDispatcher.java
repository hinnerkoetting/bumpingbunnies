package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.R;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class TouchWithJumpInputDispatcher extends
		InputDispatcher<TouchWithJumpService> {

	public TouchWithJumpInputDispatcher(TouchWithJumpService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
		getInputService().onMotionEvent(motion);
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
		if (v.getId() == R.id.button_up) {
			getInputService().onButtonTouchUp(motion);
		}
	}

}
