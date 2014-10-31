package de.oetting.bumpingbunnies.model.game.objects;

public class PlayerSimulation implements GameObject {

	private PlayerState state;
	private final int halfWidth;
	private final int halfHeight;

	public PlayerSimulation(PlayerState state, int halfWidth, int halfHeight) {
		this.state = state;
		this.halfWidth = halfWidth;
		this.halfHeight = halfHeight;
	}

	@Override
	public long maxX() {
		long centerX = this.state.getCenterX();
		return centerX + this.halfWidth;
	}

	@Override
	public long maxY() {
		long centerY = this.state.getCenterY();
		return centerY + this.halfHeight;
	}

	@Override
	public long minX() {
		long centerX = this.state.getCenterX();
		return centerX - this.halfWidth;
	}

	@Override
	public long minY() {
		long centerY = this.state.getCenterY();
		return centerY - this.halfHeight;
	}

	@Override
	public int accelerationOnThisGround() {
		throw new IllegalArgumentException("The simulated player must not interact with other players.");
	}

	public PlayerSimulation moveNextStep(PlayerState currentPlayerState) {
		currentPlayerState.copyContentTo(state);
		state.moveNextStep();
		return this;
	}

	public GameObject moveNextStepX(PlayerState currentPlayerState) {
		currentPlayerState.copyContentTo(state);
		state.moveNextStepX();
		return this;
	}

	public GameObject moveNextStepY(PlayerState currentPlayerState) {
		currentPlayerState.copyContentTo(state);
		state.moveNextStepY();
		return this;
	}

	@Override
	public void setCenterX(long gameX) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setCenterY(long gameY) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMinY(long newBottomY) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMinX(long newLeft) {
		throw new IllegalArgumentException();
	}

	@Override
	public int id() {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMaxX(long newRight) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMaxY(long newTopY) {
		throw new IllegalArgumentException();
	}

}
