package com.ldm.practica3.projectduality.gameObjects;


import android.graphics.Canvas;
import android.util.Log;

import com.ldm.practica3.projectduality.engine.GameObject;
import com.ldm.practica3.projectduality.engine.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 1000;
    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    /*private List<Enemy> enemyPool = new ArrayList<Enemy>();
    private List<Enemy> enemyPool = new ArrayList<Enemy>();
    private List<Enemy> enemyPool = new ArrayList<Enemy>();*/
    private int enemiesSpawned;

    public GameController(GameEngine gameEngine) {
        // We initialize the pool of items now
        for (int i=0; i<20; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }
    }

    @Override
    public void startGame() {
        currentMillis = 0;
        enemiesSpawned = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        long waveTimestamp = enemiesSpawned * TIME_BETWEEN_ENEMIES;
        if (currentMillis > waveTimestamp) {

            //Log.d("GC", "onUpdate: " + asteroidPool.size());
            // Spawn a new enemy
            Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            enemiesSpawned++;
            return;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Enemy enemy) {

        switch (enemy.enemyType){
            case 0:
                asteroidPool.add((Asteroid) enemy);
                break;
        }
    }
}
