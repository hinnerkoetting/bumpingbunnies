package de.oetting.bumpingbunnies.core.network.room;

import de.oetting.bumpingbunnies.core.network.ServerDevice;

public class Host {

	private final ServerDevice device;

	public Host(ServerDevice device) {
		this.device = device;
	}

	public String getName() {
		return device.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Host other = (Host) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		return true;
	}

	public ServerDevice getDevice() {
		return device;
	}

}
