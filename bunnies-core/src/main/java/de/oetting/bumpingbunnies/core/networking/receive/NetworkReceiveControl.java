package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.network.sockets.NewSocketListener;

public class NetworkReceiveControl implements NewSocketListener {

	private List<NetworkReceiver> networkReceiveThreads;
	private NetworkReceiveThreadFactory factory;

	public NetworkReceiveControl(NetworkReceiveThreadFactory factory, List<NetworkReceiver> networkReceiveThreads) {
		this.factory = factory;
		this.networkReceiveThreads = new ArrayList<NetworkReceiver>();
		this.networkReceiveThreads.addAll(networkReceiveThreads);
	}

	public void shutDownThreads() {
		for (NetworkReceiver receiver : this.networkReceiveThreads)
			receiver.shutdown();
	}

	@Override
	public void newEvent(MySocket socket) {
		List<NetworkReceiver> newThreads = this.factory.create(socket);
		this.networkReceiveThreads.addAll(newThreads);
		for (NetworkReceiver nrt : newThreads) {
			nrt.start();
		}
	}

	@Override
	public void removeEvent(MySocket socket) {
		List<NetworkReceiver> playerThreads = findThreadOfThisPlayer(socket);
		for (NetworkReceiver receiver : playerThreads) {
			receiver.cancel();
		}
		this.networkReceiveThreads.removeAll(playerThreads);
	}

	private List<NetworkReceiver> findThreadOfThisPlayer(MySocket socket) {
		List<NetworkReceiver> threads = new LinkedList<NetworkReceiver>();
		for (NetworkReceiver nrt : this.networkReceiveThreads) {
			if (nrt.belongsToSocket(socket)) {
				threads.add(nrt);
			}
		}
		return threads;
	}

	public void start() {
		for (NetworkReceiver receiver : networkReceiveThreads)
			receiver.start();
	}

}