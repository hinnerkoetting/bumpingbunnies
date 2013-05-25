package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerMovement {

	private static final int MOVEMENT = 10;
	private final World world;
	private final Player movedPlayer;
	private final CollisionDetection collision;

	public PlayerMovement(World world, Player movedPlayer,
			CollisionDetection collision) {
		this.world = world;
		this.movedPlayer = movedPlayer;
		this.collision = collision;
	}

	public void nextStep() {

	}

	public void tryMoveRight() {
		doMoveRight();
		if (this.collision.coolidesWithAnything(this.movedPlayer)) {
			doMoveLeft();
		}
	}

	private void doMoveRight() {
		this.movedPlayer.setCenterX(this.movedPlayer.getCenterX() + MOVEMENT);
	}

	public void tryMoveLeft() {
		doMoveLeft();
		if (this.collision.coolidesWithAnything(this.movedPlayer)) {
			tryMoveRight();
		}
	}

	private void doMoveLeft() {
		this.movedPlayer.setCenterX(this.movedPlayer.getCenterX() - MOVEMENT);
	}

	public void tryMoveUp() {
		doMoveUp();
		if (this.collision.coolidesWithAnything(this.movedPlayer)) {
			doMoveDown();
		}
	}

	private void doMoveUp() {
		this.movedPlayer.setCenterY(this.movedPlayer.getCenterY() - MOVEMENT);
	}

	public void tryMoveDown() {
		doMoveDown();
		if (this.collision.coolidesWithAnything(this.movedPlayer)) {
			doMoveUp();
		}
	}

	private void doMoveDown() {
		this.movedPlayer.setCenterY(this.movedPlayer.getCenterY() + MOVEMENT);
	}
}
