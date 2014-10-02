package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.model.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.tests.UnitTests;
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
import de.oetting.bumpingbunnies.usecases.game.factories.InputConfigurationFactory;

@Category(UnitTests.class)
public class InputConfigurationFactoryTest {

	@Test
	public void create_touch_returnsTouchInputServices() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.TOUCH);
		assertThat(factory, instanceOf(TouchInputServicesFactory.class));
	}

	@Test
	public void reate_touchWithUp_returnsTouchJumpInputService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.TOUCH_WITH_UP);
		assertThat(factory, instanceOf(TouchJumpInputServicesFactory.class));
	}

	@Test
	public void create_keyboard_createsKeyboardService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.KEYBOARD);
		assertThat(factory, instanceOf(KeyboardInputServicesFactory.class));
	}

	@Test
	public void create_multiTouch_returnsMultiTouchService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.MULTI_TOUCH);
		assertThat(factory, instanceOf(MultiTouchJumpServicesFactory.class));
	}

	@Test
	public void create_point_returnsPointService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.POINTER);
		assertThat(factory, instanceOf(PointerInputServiceFactory.class));
	}

	@Test
	public void create_analog_returnsAnlogService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.ANALOG);
		assertThat(factory, instanceOf(AnalogInputFactory.class));
	}

	@Test
	public void create_touchFling_returnsTouchFlingService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.TOUCH_FLING);
		assertThat(factory, instanceOf(TouchFlingFactory.class));
	}

	@Test
	public void create_touchPress_returnsTouchPressService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.TOUCH_PRESS);
		assertThat(factory, instanceOf(TouchPressInputFactory.class));
	}

	@Test
	public void create_touchRelease_returnsTouchReleaseFactory() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.TOUCH_RELEASE);
		assertThat(factory, instanceOf(TouchReleaseFactory.class));
	}

	@Test
	public void create_hardwareKeyboard_returnsHardwareKeyboardFactory() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.HARDWARE_KEYBOARD);
		assertThat(factory, instanceOf(HardwareKeyboardFactory.class));
	}

	@Test
	public void create_distributedKeyboard_returnsDistributedKeyboardFactory() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(InputConfiguration.DISTRIBUTED_KEYBOARD);
		assertThat(factory, instanceOf(DistributedKeyboardFactory.class));
	}

	private AbstractPlayerInputServicesFactory<?> whenCreating(InputConfiguration configuration) {
		return new InputConfigurationFactory().create(configuration);
	}
}
