package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Test;

import de.oetting.bumpingbunnies.usecases.game.factories.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

public class OpponentTypeFactoryFactoryTest {

	@Test
	public void createFactory_myPlayer() {
		OpponentTypeFactory factory = new OpponentTypeFactoryFactory()
				.createFactory(OpponentType.MY_PLAYER);
		assertThat(factory, instanceOf(MyPlayerOpponentTypeFactory.class));
	}

	@Test
	public void createFactory_ai() {
		OpponentTypeFactory factory = new OpponentTypeFactoryFactory()
				.createFactory(OpponentType.AI);
		assertThat(factory, instanceOf(AiOpponentTypeFactory.class));
	}

	@Test
	public void createFactory_wlan() {
		OpponentTypeFactory factory = new OpponentTypeFactoryFactory()
				.createFactory(OpponentType.WLAN);
		assertThat(factory, instanceOf(WlanOpponentTypeFactory.class));
	}

	@Test
	public void createFactory_bluetooth() {
		OpponentTypeFactory factory = new OpponentTypeFactoryFactory()
				.createFactory(OpponentType.BLUETOOTH);
		assertThat(factory, instanceOf(BluetoothOpponentTypeFactory.class));
	}

}
