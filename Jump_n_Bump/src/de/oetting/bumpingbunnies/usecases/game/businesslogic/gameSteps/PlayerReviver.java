package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerReviver implements GameStepAction {

	private final List<PlayerReviveEntry> reviveEntries;
	private final List<? extends ThreadedNetworkSender> senderList;

	public PlayerReviver(List<? extends ThreadedNetworkSender> senderList) {
		super();
		this.senderList = senderList;
		this.reviveEntries = new LinkedList<PlayerReviveEntry>();
	}

	public PlayerReviver(List<? extends ThreadedNetworkSender> senderList, List<PlayerReviveEntry> reviveEntries) {
		super();
		this.senderList = senderList;
		this.reviveEntries = reviveEntries;
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < this.reviveEntries.size(); i++) {
			PlayerReviveEntry entry = this.reviveEntries.get(i);
			if (entry.getEarliestReviveTime() <= currentTime) {
				revivePlayer(entry);
				this.reviveEntries.remove(i);
				i--;
			}
		}
	}

	private void revivePlayer(PlayerReviveEntry entry) {
		Player player = entry.getPlayer();
		player.setDead(false);
		for (ThreadedNetworkSender sender : this.senderList) {
			new PlayerIsRevivedSender(sender).sendMessage(player.id());
		}
	}

	public void revivePlayerLater(Player player) {
		this.reviveEntries.add(new PlayerReviveEntry(System.currentTimeMillis() + BunnyDelayedReviver.KILL_TIME_MILLISECONDS, player));
	}

}
