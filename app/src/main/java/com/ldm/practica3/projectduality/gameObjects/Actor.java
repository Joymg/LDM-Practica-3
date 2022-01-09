package com.ldm.practica3.projectduality.gameObjects;

import com.ldm.practica3.projectduality.engine.GameEngine;
import com.ldm.practica3.projectduality.engine.components.Faction;
import com.ldm.practica3.projectduality.engine.components.MatterState;

public abstract class Actor extends Sprite{

    protected Faction faction;
    public MatterState state;

    protected Actor(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
    }
}
