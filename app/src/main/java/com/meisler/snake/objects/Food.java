package com.meisler.snake.objects;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

/**
 * Created by Meisler on 05 ספטמבר 2016.
 */
public class Food {
    public Point point;
    public int value;
    long startTime;
    Random r;
    public Food(ArrayList<Point> notValidPoints) {
        r = new Random();
        while(true)
        {
            point = new Point(r.nextInt(),r.nextInt());
            boolean isValid = true;
            for (Point p:notValidPoints) {
                if (p.getX() == point.getX() && p.getY() == point.getY())
                {
                    isValid = false;
                }
            }
            if(isValid)
                break;
        }
        startTime = System.currentTimeMillis();
    }

    public int eaten(long endTime , int snakeSize)
    {
        value = ((endTime - startTime) / 1000 >  1 ? 1 : (Math.abs(r.nextInt()) % snakeSize) + 1); // The score formula
        return value;
    }
}
