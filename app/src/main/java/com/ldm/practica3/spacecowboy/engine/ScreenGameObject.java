package com.ldm.practica3.spacecowboy.engine;

import android.graphics.Rect;


public abstract class ScreenGameObject extends GameObject {

    protected double positionX;
    protected double positionY;

    protected int width;
    protected int height;

    public double radius;

    public abstract void onCollision(GameEngine gameEngine, ScreenGameObject otherObject);

    public Rect mBoundingRect = new Rect(-1, -1, -1, -1);

    public void onPostUpdate(GameEngine gameEngine) {
        mBoundingRect.set(
                (int) positionX,
                (int) positionY,
                (int) positionX + width,
                (int) positionY + height);
    }

    //Todo: add body type

    public boolean checkCollision(ScreenGameObject otherObject) {
        return checkCircularCollision(otherObject);
    }

    private boolean checkCircularCollision(ScreenGameObject other) {
        double distanceX = (positionX + width /2) - (other.positionX + other.width /2);
        double distanceY = (positionY + height /2) - (other.positionY + other.height /2);
        double squareDistance = distanceX*distanceX + distanceY*distanceY;
        double collisionDistance = (radius + other.radius);
        return squareDistance <= collisionDistance*collisionDistance;
    }

    public void SetPos(double x, double y){
        positionX = x - width/2;
        positionY = y - height/2;
    }

}
