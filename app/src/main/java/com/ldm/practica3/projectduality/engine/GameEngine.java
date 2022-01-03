package com.ldm.practica3.projectduality.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.activities.MainActivity;
import com.ldm.practica3.projectduality.fragments.GameFragment;
import com.ldm.practica3.projectduality.gameObjects.Player;
import com.ldm.practica3.projectduality.input.InputController;
import com.ldm.practica3.projectduality.sound.GameEvent;
import com.ldm.practica3.projectduality.sound.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();
    private List<Collision> detectedCollisions = new ArrayList<Collision>();
    private QT quadTree = new QT();

    private UpdateThread updateThread;
    private DrawThread drawThread;
    public InputController inputController;
    private final GameView gameView;

    public Random random = new Random();

    private SoundManager soundManager;

    public int width;
    public int height;
    public double pixelFactor;

    private Activity mainActivity;

    private int points;
    private int playerLives;

    public TextView pointsTextView;
    public TextView livesTextView;

    public GameEngine(Activity activity, GameView gameView) {
        mainActivity = activity;

        this.gameView = gameView;
        this.gameView.setGameObjects(this.gameObjects);

        quadTree.init();

        this.width = this.gameView.getWidth()
                - this.gameView.getPaddingRight() - this.gameView.getPaddingLeft();
        this.height = this.gameView.getHeight()
                - this.gameView.getPaddingTop() - this.gameView.getPaddingTop();

        quadTree.setArea(new Rect(0, 0, width, height));

        this.pixelFactor = this.height / 400d;

        points =0;
    }



    public void startGame() {
        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int nugameObjects = gameObjects.size();
        for (int i = 0; i < nugameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        updateThread = new UpdateThread(this);
        updateThread.start();

        // Start the drawing thread
        drawThread = new DrawThread(this);
        drawThread.start();
    }

    public void stopGame() {
        if (updateThread != null) {
            updateThread.stopGame();
        }
        if (drawThread != null) {
            drawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (updateThread != null) {
            updateThread.pauseGame();
        }
        if (drawThread != null) {
            drawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (updateThread != null) {
            updateThread.resumeGame();
        }
        if (drawThread != null) {
            drawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        } else {
            addGameObjectNow(gameObject);
        }
        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        int nugameObjects = gameObjects.size();
        for (int i = 0; i < nugameObjects; i++) {
            GameObject go =  gameObjects.get(i);
            go.onUpdate(elapsedMillis, this);
            if(go instanceof ScreenGameObject) {
                ((ScreenGameObject) go).onPostUpdate(this);
            }
        }
        checkCollisions();
        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                GameObject objectToRemove = objectsToRemove.remove(0);
                gameObjects.remove(objectToRemove);
                if (objectToRemove instanceof  ScreenGameObject) {
                    quadTree.removeGameObject((ScreenGameObject) objectToRemove);
                }
            }
            while (!objectsToAdd.isEmpty()) {
                GameObject gameObject = objectsToAdd.remove(0);
                addGameObjectNow(gameObject);
            }
        }
    }

    public void onDraw() {
        gameView.draw();
    }

    public boolean isRunning() {
        return updateThread != null && updateThread.isGameRunning();
    }

    public boolean isPaused() {
        return updateThread != null && updateThread.isGamePaused();
    }

    public Context getContext() {
        return gameView.getContext();
    }

    private void checkCollisions() {
        // Release the collisions from the previous step
        while (!detectedCollisions.isEmpty()) {
            Collision.release(detectedCollisions.remove(0));
        }
        quadTree.checkCollisions(this, detectedCollisions);
    }

    private void addGameObjectNow (GameObject object) {
        gameObjects.add(object);
        if (object instanceof ScreenGameObject) {
            ScreenGameObject sgo = (ScreenGameObject) object;

            quadTree.addGameObject(sgo);
        }
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void setInputController(InputController inputController) {
        this.inputController = inputController;
    }

    public void setUI(TextView p, TextView l){
        pointsTextView = p;
        livesTextView = l;
    }

    public void onGameEvent (GameEvent gameEvent) {
        // We notify all the GameObjects
        // Also the sound manager
        soundManager.playSoundForGameEvent(gameEvent);
    }

    public void AddPoints(int value){
        points += value;
        pointsTextView.setText("Points: " + Integer.toString(points));
    }
    public int GetPoints(){
        return points;
    }
    public void SetLives(int value){
        playerLives = value;
        livesTextView.setText("Lives: " +Integer.toString(playerLives));
    }

    public int GetLives(){
        return playerLives;
    }
}
