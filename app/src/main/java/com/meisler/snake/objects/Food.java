package com.meisler.snake.objects;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Meisler on 05 ספטמבר 2016.
 */
public class Food {
    public Point point;
    public Food(ArrayList<Point> notValidPoints) {
        Random r = new Random();
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
}
