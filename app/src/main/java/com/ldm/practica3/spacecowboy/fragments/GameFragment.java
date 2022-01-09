package com.ldm.practica3.spacecowboy.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldm.practica3.spacecowboy.activities.MainActivity;
import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.GameView;
import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.components.ShipSelection;
import com.ldm.practica3.spacecowboy.gameObjects.FPSDisplay;
import com.ldm.practica3.spacecowboy.gameObjects.GameController;
import com.ldm.practica3.spacecowboy.gameObjects.Player;
import com.ldm.practica3.spacecowboy.gameObjects.parallax.Background1;
import com.ldm.practica3.spacecowboy.gameObjects.parallax.Background2;
import com.ldm.practica3.spacecowboy.gameObjects.parallax.Background3;
import com.ldm.practica3.spacecowboy.gameObjects.parallax.Foreground1;
import com.ldm.practica3.spacecowboy.gameObjects.parallax.Foreground2;
import com.ldm.practica3.spacecowboy.gameObjects.parallax.Foreground3;
import com.ldm.practica3.spacecowboy.input.JoystickInputController;
import com.ldm.practica3.spacecowboy.sound.GameEvent;

public class GameFragment extends BaseFragment implements View.OnClickListener {
    private final int bg = R.drawable.bg;
    private final int fg = R.drawable.fg;

    private GameEngine gameEngine;
    public TextView points;
    ShipSelection shipSelection;

    public GameFragment(ShipSelection selectedShip) {
        this.shipSelection = selectedShip;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);

        ImageView switchButton = view.findViewById(R.id.changeIcon);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.changebutton);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setFilterBitmap(false);
        switchButton.setImageDrawable(drawable);

        /*points= (TextView) view.findViewById(R.id.points);
        lives= (TextView) view.findViewById(R.id.lives);*/
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);

                TextView points = (TextView) view.findViewById(R.id.points);
                points.setText("0");

                gameEngine = new GameEngine(getActivity(), gameView);
                gameEngine.setSoundManager(((MainActivity) getActivity()).getSoundManager());

                //Parallax Effect
                gameEngine.addGameObject(new Background1(gameEngine, bg, 20));
                gameEngine.addGameObject(new Background2(gameEngine, bg, 20));
                gameEngine.addGameObject(new Background3(gameEngine, bg, 20));

                gameEngine.addGameObject(new Foreground1(gameEngine, fg, 50));
                gameEngine.addGameObject(new Foreground2(gameEngine, fg, 50));
                gameEngine.addGameObject(new Foreground3(gameEngine, fg, 50));

                gameEngine.setUI(points);

                gameEngine.addGameObject(new GameController(gameEngine));
                gameEngine.addGameObject(new Player(gameEngine, PlayerPreparations()));
                gameEngine.addGameObject(new FPSDisplay(gameEngine));

                gameEngine.setInputController(new JoystickInputController(getView(), gameEngine));

                gameEngine.startGame();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
            gameEngine.onGameEvent(GameEvent.Click);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (gameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            gameEngine.onGameEvent(GameEvent.Defeat);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (gameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        gameEngine.pauseGame();
        new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogTheme)
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gameEngine.stopGame();
                        ((MainActivity) getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        gameEngine.resumeGame();
                    }
                })
                .create().show();
    }

    private void playOrPause() {
        ImageButton button = (ImageButton) getView().findViewById(R.id.btn_play_pause);
        if (gameEngine.isPaused()) {
            gameEngine.resumeGame();
            //button.setText(R.string.pause);
        } else {
            gameEngine.pauseGame();
            //button.setText(R.string.resume);
        }
    }

    private int[] PlayerPreparations() {
        int[] resources = new int[2];
        switch (shipSelection) {
            case ship0:
                resources[0] = R.drawable.player1;
                resources[1] = R.drawable.player1inv;
                break;
            case ship1:
                resources[0] = R.drawable.player2;
                resources[1] = R.drawable.player2inv;
                break;
            case ship2:
                resources[0] = R.drawable.player3;
                resources[1] = R.drawable.player3inv;
                break;
            default:
                resources[0] = R.drawable.player1;
                resources[1] = R.drawable.heart;
                break;
        }
        return resources;
    }
}
