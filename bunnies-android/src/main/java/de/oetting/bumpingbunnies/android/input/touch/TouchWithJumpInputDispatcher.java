package de.oetting.bumpingbunnies.android.input.touch;

import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;

public class TouchWithJumpInputDispatcher extends
		InputDispatcher<TouchWithJumpService> {

	public TouchWithJumpInputDispatcher(TouchWithJumpService service) {
		super(service);
	}

	@Override
	public boolean dispatchGameTouch(MotionEvent motion) {
		getInputService().onMotionEvent(motion);
		return false;
	}

	@Override
	public boolean dispatchControlViewTouch(View v, MotionEvent motion) {
		if (v.getId() == R.id.button_up) {
			getInputService().onButtonTouchUp(motion);
			return true;
		}
		return false;
	}

}
