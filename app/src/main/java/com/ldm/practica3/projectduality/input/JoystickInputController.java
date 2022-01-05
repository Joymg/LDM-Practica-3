package com.ldm.practica3.projectduality.input;

import android.view.MotionEvent;
import android.view.View;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.gameObjects.UIElement;

public class JoystickInputController extends InputController {

    private float startingPositionX;
    private float startingPositionY;

    private final double maxDistance;

    GameEngine gameEngine;
    UIElement joystick;

    public JoystickInputController(View view, GameEngine gameEngine) {
        view.findViewById(R.id.joystick_main).setOnTouchListener(new JoystickTouchListener());
        view.findViewById(R.id.joystick_touch).setOnTouchListener(new FireButtonTouchListener());

        double pixelFactor = view.getHeight() / 400d;
        maxDistance = 25 * pixelFactor;

        this.gameEngine = gameEngine;
        joystick = new UIElement(gameEngine, R.drawable.joystick);
        joystick.SetPos(gameEngine.width / 2f, gameEngine.height / 1.2f);
        gameEngine.addGameObject(joystick);
    }

    private class JoystickTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                startingPositionX = event.getX(0);
                startingPositionY = event.getY(0);
            } else if (action == MotionEvent.ACTION_UP) {
                horizontalFactor = 0;
                verticalFactor = 0;
            } else if (action == MotionEvent.ACTION_MOVE) {
                // Get the proportion to the max
                horizontalFactor = (event.getX(0) - startingPositionX) / maxDistance;
                if (horizontalFactor > 1) {
                    horizontalFactor = 1;
                } else if (horizontalFactor < -1) {
                    horizontalFactor = -1;
                }
                verticalFactor = (event.getY(0) - startingPositionY) / maxDistance;
                if (verticalFactor > 1) {
                    verticalFactor = 1;
                } else if (verticalFactor < -1) {
                    verticalFactor = -1;
                }
            }

            double x = (gameEngine.width / 2f) + maxDistance * horizontalFactor;
            double y = (gameEngine.height / 1.2f) + maxDistance * verticalFactor;
            joystick.SetPos(x, y);

            return true;
        }
    }

    private class FireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                isFiring = true;
            } else if (action == MotionEvent.ACTION_UP) {
                isFiring = false;
            }
            return true;
        }
    }
}


