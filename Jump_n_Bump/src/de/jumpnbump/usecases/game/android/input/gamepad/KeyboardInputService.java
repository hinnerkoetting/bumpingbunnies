package de.jumpnbump.usecases.game.android.input.gamepad;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputService;

public interface KeyboardInputService extends InputService {

	boolean onButtonTouch(View v, MotionEvent event);
}
