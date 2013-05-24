package de.jumpnbump.usecases.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

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

}