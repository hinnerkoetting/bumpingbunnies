package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerReviver implements GameStepAction {

	private final List<PlayerReviveEntry> reviveEntries = new LinkedList<PlayerReviveEntry>();
	private final List<? extends RemoteSender> senderList;

	public PlayerReviver(List<? extends RemoteSender> senderList) {
		super();
		this.senderList = senderList;
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
		for (RemoteSender sender : this.senderList) {
			new PlayerIsRevivedSender(sender).sendMessage(player.id());
		}
	}

	public void revivePlayerLater(Player player) {
		this.reviveEntries.add(new PlayerReviveEntry(System.currentTimeMillis() + BunnyDelayedReviver.KILL_TIME_MILLISECONDS, player));
	}

}
