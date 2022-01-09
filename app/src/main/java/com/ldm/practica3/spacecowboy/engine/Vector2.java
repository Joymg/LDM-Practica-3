package com.ldm.practica3.spacecowboy.engine;

public class Vector2 {
    //https://noobtuts.com/java/vector2-class
    // Members
    public float x;
    public float y;

    // Constructors
    public Vector2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Compare two vectors
    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }

    public static double distance(Vector2 a, Vector2 b) {
        float v0 = b.x - a.x;
        float v1 = b.y - a.y;
        return Math.sqrt(v0*v0 + v1*v1);
    }

    public Vector2 multiply(double d){
        return new Vector2((float) d*x,(float)d*y);
    }

    public void normalize() {
        // sets length to 1
        //
        double length = Math.sqrt(x*x + y*y);

        if (length != 0.0) {
            float s = 1.0f / (float)length;
            x = x*s;
            y = y*s;
        }
    }

    public static Vector2 vecFromAngle(double angle){
        double theta = Math.toRadians(angle);

        float cs = (float)Math.cos(theta);
        float sn = (float) Math.sin(theta);

        return new Vector2(sn,-cs);
    }

    public static double AngleBetween(Vector2 v, Vector2 w){
        return Math.atan2(w.y - v.y, w.x - v.x);
    }

    public static double Dot(Vector2 v, Vector2 w){
        return (v.x * w.x) + (v.y + w.y);
    }

    public double GetMagnitude(){
        return Math.sqrt(x * x + y * y);
    }
}
