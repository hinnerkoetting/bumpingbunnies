package de.oetting.bumpingbunnies.usecases.settings;

import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.android.input.hardwareKeyboard.HardwareKeyboardInputConfiguration;
import de.oetting.bumpingbunnies.android.input.multiTouch.MultiTouchInput;
import de.oetting.bumpingbunnies.android.input.touch.TouchInput;
import de.oetting.bumpingbunnies.android.input.touch.TouchWithUpInput;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class InputConfigurationGenerator {

	public static InputConfiguration createInputConfigurationFromRadioGroup(RadioGroup group) {
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
			return new DistributedKeyboardinput();
		case R.id.start_button_touch:
			return new TouchInput();
		case R.id.start_button_touch_jump:
			return new TouchWithUpInput();
		case R.id.start_button_multi_touch:
			return new MultiTouchInput();
		case R.id.start_button_hardware_keyboard:
			return new HardwareKeyboardInputConfiguration();
		case R.id.start_button_distributed_keyboard:
			return new DistributedKeyboardinput();
		default:
			throw new IllegalArgumentException("Unknown inputtype");
		}
	}

	public static void selectInputConfiguration(InputConfiguration inputconfiguration, RadioGroup group) {
		for (int i = 0; i < group.getChildCount(); i++) {
			CompoundButton cb = (CompoundButton) group.getChildAt(i);
			InputConfiguration buttonInputConfiguration = createInputConfigurationFromView(cb.getId());
			if (buttonInputConfiguration.equals(inputconfiguration)) {
				cb.setChecked(true);
				return;
			}
		}
	}
}
