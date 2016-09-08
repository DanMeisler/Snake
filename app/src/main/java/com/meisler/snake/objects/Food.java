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
    int notValidPointsSize;
    public Food(ArrayList<Point> notValidPoints) {
        startTime = System.currentTimeMillis();
        r = new Random();
        notValidPointsSize = notValidPoints.size();
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


    }

    public int eaten(long endTime)
    {
        value = ((endTime - startTime) / 1000 >  1 ? 1 : (Math.abs(r.nextInt()) % notValidPointsSize) + 1); // The score formula
        return value;
    }
}
