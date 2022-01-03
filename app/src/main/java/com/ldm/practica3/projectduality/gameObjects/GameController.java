package com.ldm.practica3.projectduality.gameObjects;


import android.graphics.Canvas;
import android.util.Log;

import com.ldm.practica3.projectduality.engine.GameObject;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.gameObjects.enemies.asteroids.A_Big;
import com.ldm.practica3.projectduality.gameObjects.enemies.asteroids.A_Medium;
import com.ldm.practica3.projectduality.gameObjects.enemies.asteroids.A_Small;
import com.ldm.practica3.projectduality.gameObjects.enemies.asteroids.Asteroid;
import com.ldm.practica3.projectduality.gameObjects.enemies.asteroids.AsteroidType;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.BE_M_Kamikaze;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.BE_M_Otaku;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.BE_R_Faye;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.BE_R_Spike;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.BE_R_Tiger;
import com.ldm.practica3.projectduality.gameObjects.enemies.Enemy;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.MeleeEnemy;
import com.ldm.practica3.projectduality.gameObjects.enemies.basicEnemies.RangedEnemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 1000;
    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private List<Enemy> enemyPool = new ArrayList<Enemy>();

    private List<A_Small> smallAsteroidPool = new ArrayList<A_Small>();
    private List<A_Medium> mediumAsteroidPool = new ArrayList<A_Medium>();

    private GameEngine gameEngine;

    /*private List<Enemy> enemyPool = new ArrayList<Enemy>();
    private List<Enemy> enemyPool = new ArrayList<Enemy>();*/
    private int enemiesSpawned;

    public GameController(GameEngine gameEngine) {
        // We initialize the pool of items now
        this.gameEngine = gameEngine;
        for (int i = 0; i < 20; i++) {
            asteroidPool.add(new A_Big(this, gameEngine));
            mediumAsteroidPool.add(new A_Medium(this, gameEngine));
            smallAsteroidPool.add(new A_Small(this, gameEngine));
        }

        FillEnemyPool(20, gameEngine);
        Log.d("a", "GameController: ");
    }

    private void FillEnemyPool(int numEnemies, GameEngine gameEngine) {
        for (int i = 0; i < numEnemies; i++) {
            Random rand = new Random();
            switch (rand.nextInt(8)) {
                case 0:
                    enemyPool.add(new A_Big(this, gameEngine));
                    break;
                case 1:
                    enemyPool.add(new A_Medium(this, gameEngine));
                    break;
                case 2:
                    enemyPool.add(new A_Small(this, gameEngine));
                    break;
                case 3:
                    enemyPool.add(new BE_M_Kamikaze(this, gameEngine));
                    break;
                case 4:
                    enemyPool.add(new BE_M_Otaku(this, gameEngine));
                    break;
                case 5:
                    enemyPool.add(new BE_R_Faye(this, gameEngine));
                    break;
                case 6:
                    enemyPool.add(new BE_R_Spike(this, gameEngine));
                    break;
                case 7:
                    enemyPool.add(new BE_R_Tiger(this, gameEngine));
                    break;

            }
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
            Enemy e = enemyPool.remove(0);

            switch (e.enemyType){

                case Asteroid:
                    ((Asteroid)e).init(gameEngine);
                    break;
                case Ranged:
                    ((RangedEnemy)e).init(gameEngine);
                    break;
                case Melee:
                    ((MeleeEnemy)e).init(gameEngine);
                    break;
                case Boss:
                    break;
            }
            gameEngine.addGameObject(e);
            enemiesSpawned++;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Enemy enemy) {

        switch (enemy.enemyType) {
            case Asteroid:
                switch (((Asteroid) enemy).asteroidType){

                    case Big:
                        asteroidPool.add((Asteroid) enemy);
                        break;
                    case Medium:
                        mediumAsteroidPool.add((A_Medium) enemy);
                        break;
                    case Small:
                        smallAsteroidPool.add((A_Small) enemy);
                        break;
                }
                break;
            case Ranged:
            case Melee:
                enemyPool.add(enemy);
                break;
            case Boss:
                break;
        }
    }

    public void onAsteroidDestroyed(AsteroidType asteroidType, double asteroidPosX, double asteroidPosY) {
        switch (asteroidType) {
            case Big:
                A_Medium a_medium = mediumAsteroidPool.remove(0);
                a_medium.SpawnAtPosition(asteroidPosX, asteroidPosY);
                gameEngine.addGameObject(a_medium);
                enemiesSpawned++;
                A_Small a_small = smallAsteroidPool.remove(0);
                a_small.SpawnAtPosition(asteroidPosX, asteroidPosY);
                gameEngine.addGameObject(a_small);
                enemiesSpawned++;
                break;
            case Medium:
                A_Small a_smallA = smallAsteroidPool.remove(0);
                a_smallA.SpawnAtPosition(asteroidPosX, asteroidPosY);
                gameEngine.addGameObject(a_smallA);
                enemiesSpawned++;
                A_Small a_smallB = smallAsteroidPool.remove(0);
                a_smallB.SpawnAtPosition(asteroidPosX, asteroidPosY);
                gameEngine.addGameObject(a_smallB);
                enemiesSpawned++;
                break;
            case Small:
                break;
        }
    }
}
