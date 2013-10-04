package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerReviver implements GameStepAction, PlayerJoinListener {

	private final List<PlayerReviveEntry> reviveEntries;
	private final List<? extends RemoteSender> senderList;

	public PlayerReviver(List<? extends RemoteSender> senderList) {
		super();
		this.senderList = senderList;
		this.reviveEntries = new LinkedList<PlayerReviveEntry>();
	}

	public PlayerReviver(List<? extends RemoteSender> senderList, List<PlayerReviveEntry> reviveEntries) {
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
		for (RemoteSender sender : this.senderList) {
			new PlayerIsRevivedSender(sender).sendMessage(player.id());
		}
	}

	public void revivePlayerLater(Player player) {
		this.reviveEntries.add(new PlayerReviveEntry(System.currentTimeMillis() + BunnyDelayedReviver.KILL_TIME_MILLISECONDS, player));
	}

	@Override
	public void newPlayerJoined(Player p) {
		revivePlayerLater(p);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		// nothing needs to happen
	}

}
