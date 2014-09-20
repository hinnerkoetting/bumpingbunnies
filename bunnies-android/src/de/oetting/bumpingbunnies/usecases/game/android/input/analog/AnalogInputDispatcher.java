package de.oetting.bumpingbunnies.usecases.game.android.input.analog;

import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;

public class AnalogInputDispatcher extends InputDispatcher<AnalogInputService> {

	public AnalogInputDispatcher(AnalogInputService service) {
		super(service);
	}

	@Override
	public boolean dispatchGameTouch(MotionEvent motion) {
		return false;
	}

	@Override
	public boolean dispatchControlViewTouch(View v, MotionEvent motion) {
		if (motion.getAction() == MotionEvent.ACTION_UP) {
			getInputService().reset();
		} else {
			if (isTouchInUpperHalf(v, motion)) {
				getInputService().onTouchUpperHalf();
			} else if (isTouchInLowerHalf(v, motion)) {
				getInputService().onTouchLowerHalf();
			} else if (isTouchInRightHalf(v, motion)) {
				getInputService().onTouchRightHalf();
			} else if (isTouchInLeftHalf(v, motion)) {
				getInputService().onTouchLeftHalf();
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean isTouchInUpperHalf(View v, MotionEvent motion) {
		float y = motion.getY();
		return y / v.getHeight() < 0.5;
	}

	private boolean isTouchInRightHalf(View v, MotionEvent motion) {
		float x = motion.getX();
		return x / v.getWidth() > 0.5;
	}

	private boolean isTouchInLeftHalf(View v, MotionEvent motion) {
		float x = motion.getX();
		return x / v.getWidth() < 0.5;
	}

	private boolean isTouchInLowerHalf(View v, MotionEvent motion) {
		float y = motion.getY();
		return y / v.getHeight() > 0.5;
	}

}
