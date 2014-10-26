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

	@Override
	public void interactWithPlayerOnTop(Player p) {
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

}
