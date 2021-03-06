package com.ldm.practica3.spacecowboy.gameObjects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ldm.practica3.spacecowboy.engine.GameEngine;
import com.ldm.practica3.spacecowboy.engine.ScreenGameObject;

public abstract class Sprite extends ScreenGameObject {

    protected double rotation;

    protected double pixelFactor;

    protected Bitmap bitmap;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.width = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.height = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        radius = Math.max(height, width)/2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - width
                || positionY < - height) {
            return;
        }
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + width/2), (float) (positionY + height/2));

        /*if (this instanceof Actor){
            switch (((Actor) this).state) {
                case Determined:
                    canvas.drawBitmap(bitmap, matrix, null);
                    break;
                case Quantic:
                    Paint paint = new Paint();
                    paint.setColor(Color.rgb(0,255,0));
                    canvas.drawBitmap(bitmap, matrix, new Paint(Color.GREEN));
                    break;
            }
        }
        else*/
            canvas.drawBitmap(bitmap, matrix, null);
        //Shows collider
       /* Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(255,0,0));
        paint.setStrokeWidth(2);
        canvas.drawRect(this.mBoundingRect, paint);*/

    }
}