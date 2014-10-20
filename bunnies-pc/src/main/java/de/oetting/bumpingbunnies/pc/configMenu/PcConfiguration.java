package de.oetting.bumpingbunnies.pc.configMenu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PcConfiguration {

	private String player1Name;
	private String player1Left;
	private String player1Up;
	private String player1Down;
	private String player1Right;
	private String player2Name;
	private String player2Left;
	private String player2Up;
	private String player2Down;
	private String player2Right;
	private String player3Name;
	private String player3Left;
	private String player3Up;
	private String player3Down;
	private String player3Right;

	private int speed;

	public String getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer1Left() {
		return player1Left;
	}

	public void setPlayer1Left(String player1Left) {
		this.player1Left = player1Left;
	}

	public String getPlayer1Up() {
		return player1Up;
	}

	public void setPlayer1Up(String player1Up) {
		this.player1Up = player1Up;
	}

	public String getPlayer1Down() {
		return player1Down;
	}

	public void setPlayer1Down(String player1Down) {
		this.player1Down = player1Down;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public String getPlayer2Left() {
		return player2Left;
	}

	public void setPlayer2Left(String player2Left) {
		this.player2Left = player2Left;
	}

	public String getPlayer2Up() {
		return player2Up;
	}

	public void setPlayer2Up(String player2Up) {
		this.player2Up = player2Up;
	}

	public String getPlayer2Down() {
		return player2Down;
	}

	public void setPlayer2Down(String player2Down) {
		this.player2Down = player2Down;
	}

	public String getPlayer3Name() {
		return player3Name;
	}

	public void setPlayer3Name(String player3Name) {
		this.player3Name = player3Name;
	}

	public String getPlayer3Left() {
		return player3Left;
	}

	public void setPlayer3Left(String player3Left) {
		this.player3Left = player3Left;
	}

	public String getPlayer3Up() {
		return player3Up;
	}

	public void setPlayer3Up(String player3Up) {
		this.player3Up = player3Up;
	}

	public String getPlayer3Down() {
		return player3Down;
	}

	public void setPlayer3Down(String player3Down) {
		this.player3Down = player3Down;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getPlayer1Right() {
		return player1Right;
	}

	public void setPlayer1Right(String player1Right) {
		this.player1Right = player1Right;
	}

	public String getPlayer2Right() {
		return player2Right;
	}

	public void setPlayer2Right(String player2Right) {
		this.player2Right = player2Right;
	}

	public String getPlayer3Right() {
		return player3Right;
	}

	public void setPlayer3Right(String player3Right) {
		this.player3Right = player3Right;
	}

}
