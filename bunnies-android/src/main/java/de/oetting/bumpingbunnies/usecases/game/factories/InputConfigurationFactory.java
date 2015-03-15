package de.oetting.bumpingbunnies.usecases.game.factories;

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
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;

public class InputConfigurationFactory {

	@SuppressWarnings("unchecked")
	public AbstractPlayerInputServicesFactory<InputService> create(Configuration configuration) {
		return (AbstractPlayerInputServicesFactory<InputService>) createIntern(configuration);
	}

	private AbstractPlayerInputServicesFactory<? extends InputService> createIntern(Configuration configuration) {
		InputConfiguration input = configuration.getInputConfiguration();
		if (input instanceof TouchInput)
			return new TouchInputServicesFactory();
		else if (input instanceof TouchWithUpInput)
			return new TouchJumpInputServicesFactory();
		else if (input instanceof KeyboardInputConfiguration)
			return new KeyboardInputServicesFactory();
		else if (input instanceof MultiTouchInput)
			return new MultiTouchJumpServicesFactory();
		else if (input instanceof PointerInput)
			return new PointerInputServiceFactory();
		else if (input instanceof HardwareKeyboardInputConfiguration)
			return new HardwareKeyboardFactory(configuration.getLocalSettings().isLefthanded());
		else if (input instanceof DistributedKeyboardinput)
			return new DistributedKeyboardFactory(configuration.getLocalSettings().isLefthanded());
		throw new IllegalArgumentException(input.toString());
	}
}
