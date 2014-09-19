package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated;

public class PlayerScoreMessage {

	private final int playerId;
	private final int newScore;

	public PlayerScoreMessage(int playerId, int newScore) {
		super();
		this.playerId = playerId;
		this.newScore = newScore;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public int getNewScore() {
		return this.newScore;
	}

}
