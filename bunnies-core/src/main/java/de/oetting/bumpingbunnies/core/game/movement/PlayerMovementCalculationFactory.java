package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerMovementCalculationFactory {

	private final GameObjectInteractor interactionService;
	private final CollisionDetection collisionDetection;
	private final MusicPlayer jumpMusic;

	public PlayerMovementCalculationFactory(GameObjectInteractor interactionService, CollisionDetection collisionDetection, MusicPlayer jumpMusic) {
		this.interactionService = interactionService;
		this.collisionDetection = collisionDetection;
		this.jumpMusic = jumpMusic;
	}

	public PlayerMovement create(Player p) {
		return new PlayerMovement(p, this.interactionService, this.collisionDetection, this.jumpMusic);
	}
}
