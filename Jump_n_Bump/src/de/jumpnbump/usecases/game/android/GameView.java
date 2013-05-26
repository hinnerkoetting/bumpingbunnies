package de.jumpnbump.usecases.game.android;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.businesslogic.GameThread;

public class GameView extends SurfaceView {

	private List<GameScreenSizeChangeListener> sizeListeners = new LinkedList<GameScreenSizeChangeListener>();;

	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setGameThread(GameThread gameThread) {
		getHolder().addCallback(gameThread);
	}

	public void addOnSizeListener(GameScreenSizeChangeListener listener) {
		this.sizeListeners.add(listener);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		for (GameScreenSizeChangeListener l : this.sizeListeners) {
			l.setNewSize(w, h);
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}
}