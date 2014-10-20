package de.oetting.bumpingbunnies.pc.configMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PcConfiguration {

	private final List<PlayerConfiguration> configurations = new ArrayList<PlayerConfiguration>();

	public PcConfiguration() {
		for (int i = 0; i < 3; i++)
			configurations.add(new PlayerConfiguration());
	}

	private int speed;

	public PlayerConfiguration getPlayer1Configuration() {
		return configurations.get(0);
	}

	public PlayerConfiguration getPlayer2Configuration() {
		return configurations.get(1);
	}

	public PlayerConfiguration getPlayer3Configuration() {
		return configurations.get(2);
	}

	public PlayerConfiguration getPlayerConfiguration(int index) {
		return configurations.get(index);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getMaxNumberOfPlayers() {
		return configurations.size();
	}

	public List<PlayerConfiguration> getPlayerConfigurations() {
		return Collections.unmodifiableList(configurations);
	}

	@Override
	public String toString() {
		return "PcConfiguration [configurations=" + configurations + ", speed=" + speed + "]";
	}

}
