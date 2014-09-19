package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.analog.AnalogInputFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.distributedKeyboard.DistributedKeyboardFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.TouchInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.TouchJumpInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.gamepad.KeyboardInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.hardwareKeyboard.HardwareKeyboardFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.multiTouch.MultiTouchJumpServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.pointer.PointerInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touchFling.TouchFlingFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touchPress.TouchPressInputFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touchRelease.TouchReleaseFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;

public class InputConfigurationFactory {

	@SuppressWarnings("unchecked")
	public AbstractPlayerInputServicesFactory<InputService> create(InputConfiguration touch) {
		return (AbstractPlayerInputServicesFactory<InputService>) createIntern(touch);
	}

	private AbstractPlayerInputServicesFactory<? extends InputService> createIntern(InputConfiguration touch) {
		switch (touch) {
		case TOUCH:
			return new TouchInputServicesFactory();
		case TOUCH_WITH_UP:
			return new TouchJumpInputServicesFactory();
		case KEYBOARD:
			return new KeyboardInputServicesFactory();
		case MULTI_TOUCH:
			return new MultiTouchJumpServicesFactory();
		case POINTER:
			return new PointerInputServiceFactory();
		case ANALOG:
			return new AnalogInputFactory();
		case TOUCH_FLING:
			return new TouchFlingFactory();
		case TOUCH_PRESS:
			return new TouchPressInputFactory();
		case TOUCH_RELEASE:
			return new TouchReleaseFactory();
		case HARDWARE_KEYBOARD:
			return new HardwareKeyboardFactory();
		case DISTRIBUTED_KEYBOARD:
			return new DistributedKeyboardFactory();
		}
		throw new IllegalArgumentException(touch.toString());
	}
}
