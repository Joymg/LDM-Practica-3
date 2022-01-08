package com.ldm.practica3.projectduality.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.activities.MainActivity;

public class EndGameFragment extends BaseFragment{

    Button mainMenu;
    Button restart;

    int points;
    TextView pointsTextView;


    public EndGameFragment(int gamePoints){
        points = gamePoints;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_end_game, container, false);

        mainMenu = rootView.findViewById(R.id.mainMenuButton);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).navigateToFragment(new MainMenuFragment());
            }
        });
        restart = rootView.findViewById(R.id.restartButton);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).navigateBack();
            }
        });
        pointsTextView = rootView.findViewById(R.id.score);
        pointsTextView.setText(Integer.toString(points));

        return rootView;
    }
}
