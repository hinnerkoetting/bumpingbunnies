package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.factories.InputConfigurationFactory;

@Category(UnitTests.class)
public class InputConfigurationFactoryTest {

	@Test
	public void create_touch_returnsTouchInputServices() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new TouchInput());
		assertThat(factory, instanceOf(TouchInputServicesFactory.class));
	}

	@Test
	public void reate_touchWithUp_returnsTouchJumpInputService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new TouchWithUpInput());
		assertThat(factory, instanceOf(TouchJumpInputServicesFactory.class));
	}

	@Test
	public void create_keyboard_createsKeyboardService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new KeyboardInputConfiguration("A", "B", "C"));
		assertThat(factory, instanceOf(KeyboardInputServicesFactory.class));
	}

	@Test
	public void create_multiTouch_returnsMultiTouchService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new MultiTouchInput());
		assertThat(factory, instanceOf(MultiTouchJumpServicesFactory.class));
	}

	@Test
	public void create_point_returnsPointService() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new PointerInput());
		assertThat(factory, instanceOf(PointerInputServiceFactory.class));
	}

	@Test
	public void create_hardwareKeyboard_returnsHardwareKeyboardFactory() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new HardwareKeyboardInputConfiguration());
		assertThat(factory, instanceOf(HardwareKeyboardFactory.class));
	}

	@Test
	public void create_distributedKeyboard_returnsDistributedKeyboardFactory() {
		AbstractPlayerInputServicesFactory<?> factory = whenCreating(new DistributedKeyboardinput());
		assertThat(factory, instanceOf(DistributedKeyboardFactory.class));
	}

	private AbstractPlayerInputServicesFactory<?> whenCreating(InputConfiguration input) {
		Configuration configuration = new Configuration(new LocalSettings(input, 1, false, false, false), null, null, null, false);
		return new InputConfigurationFactory().create(configuration);
	}
}
