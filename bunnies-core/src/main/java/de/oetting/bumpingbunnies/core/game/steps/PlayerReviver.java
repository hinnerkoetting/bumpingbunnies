package de.oetting.bumpingbunnies.core.game.steps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.MessageSender;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerReviver implements GameStepAction {

	private static final int KILL_TIME_MILLISECONDS = 1000;

	private final List<PlayerReviveEntry> reviveEntries;
	private final MessageSender messageSender;

	public PlayerReviver(MessageSender messageSender) {
		super();
		this.messageSender = messageSender;
		this.reviveEntries = new LinkedList<PlayerReviveEntry>();
	}

	public PlayerReviver(List<PlayerReviveEntry> reviveEntries, MessageSender messageSender) {
		super();
		this.reviveEntries = reviveEntries;
		this.messageSender = messageSender;
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
		this.messageSender.sendMessage(MessageId.PLAYER_IS_REVIVED, player.id());
	}

	public void revivePlayerLater(Player player) {
		this.reviveEntries.add(new PlayerReviveEntry(System.currentTimeMillis() + KILL_TIME_MILLISECONDS, player));
	}

}
