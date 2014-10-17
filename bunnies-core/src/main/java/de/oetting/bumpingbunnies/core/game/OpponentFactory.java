package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class OpponentFactory {

	public static Opponent createLocalPlayer(String playerName) {
		return createOpponent("LOCAL" + playerName, OpponentType.LOCAL_PLAYER);
	}

	public static Opponent createRemoteOpponent(String identifier, OpponentType type) {
		return createOpponent(identifier, type);
	}

	public static Opponent createAiPlayer(String name) {
		return createOpponent("LOCALAI" + name, OpponentType.AI);
	}

	public static Opponent createBluetoothPlayer(String address) {
		return createOpponent("BLUETOOTH" + address, OpponentType.BLUETOOTH);
	}

	public static Opponent createBroadcastOpponent() {
		return createOpponent("BROADCAST", OpponentType.WLAN);
	}

	public static Opponent createWlanPlayer(String address, int port) {
		return createOpponent("WLAN" + address + ":" + port, OpponentType.WLAN);
	}

	public static Opponent createListeningOpponent() {
		return createOpponent("LISTENING", OpponentType.WLAN);
	}

	public static Opponent createJoinedPlayer(String playerName, int playerId) {
		return createOpponent("JOINED_LATER" + playerName + playerId, OpponentType.INDIRECT);
	}

	private static Opponent createOpponent(String identifier, OpponentType type) {
		Guard.againstNull(identifier);
		Guard.againstNull(type);
		return new Opponent(new OpponentIdentifier(identifier), type);
	}

}
