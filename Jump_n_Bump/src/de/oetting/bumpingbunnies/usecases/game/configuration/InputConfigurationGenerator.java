package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;

public class InputConfigurationGenerator {

	public static InputConfiguration createInputConfigurationFromRadioGroup(
			RadioGroup group) {
		for (int i = 0; i < group.getChildCount(); i++) {
			CompoundButton cb = (CompoundButton) group.getChildAt(i);
			if (cb.isChecked()) {
				return createInputConfigurationFromView(cb.getId());
			}
		}
		throw new IllegalArgumentException("no button checked in this group");
	}

	public static InputConfiguration createInputConfigurationFromView(int id) {
		switch (id) {
		case R.id.start_button_keyboard:
			return InputConfiguration.KEYBOARD;
		case R.id.start_button_touch:
			return InputConfiguration.TOUCH;
		case R.id.start_button_touch_jump:
			return InputConfiguration.TOUCH_WITH_UP;
		case R.id.start_button_multi_touch:
			return InputConfiguration.MULTI_TOUCH;
		case R.id.start_button_pointer:
			return InputConfiguration.POINTER;
		case R.id.start_button_remember_pointer:
			return InputConfiguration.REMEMBER_POINTER;
		case R.id.start_button_analog:
			return InputConfiguration.ANALOG;
			// case R.id.start_button_fling_touch:
			// return InputConfiguration.TOUCH_FLING;
		case R.id.start_button_touch_press:
			return InputConfiguration.TOUCH_PRESS;
		case R.id.start_button_touch_release:
			return InputConfiguration.TOUCH_RELEASE;
		case R.id.start_button_hardware_keyboard:
			return InputConfiguration.HARDWARE_KEYBOARD;
		case R.id.start_button_distributed_keyboard:
			return InputConfiguration.DISTRIBUTED_KEYBOARD;
		default:
			throw new IllegalArgumentException("Unknown inputtype");
		}
	}
}
