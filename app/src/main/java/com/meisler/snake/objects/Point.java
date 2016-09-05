package com.meisler.snake.objects;

/**
 * Created by Meisler on 04 ספטמבר 2016.
 */
public class Point {
    private int x;
    private int y;
    static int maxX = Integer.MAX_VALUE;
    static int maxY = Integer.MAX_VALUE;
    static int radius = 10;

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        Point.radius = radius;
    }

    public static int getMaxX() {
        return maxX;
    }

    public static void setMaxX(int maxX) {
        Point.maxX = maxX;
    }


    public static int getMaxY() {
        return maxY;
    }

    public static void setMaxY(int maxY) {
        Point.maxY = maxY;
    }

    public int getRealX() {
        return x * 2 * radius + radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = (x % maxX + maxX) % maxX;
    }

    public int getRealY() {
        return y * 2 * radius + radius;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = (y % maxY + maxY) % maxY;
    }

    public Point(int x, int y) {
        this.x = (x % maxX + maxX) % maxX;
        this.y = (y % maxY + maxY) % maxY;
    }
}
