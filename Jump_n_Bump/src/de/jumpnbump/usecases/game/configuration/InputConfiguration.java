package de.jumpnbump.usecases.game.configuration;

import de.jumpnbump.usecases.game.android.input.analog.AnalogInputFactory;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.factory.KeyboardInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.factory.TouchInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.factory.TouchJumpInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.hardwareKeyboard.HardwareKeyboardFactory;
import de.jumpnbump.usecases.game.android.input.multiTouch.MultiTouchJumpServicesFactory;
import de.jumpnbump.usecases.game.android.input.pointer.PointerInputServiceFactory;
import de.jumpnbump.usecases.game.android.input.rememberPointer.RememberPointerInputFactory;
import de.jumpnbump.usecases.game.android.input.touchFling.TouchFlingFactory;
import de.jumpnbump.usecases.game.android.input.touchPress.TouchPressInputFactory;
import de.jumpnbump.usecases.game.android.input.touchRelease.TouchReleaseFactory;

public enum InputConfiguration {

	TOUCH(TouchInputServicesFactory.class), TOUCH_WITH_UP(
			TouchJumpInputServicesFactory.class), KEYBOARD(
			KeyboardInputServicesFactory.class), MULTI_TOUCH(
			MultiTouchJumpServicesFactory.class), POINTER(
			PointerInputServiceFactory.class), REMEMBER_POINTER(
			RememberPointerInputFactory.class), ANALOG(AnalogInputFactory.class), TOUCH_FLING(
			TouchFlingFactory.class), TOUCH_PRESS(TouchPressInputFactory.class), TOUCH_RELEASE(
			TouchReleaseFactory.class), HARDWARE_KEYBOARD(
			HardwareKeyboardFactory.class);

	private Class<? extends AbstractPlayerInputServicesFactory<?>> factoryClass;

	private InputConfiguration(
			Class<? extends AbstractPlayerInputServicesFactory<?>> clazz) {
		this.factoryClass = clazz;
	}

	public AbstractPlayerInputServicesFactory<?> createInputconfigurationClass() {
		try {
			return this.factoryClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
