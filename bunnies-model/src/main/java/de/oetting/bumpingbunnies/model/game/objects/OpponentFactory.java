package de.oetting.bumpingbunnies.model.game.objects;

public class OpponentFactory {

	public static Opponent createLocalPlayer(String playerName) {
		return new Opponent(new OpponentIdentifier("LOCAL" + playerName), OpponentType.LOCAL_PLAYER);
	}

	public static Opponent createRemoteOpponent(String identifier, OpponentType type) {
		return new Opponent(new OpponentIdentifier(identifier), type);
	}

	public static Opponent createAiPlayer(String name) {
		return new Opponent(new OpponentIdentifier("LOCALAI" + name), OpponentType.AI);
	}

	public static Opponent createBluetoothPlayer(String address) {
		return new Opponent(new OpponentIdentifier("BLUETOOTH" + address), OpponentType.BLUETOOTH);
	}

	public static Opponent createBroadcastOpponent() {
		return new Opponent(new OpponentIdentifier("BROADCAST"), OpponentType.WLAN);
	}

	public static Opponent createWlanPlayer(String address, int port) {
		return new Opponent(new OpponentIdentifier("WLAN" + address + port), OpponentType.WLAN);
	}

	public static Opponent createListeningOpponent() {
		return new Opponent(new OpponentIdentifier("LISTENING"), OpponentType.WLAN);
	}

	public static Opponent createJoinedPlayer(String playerName, int playerId) {
		return new Opponent(new OpponentIdentifier("JOINED_LATER" + playerName + playerId), OpponentType.INDIRECT);
	}

}
