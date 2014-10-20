package de.oetting.bumpingbunnies.pc.configMenu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerConfiguration {

	private String playerName;
	private String playerLeft;
	private String playerUp;
	private String playerRight;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String player1Name) {
		this.playerName = player1Name;
	}

	public String getPlayerLeft() {
		return playerLeft;
	}

	public void setPlayerLeft(String player1Left) {
		this.playerLeft = player1Left;
	}

	public String getPlayerUp() {
		return playerUp;
	}

	public void setPlayerUp(String player1Up) {
		this.playerUp = player1Up;
	}

	public String getPlayerRight() {
		return playerRight;
	}

	public void setPlayerRight(String player1Right) {
		this.playerRight = player1Right;
	}

	@Override
	public String toString() {
		return "PlayerConfiguration [playerName=" + playerName + ", playerLeft=" + playerLeft + ", playerUp=" + playerUp + ", playerRight=" + playerRight + "]";
	}

}
