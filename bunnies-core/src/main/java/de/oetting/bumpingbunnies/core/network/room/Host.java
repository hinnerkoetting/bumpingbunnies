package de.oetting.bumpingbunnies.core.network.room;

import java.net.InetAddress;

public class Host {

	private final InetAddress address;
	private final String name;

	public Host(InetAddress address) {
		this.address = address;
		this.name = null;
	}

	public Host(String name) {
		this.address = null;
		this.name = name;
	}

	public String getName() {
		return name == null ? address.getHostAddress() : name;
	}

	public InetAddress getAddress() {
		if (address == null)
			throw new IllegalArgumentException("Host does not exist.");
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

}
