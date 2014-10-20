package de.oetting.bumpingbunnies.pc.configMenu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PcConfiguration {

	private PlayerConfiguration player1Configuration = new PlayerConfiguration();
	private PlayerConfiguration player2Configuration = new PlayerConfiguration();
	private PlayerConfiguration player3Configuration = new PlayerConfiguration();

	private int speed;

	public PlayerConfiguration getPlayer1Configuration() {
		return player1Configuration;
	}

	public void setPlayer1Configuration(PlayerConfiguration player1Configuration) {
		this.player1Configuration = player1Configuration;
	}

	public PlayerConfiguration getPlayer2Configuration() {
		return player2Configuration;
	}

	public void setPlayer2Configuration(PlayerConfiguration player2Configuration) {
		this.player2Configuration = player2Configuration;
	}

	public PlayerConfiguration getPlayer3Configuration() {
		return player3Configuration;
	}

	public void setPlayer3Configuration(PlayerConfiguration player3Configuration) {
		this.player3Configuration = player3Configuration;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
