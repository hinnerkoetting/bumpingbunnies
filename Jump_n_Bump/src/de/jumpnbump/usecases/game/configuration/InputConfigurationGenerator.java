package de.jumpnbump.usecases.game.configuration;

import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.jumpnbump.R;

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
		default:
			throw new IllegalArgumentException("Unknown inputtype");
		}
	}
}
