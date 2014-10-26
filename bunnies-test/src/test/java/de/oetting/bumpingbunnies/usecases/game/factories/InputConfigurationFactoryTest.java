package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

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
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;

public class InputConfigurationFactoryTest {

	@Test
	public void create_touch_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new TouchInput());
		assertThat(factory, instanceOf(TouchInputServicesFactory.class));
	}

	@Test
	public void create_touchWithUp_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new TouchWithUpInput());
		assertThat(factory, instanceOf(TouchJumpInputServicesFactory.class));
	}

	@Test
	public void create_keyboard_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new KeyboardInputConfiguration("A", "B", "C"));
		assertThat(factory, instanceOf(KeyboardInputServicesFactory.class));
	}

	@Test
	public void create_multiTouch_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new MultiTouchInput());
		assertThat(factory, instanceOf(MultiTouchJumpServicesFactory.class));
	}

	@Test
	public void create_pointer_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new PointerInput());
		assertThat(factory, instanceOf(PointerInputServiceFactory.class));
	}

	@Test
	public void create_hardwareKeyboard_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new HardwareKeyboardInputConfiguration());
		assertThat(factory, instanceOf(HardwareKeyboardFactory.class));
	}

	@Test
	public void create_distributedKeyboard_createsTouchInputConfiguration() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new DistributedKeyboardinput());
		assertThat(factory, instanceOf(DistributedKeyboardFactory.class));
	}

	private AbstractPlayerInputServicesFactory<?> whenCreating(InputConfiguration touch) {
		return new InputConfigurationFactory().create(touch);
	}
}
