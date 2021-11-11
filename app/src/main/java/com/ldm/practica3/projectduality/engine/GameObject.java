package com.ldm.practica3.projectduality.engine;

import android.graphics.Canvas;

import com.ldm.practica3.projectduality.engine.GameEngine;

public abstract class GameObject {

    public abstract void startGame();

    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine);

    public abstract void onDraw(Canvas canvas);

    public final Runnable onAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public void onAddedToGameUiThread(){
    }

    public final Runnable onRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread(){
    }

}
