package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NetworkReceiveControl implements PlayerJoinListener {

	private List<NetworkReceiveThread> networkReceiveThreads;
	private NetworkReceiveThreadFactory factory;

	public NetworkReceiveControl(NetworkReceiveThreadFactory factory) {
		super();
		this.factory = factory;
		this.networkReceiveThreads = new CopyOnWriteArrayList<NetworkReceiveThread>();
	}

	public NetworkReceiveControl(NetworkReceiveThreadFactory factory, List<NetworkReceiveThread> networkReceiveThreads) {
		super();
		this.factory = factory;
		this.networkReceiveThreads = networkReceiveThreads;
	}

	public void shutDownThreads() {
		for (NetworkReceiveThread receiver : this.networkReceiveThreads) {
			receiver.cancel();
		}
	}

	@Override
	public void newPlayerJoined(Player p) {
		List<NetworkReceiveThread> newThreads = this.factory.create(p);
		this.networkReceiveThreads.addAll(newThreads);
		for (NetworkReceiveThread nrt : newThreads) {
			nrt.start();
		}
	}

	@Override
	public void playerLeftTheGame(Player p) {
		List<NetworkReceiveThread> playerThreads = findThreadOfThisPlayer(p);
		this.networkReceiveThreads.removeAll(playerThreads);
	}

	private List<NetworkReceiveThread> findThreadOfThisPlayer(Player p) {
		List<NetworkReceiveThread> threads = new LinkedList<NetworkReceiveThread>();
		for (NetworkReceiveThread nrt : this.networkReceiveThreads) {
			if (nrt.belongsToPlayer(p)) {
				threads.add(nrt);
			}
		}
		return threads;
	}

}