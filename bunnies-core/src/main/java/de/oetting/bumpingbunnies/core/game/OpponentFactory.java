package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class OpponentFactory {

	public static ConnectionIdentifier createLocalPlayer(String playerName) {
		return createOpponent("LOCAL" + playerName, OpponentType.LOCAL_PLAYER);
	}

	public static ConnectionIdentifier createRemoteOpponent(String identifier, OpponentType type) {
		return createOpponent(identifier, type);
	}

	public static ConnectionIdentifier createAiPlayer(String name) {
		return createOpponent("LOCALAI" + name, OpponentType.AI);
	}

	public static ConnectionIdentifier createBluetoothPlayer(String address) {
		return createOpponent("BLUETOOTH" + address, OpponentType.BLUETOOTH);
	}

	public static ConnectionIdentifier createBroadcastOpponent() {
		return createOpponent("BROADCAST", OpponentType.WLAN);
	}

	public static ConnectionIdentifier createWlanPlayer(String address, int port) {
		return createOpponent("WLAN" + address + ":" + port, OpponentType.WLAN);
	}

	public static ConnectionIdentifier createListeningOpponent() {
		return createOpponent("LISTENING", OpponentType.WLAN);
	}

	public static ConnectionIdentifier createJoinedPlayer(String playerName, int playerId) {
		return createOpponent("JOINED_LATER" + playerName + playerId, OpponentType.INDIRECT);
	}

	private static ConnectionIdentifier createOpponent(String identifier, OpponentType type) {
		Guard.againstNull(identifier);
		Guard.againstNull(type);
		return new ConnectionIdentifier(new OpponentIdentifier(identifier), type);
	}

}
