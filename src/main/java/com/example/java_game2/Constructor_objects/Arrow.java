package com.example.java_game2.Constructor_objects;

import javafx.scene.shape.*;

public class Arrow extends Path {
    private static final double defaultArrowLength = 30.0;

    private final int x, y;

    public Arrow(int startX, int startY, double arrowLength){

        strokeWidthProperty().set(3);
        double endX = startX + arrowLength;
        x = startX; y = startY;
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, startY));



    }

    public Arrow(int startX, int startY){ this(startX, startY, defaultArrowLength); }

    @Override
    public String toString() {
        return "Arrow{" +
                "x='" + this.x + '\'' +
                ", y=" + this.y +
                '}';
    }
}