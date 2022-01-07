package com.ldm.practica3.projectduality.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ldm.practica3.projectduality.activities.MainActivity;
import com.ldm.practica3.projectduality.R;
import com.ldm.practica3.projectduality.engine.components.ShipSelection;

public class MainMenuFragment extends BaseFragment implements View.OnClickListener {

    Button startGame;
    Button previusShip;
    Button nextShip;

    ImageView shipImage;
    boolean secondShip;
    int chosenPlayerImage = R.drawable.player1;
    ShipSelection shipSelection;
    int shipSelectionCounter;

    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //reseting after going back
        shipSelection = ShipSelection.ship0;
        shipSelectionCounter = 0;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shipImage = view.findViewById(R.id.playerImage);

        previusShip = view.findViewById(R.id.btn_nave_izda);
        previusShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipSelectionCounter --;
                if (shipSelectionCounter<0){
                    shipSelectionCounter = ShipSelection.values().length-1;
                }
                shipSelection = ShipSelection.values()[shipSelectionCounter];
                switch (shipSelection) {
                    case ship0:
                        shipImage.setImageResource(R.drawable.player1);
                        break;
                    case ship1:
                        shipImage.setImageResource(R.drawable.player2);
                        break;
                    case ship2:
                        shipImage.setImageResource(R.drawable.player3);
                        break;
                }

            }
        });

        nextShip = view.findViewById(R.id.btn_nave_dcha);
        nextShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shipSelectionCounter ++;
                if (shipSelectionCounter>2){
                    shipSelectionCounter = 0;
                }
                shipSelection = ShipSelection.values()[shipSelectionCounter];
                switch (shipSelection) {
                    case ship0:
                        shipImage.setImageResource(R.drawable.player1);
                        break;
                    case ship1:
                        shipImage.setImageResource(R.drawable.player2);
                        break;
                    case ship2:
                        shipImage.setImageResource(R.drawable.player3);
                        break;
                }
            }
        });
        view.findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).startGame(shipSelection);
    }
}
