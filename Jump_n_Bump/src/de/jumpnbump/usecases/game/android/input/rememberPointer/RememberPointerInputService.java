package de.jumpnbump.usecases.game.android.input.rememberPointer;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.calculation.CoordinatesCalculation;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.android.input.PathFinder.PathFinder;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class RememberPointerInputService extends AbstractTouchService implements
		GameScreenSizeChangeListener {

	private PathFinder pathFinder;
	private RememberPointerState state;

	public RememberPointerInputService(PlayerMovementController playerMovement,
			PathFinder pathFinder, CoordinatesCalculation coordinateCalculations) {
		super(playerMovement, coordinateCalculations);
		this.pathFinder = pathFinder;
		this.state = new RememberPointerState();
	}

	@Override
	public void executeUserInput() {
		if (this.state.isThereNewWaypoint()) {
			handleHorizontalMovement();
			handleVerticalMovement();
			if (isWaypointReached()) {
				this.state.waypointIsReached();
			}
		}
	}

	private boolean isWaypointReached() {
		return touchesPlayerThisHorizontalPosition(this.state
				.getRememberedWaypointX())
				&& touchesPlayerThisVerticalPosition(this.state
						.getRememberedWaypointY());
	}

	private void handleVerticalMovement() {
		if (touchesPlayerThisVerticalPosition(this.state
				.getRememberedWaypointY())) {
			moveDown();
		} else {
			goToRememberedYPosition();
		}
	}

	private void handleHorizontalMovement() {
		if (touchesPlayerThisHorizontalPosition(this.state
				.getRememberedWaypointX())) {
			removeHorizontalMovement();
		} else {
			goToRememberedXPosition();
		}
	}

	private void goToRememberedYPosition() {
		if (this.pathFinder.canBeReachedByJumping(
				this.state.getRememberedWaypointX(),
				this.state.getRememberedWaypointY())) {
			moveUp();
		} else {
			moveDown();
		}
	}

	private void goToRememberedXPosition() {
		double playerX = getMovedPlayer().getCenterX();
		if (this.state.getRememberedWaypointX() < playerX) {
			moveLeft();
		} else {
			moveRight();
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		double x = translateToGameXCoordinate(motionEvent);
		double y = translateToGameYCoordinate(motionEvent);
		this.state.setNewWaypoint(x, y);
	}

}
