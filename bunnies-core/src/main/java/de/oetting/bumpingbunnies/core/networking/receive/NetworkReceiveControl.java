package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class NetworkReceiveControl implements PlayerJoinListener {

	private List<NetworkReceiver> networkReceiveThreads;
	private NetworkReceiverFactory factory;

	public NetworkReceiveControl(NetworkReceiverFactory factory) {
		this.factory = factory;
		this.networkReceiveThreads = new CopyOnWriteArrayList<NetworkReceiver>();
	}

	public NetworkReceiveControl(NetworkReceiverFactory factory, List<NetworkReceiver> networkReceiveThreads) {
		this.factory = factory;
		this.networkReceiveThreads = new ArrayList<NetworkReceiver>();
		this.networkReceiveThreads.addAll(networkReceiveThreads);
	}

	public void shutDownThreads() {
		for (NetworkReceiver receiver : this.networkReceiveThreads) {
			receiver.cancel();
		}
	}

	@Override
	public void newPlayerJoined(Player p) {
		List<NetworkReceiver> newThreads = this.factory.create(p);
		this.networkReceiveThreads.addAll(newThreads);
		for (NetworkReceiver nrt : newThreads) {
			nrt.start();
		}
	}

	@Override
	public void playerLeftTheGame(Player p) {
		List<NetworkReceiver> playerThreads = findThreadOfThisPlayer(p);
		this.networkReceiveThreads.removeAll(playerThreads);
	}

	private List<NetworkReceiver> findThreadOfThisPlayer(Player p) {
		List<NetworkReceiver> threads = new LinkedList<NetworkReceiver>();
		for (NetworkReceiver nrt : this.networkReceiveThreads) {
			if (nrt.belongsToPlayer(p)) {
				threads.add(nrt);
			}
		}
		return threads;
	}

}