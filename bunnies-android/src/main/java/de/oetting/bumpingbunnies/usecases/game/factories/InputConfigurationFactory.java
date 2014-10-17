package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.android.input.analog.AnalogInputConfiguration;
import de.oetting.bumpingbunnies.android.input.analog.AnalogInputFactory;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardFactory;
import de.oetting.bumpingbunnies.android.input.distributedKeyboard.DistributedKeyboardinput;
import de.oetting.bumpingbunnies.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.factory.TouchInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.factory.TouchJumpInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.gamepad.KeyboardInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.hardwareKeyboard.HardwareKeyboardFactory;
import de.oetting.bumpingbunnies.android.input.hardwareKeyboard.HardwareKeyboardInputConfiguration;
import de.oetting.bumpingbunnies.android.input.multiTouch.MultiTouchInput;
import de.oetting.bumpingbunnies.android.input.multiTouch.MultiTouchJumpServicesFactory;
import de.oetting.bumpingbunnies.android.input.pointer.PointerInput;
import de.oetting.bumpingbunnies.android.input.pointer.PointerInputServiceFactory;
import de.oetting.bumpingbunnies.android.input.touch.TouchInput;
import de.oetting.bumpingbunnies.android.input.touch.TouchWithUpInput;
import de.oetting.bumpingbunnies.android.input.touchFling.TouchFlingFactory;
import de.oetting.bumpingbunnies.android.input.touchFling.TouchFlingInput;
import de.oetting.bumpingbunnies.android.input.touchPress.TouchPressInput;
import de.oetting.bumpingbunnies.android.input.touchPress.TouchPressInputFactory;
import de.oetting.bumpingbunnies.android.input.touchRelease.TouchReleaseFactory;
import de.oetting.bumpingbunnies.android.input.touchRelease.TouchReleaseInput;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;

public class InputConfigurationFactory {

	@SuppressWarnings("unchecked")
	public AbstractPlayerInputServicesFactory<InputService> create(InputConfiguration touch) {
		return (AbstractPlayerInputServicesFactory<InputService>) createIntern(touch);
	}

	private AbstractPlayerInputServicesFactory<? extends InputService> createIntern(InputConfiguration touch) {
		if (touch instanceof TouchInput)
			return new TouchInputServicesFactory();
		else if (touch instanceof TouchWithUpInput)
			return new TouchJumpInputServicesFactory();
		else if (touch instanceof KeyboardInputConfiguration)
			return new KeyboardInputServicesFactory();
		else if (touch instanceof MultiTouchInput)
			return new MultiTouchJumpServicesFactory();
		else if (touch instanceof PointerInput)
			return new PointerInputServiceFactory();
		else if (touch instanceof AnalogInputConfiguration)
			return new AnalogInputFactory();
		else if (touch instanceof TouchFlingInput)
			return new TouchFlingFactory();
		else if (touch instanceof TouchPressInput)
			return new TouchPressInputFactory();
		else if (touch instanceof TouchReleaseInput)
			return new TouchReleaseFactory();
		else if (touch instanceof HardwareKeyboardInputConfiguration)
			return new HardwareKeyboardFactory();
		else if (touch instanceof DistributedKeyboardinput)
			return new DistributedKeyboardFactory();
		throw new IllegalArgumentException(touch.toString());
	}
}
