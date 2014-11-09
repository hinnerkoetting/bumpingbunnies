package de.oetting.bumpingbunnies.pc.scoreMenu;

public class ScoreEntry {

	private final String playerName;
	private final int score;
	private final int color;

	public ScoreEntry(String playerName, int score, int color) {
		this.playerName = playerName;
		this.score = score;
		this.color = color;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getScore() {
		return score;
	}

	public int getColor() {
		return color;
	}

}
