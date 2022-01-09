package com.ldm.practica3.spacecowboy.activities;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.components.ShipSelection;
import com.ldm.practica3.spacecowboy.fragments.BaseFragment;
import com.ldm.practica3.spacecowboy.fragments.GameFragment;
import com.ldm.practica3.spacecowboy.fragments.MainMenuFragment;
import com.ldm.practica3.spacecowboy.sound.SoundManager;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_FRAGMENT = "content";


    private TextView points;
    private TextView lives;

    public TextView getPointsTextView() {
        return points;
    }

    public TextView getLivesTextView() {
        return lives;
    }

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundManager = new SoundManager(getApplicationContext());

        points= (TextView) findViewById(R.id.points);
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void startGame(ShipSelection shipSelection) {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment( new GameFragment(shipSelection));
    }

    public void navigateToFragment(Fragment dst) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, dst, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        final BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null /*|| !fragment.onBackPressed()*/) {
            super.onBackPressed();
        }
    }

    public void navigateBack() {
        // Do a push on the navigation history
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
            else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }
}
