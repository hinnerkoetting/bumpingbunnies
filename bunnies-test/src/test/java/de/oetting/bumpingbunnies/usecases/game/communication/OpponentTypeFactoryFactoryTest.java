package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.oetting.bumpingbunnies.communication.AndroidOpponentTypeReceiveFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.AiOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.MyPlayerOpponentTypeFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.BluetoothOpponentTypeFactory;

public class OpponentTypeFactoryFactoryTest {

	@Test
	public void createFactory_myPlayer() {
		OpponentTypeFactory factory = new AndroidOpponentTypeReceiveFactoryFactory().createReceiveFactory(OpponentType.MY_PLAYER);
		assertThat(factory, instanceOf(MyPlayerOpponentTypeFactory.class));
	}

	@Test
	public void createFactory_ai() {
		OpponentTypeFactory factory = new AndroidOpponentTypeReceiveFactoryFactory().createReceiveFactory(OpponentType.AI);
		assertThat(factory, instanceOf(AiOpponentTypeFactory.class));
	}

	@Test
	public void createFactory_wlan() {
		OpponentTypeFactory factory = new AndroidOpponentTypeReceiveFactoryFactory().createReceiveFactory(OpponentType.WLAN);
		assertThat(factory, instanceOf(WlanOpponentTypeFactory.class));
	}

	@Test
	public void createFactory_bluetooth() {
		OpponentTypeFactory factory = new AndroidOpponentTypeReceiveFactoryFactory().createReceiveFactory(OpponentType.BLUETOOTH);
		assertThat(factory, instanceOf(BluetoothOpponentTypeFactory.class));
	}

}
