package com.ldm.practica3.spacecowboy.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ldm.practica3.spacecowboy.activities.MainActivity;
import com.ldm.practica3.spacecowboy.R;
import com.ldm.practica3.spacecowboy.engine.components.ShipSelection;

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
        UpdateShipSprite();

        previusShip = view.findViewById(R.id.btn_nave_izda);
        previusShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipSelectionCounter--;
                if (shipSelectionCounter < 0) {
                    shipSelectionCounter = ShipSelection.values().length - 1;
                }
                UpdateShipSprite();
            }
        });

        nextShip = view.findViewById(R.id.btn_nave_dcha);
        nextShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shipSelectionCounter++;
                if (shipSelectionCounter > 2) {
                    shipSelectionCounter = 0;
                }
                UpdateShipSprite();
            }
        });
        view.findViewById(R.id.btn_start).setOnClickListener(this);
    }

    void UpdateShipSprite(){
        shipSelection = ShipSelection.values()[shipSelectionCounter];
        Bitmap bitmap;
        switch (shipSelection) {
            case ship0:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
                break;
            case ship1:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player2);
                break;
            case ship2:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player3);
                break;
            default:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
        }


        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setFilterBitmap(false);
        shipImage.setImageDrawable(drawable);
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).startGame(shipSelection);
    }
}
