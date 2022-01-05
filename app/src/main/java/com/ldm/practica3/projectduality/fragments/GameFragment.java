package com.ldm.practica3.projectduality.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ldm.practica3.projectduality.activities.MainActivity;
import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.GameView;
import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.gameObjects.FPSDisplay;
import com.ldm.practica3.projectduality.gameObjects.GameController;
import com.ldm.practica3.projectduality.gameObjects.Player;
import com.ldm.practica3.projectduality.gameObjects.parallax.Background1;
import com.ldm.practica3.projectduality.gameObjects.parallax.Background2;
import com.ldm.practica3.projectduality.gameObjects.parallax.Background3;
import com.ldm.practica3.projectduality.gameObjects.parallax.BackgroundBase;
import com.ldm.practica3.projectduality.gameObjects.TMPForeground;
import com.ldm.practica3.projectduality.gameObjects.parallax.Foreground1;
import com.ldm.practica3.projectduality.gameObjects.parallax.Foreground2;
import com.ldm.practica3.projectduality.gameObjects.parallax.Foreground3;
import com.ldm.practica3.projectduality.input.JoystickInputController;

public class GameFragment extends BaseFragment implements View.OnClickListener {
    private final int bg = R.drawable.bg;
    private final int fg = R.drawable.fg;

    private GameEngine gameEngine;
    public TextView points;
    public TextView lives;

    public GameFragment() {
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

                TextView points= (TextView) view.findViewById(R.id.points);
                points.setText("Points: 0");
                TextView lives= (TextView) view.findViewById(R.id.lives);

                gameEngine = new GameEngine(getActivity(), gameView);
                gameEngine.setSoundManager(((MainActivity) getActivity()).getSoundManager());
                gameEngine.setInputController(new JoystickInputController(getView()));


                //Parallax Effect
                gameEngine.addGameObject(new Background1(gameEngine,bg,50));
                gameEngine.addGameObject(new Background2(gameEngine,bg,50));
                gameEngine.addGameObject(new Background3(gameEngine,bg,50));
                gameEngine.addGameObject(new Foreground1(gameEngine,fg,20));
                gameEngine.addGameObject(new Foreground2(gameEngine,fg,20));
                gameEngine.addGameObject(new Foreground3(gameEngine,fg,20));

                gameEngine.setUI(points,lives);

                gameEngine.addGameObject(new Player(gameEngine));
                gameEngine.addGameObject(new FPSDisplay(gameEngine));
                gameEngine.addGameObject(new GameController(gameEngine));
                gameEngine.startGame();



            }
        });



    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (gameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
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
        new AlertDialog.Builder(getActivity())
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
                .create()
                .show();

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
}
