package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.steps.GameStepAction;
import de.oetting.bumpingbunnies.core.game.steps.PlayerReviveEntry;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerReviver implements GameStepAction {

	private static final int KILL_TIME_MILLISECONDS = 1000;

	private final List<PlayerReviveEntry> reviveEntries;
	private final NetworkSendControl sendControl;

	public PlayerReviver(NetworkSendControl sendControl) {
		super();
		this.sendControl = sendControl;
		this.reviveEntries = new LinkedList<PlayerReviveEntry>();
	}

	public PlayerReviver(List<PlayerReviveEntry> reviveEntries,
			NetworkSendControl sendControl) {
		super();
		this.reviveEntries = reviveEntries;
		this.sendControl = sendControl;
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
		this.sendControl.sendMessage(MessageId.PLAYER_IS_REVIVED, player.id());
	}

	public void revivePlayerLater(Player player) {
		this.reviveEntries.add(new PlayerReviveEntry(System.currentTimeMillis() + KILL_TIME_MILLISECONDS, player));
	}

}
