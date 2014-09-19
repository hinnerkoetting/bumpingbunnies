package de.oetting.bumpingbunnies.usecases.game.configuration;

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

public class InputConfigurationFactory {

	public AbstractPlayerInputServicesFactory<?> create(InputConfiguration configuration) {
		switch (configuration) {
		case ANALOG:
			return new AnalogInputFactory();
		case DISTRIBUTED_KEYBOARD:
			return new DistributedKeyboardFactory();
		case HARDWARE_KEYBOARD:
			return new HardwareKeyboardFactory();
		case KEYBOARD:
			return new KeyboardInputServicesFactory();
		case MULTI_TOUCH:
			return new MultiTouchJumpServicesFactory();
		case POINTER:
			return new PointerInputServiceFactory();
		case TOUCH:
			return new TouchInputServicesFactory();
		case TOUCH_FLING:
			return new TouchFlingFactory();
		case TOUCH_PRESS:
			return new TouchPressInputFactory();
		case TOUCH_RELEASE:
			return new TouchReleaseFactory();
		case TOUCH_WITH_UP:
			return new TouchJumpInputServicesFactory();
		default:
			throw new IllegalArgumentException(configuration.toString());
		}
	}
}
