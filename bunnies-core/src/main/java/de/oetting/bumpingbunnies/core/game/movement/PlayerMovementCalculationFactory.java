package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;

public class PlayerMovementCalculationFactory {

	private final GameObjectInteractor interactionService;
	private final CollisionDetection collisionDetection;
	private final MusicPlayer jumpMusic;

	public PlayerMovementCalculationFactory(GameObjectInteractor interactionService, CollisionDetection collisionDetection,
			MusicPlayer jumpMusic) {
		super();
		this.interactionService = interactionService;
		this.collisionDetection = collisionDetection;
		this.jumpMusic = jumpMusic;
	}

	public PlayerMovementCalculation create(Player p) {
		return new PlayerMovementCalculation(p, this.interactionService, this.collisionDetection, this.jumpMusic);
	}
}