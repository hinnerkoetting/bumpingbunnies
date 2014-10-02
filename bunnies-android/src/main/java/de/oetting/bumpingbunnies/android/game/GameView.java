package de.oetting.bumpingbunnies.android.game;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import de.oetting.bumpingbunnies.android.graphics.AndroidDrawer;
import de.oetting.bumpingbunnies.core.graphics.GameScreenSizeChangeListener;

public class GameView extends SurfaceView {

	private List<GameScreenSizeChangeListener> sizeListeners = new LinkedList<GameScreenSizeChangeListener>();

	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setCallback(AndroidDrawer drawer) {
		getHolder().addCallback(drawer);
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