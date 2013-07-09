package de.oetting.bumpingbunnies.usecases.game.model;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionHandling;

public class Player implements GameObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);
	private final int speedFaktor;
	private final CollisionHandling collisionHandling;

	private final int halfWidth;
	private final int halfHeight;
	private final int id;
	private final String name;

	private PlayerState state;
	private Player simulatedObject;

	private Rect rect;

	public Player(int id, String name, int speedFaktor) {
		this.name = name;
		this.speedFaktor = speedFaktor;
		this.rect = new Rect();
		this.state = new PlayerState(id);
		this.id = id;
		calculateRect();
		this.halfHeight = ModelConstants.PLAYER_HEIGHT / 2;
		this.halfWidth = ModelConstants.PLAYER_WIDTH / 2;
		this.collisionHandling = new CollisionHandling();
	}

	public Player(Player simulatedObject, int id, String name, int speedFaktor) {
		this(id, name, speedFaktor);
		this.simulatedObject = simulatedObject;
	}

	public void calculateRect() {
		int centerX = this.state.getCenterX();
		int centerY = this.state.getCenterY();
		this.rect.setMinX(centerX - this.halfWidth);
		this.rect.setMaxX(centerX + this.halfWidth);
		this.rect.setMinY(centerY - this.halfHeight);
		this.rect.setMaxY(centerY + this.halfHeight);
	}

	public int getCenterX() {
		return this.state.getCenterX();
	}

	public void setCenterX(int centerX) {
		this.state.setCenterX(centerX);
		calculateRect();
		LOGGER.debug("%s %d", "set", centerX);
	}

	public int getCenterY() {
		return this.state.getCenterY();
	}

	public void setCenterY(int centerY) {
		this.state.setCenterY(centerY);
		calculateRect();
	}

	public void setMovementX(int movementX) {
		this.state.setMovementX(movementX);
	}

	public void increaseYMovement(int delta) {
		this.state.setMovementY(this.state.getMovementY() + delta);
	}

	public void setMovementY(int movementY) {
		this.state.setMovementY(movementY);
	}

	public int getAccelerationX() {
		return this.state.getAccelerationX();
	}

	public void setAccelerationX(int accelerationX) {
		this.state.setAccelerationX(accelerationX);
	}

	public int getAccelerationY() {
		return this.state.getAccelerationY();
	}

	public void setAccelerationY(int accelerationY) {
		this.state.setAccelerationY(accelerationY);
	}

	@Override
	public int maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public int maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public int minX() {
		return this.rect.getMinX();
	}

	@Override
	public int minY() {
		return this.rect.getMinY();
	}

	public void moveNextStep() {
		moveNextStepX();
		moveNextStepY();
		calculateRect();
	}

	public void calculateNextSpeed() {
		this.state.setMovementY(this.state.getMovementY()
				+ this.state.getAccelerationY());

		int newMovementSpeedX = calculateNewMovementSpeedX();
		this.state.setMovementX(newMovementSpeedX);
	}

	private int calculateNewMovementSpeedX() {
		int newMovementSpeedX = this.state.getMovementX()
				+ this.state.getAccelerationX();
		if (newMovementSpeedX > ModelConstants.MOVEMENT * this.speedFaktor) {
			return ModelConstants.MOVEMENT * this.speedFaktor;
		} else if (newMovementSpeedX < -ModelConstants.MOVEMENT
				* this.speedFaktor) {
			return -ModelConstants.MOVEMENT * this.speedFaktor;
		} else {
			return newMovementSpeedX;
		}
	}

	public int movementX() {
		return this.state.getMovementX();
	}

	public int movementY() {
		return this.state.getMovementY();
	}

	/**
	 * Careful: each new simulation will overwrite previous results
	 */
	public GameObject simulateNextStep() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStep();
		return this.simulatedObject;
	}

	public void resetSimulatedObject() {
		this.simulatedObject.setCenterX(this.state.getCenterX());
		this.simulatedObject.setMovementX(this.state.getMovementX());
		this.simulatedObject.setCenterY(this.state.getCenterY());
		this.simulatedObject.setMovementY(this.state.getMovementY());
	}

	public int id() {
		return this.id;
	}

	public PlayerState getState() {
		return this.state;
	}

	@Override
	public int getColor() {
		return this.state.getColor();
	}

	public int centerX() {
		return this.state.getCenterX();
	}

	public int centerY() {
		return this.state.getCenterY();
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
		// PlayerState state = p.getState();
		// state.setScore(state.getScore() + 1);
		// resetPosition(this, p);
	}

	public GameObject simulateNextStepX() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStepX();
		this.simulatedObject.calculateRect();
		return this.simulatedObject;
	}

	public GameObject simulateNextStepY() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStepY();
		this.simulatedObject.calculateRect();
		return this.simulatedObject;
	}

	private void moveNextStepX() {
		this.state.setCenterX(this.state.getCenterX()
				+ this.state.getMovementX());
	}

	private void moveNextStepY() {
		this.state.setCenterY(this.state.getCenterY()
				+ this.state.getMovementY());
	}

	@Override
	public void handleCollisionWithPlayer(Player player,
			CollisionDetection collisionDetection) {
		this.collisionHandling.interactWith(player, this, collisionDetection);
		GameObject simulatedNextStep = player.simulateNextStep();
		if (collisionDetection.isExactlyOverObject(simulatedNextStep, this)) {
			player.interactWithPlayerOnTop(this);
		}
	}

	public boolean isFacingLeft() {
		return this.state.isFacingLeft();
	}

	public void setFacingLeft(boolean facingLeft) {
		this.state.setFacingLeft(facingLeft);
	}

	public String getName() {
		return this.name;
	}

}
