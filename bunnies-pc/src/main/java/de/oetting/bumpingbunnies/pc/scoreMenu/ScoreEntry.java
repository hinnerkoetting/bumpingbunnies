package de.oetting.bumpingbunnies.pc.scoreMenu;

public class ScoreEntry {

	private final String playerName;
	private final int score;

	public ScoreEntry(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getScore() {
		return score;
	}

}
