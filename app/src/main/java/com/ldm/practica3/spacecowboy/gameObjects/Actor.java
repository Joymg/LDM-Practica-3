package com.ldm.practica3.spacecowboy.gameObjects;

import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.components.Faction;
import com.ldm.practica3.spacecowboy.engine.components.MatterState;

public abstract class Actor extends Sprite{

    protected Faction faction;
    public MatterState state;

    protected Actor(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
    }
}
