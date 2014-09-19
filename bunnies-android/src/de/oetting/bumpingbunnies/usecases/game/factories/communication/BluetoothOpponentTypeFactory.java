package de.oetting.bumpingbunnies.usecases.game.factories.communication;


public class BluetoothOpponentTypeFactory implements OpponentTypeFactory {

	@Override
	public OpponentTypeReceiveFactory createReceiveFactory() {
		return new BluetoothOpponentTypeReceiveFactory();
	}

	@Override
	public OpponentTypeSendFactory createSendFactory() {
		return new BluetoothOpponentTypeSendFactory();
	}

}
