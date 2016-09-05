package com.meisler.snake.objects;

import java.util.ArrayList;

/**
 * Created by Meisler on 04 ספטמבר 2016.
 */
public class Snake {
    public ArrayList<Point> points;
    private Direction direction;

    public Snake()
    {
        points = new ArrayList<Point>();
        points.add(new Point(Point.getMaxX() / 2 ,Point.getMaxY() / 2));
        points.add(new Point(Point.getMaxX() / 2 ,Point.getMaxY() / 2 - 1));
        direction = Direction.Down;
    }

    public void eat()
    {
        Point lastPoint = points.get(points.size() - 1);
        points.add(new Point(lastPoint.getX(),lastPoint.getY()));
    }

    public void move()
    {
        for(int i = points.size() - 1 ; i > 0; --i)
        {
            points.get(i).setX(points.get(i - 1).getX());
            points.get(i).setY(points.get(i - 1).getY());
        }
        switch (direction)
        {
            case Up:
                points.get(0).setY(points.get(0).getY() - 1);
                break;
            case Down:
                points.get(0).setY(points.get(0).getY() + 1);
                break;
            case Left:
                points.get(0).setX(points.get(0).getX() - 1);
                break;
            case Right:
                points.get(0).setX(points.get(0).getX() + 1);
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (this.direction == Direction.Left && direction == Direction.Right)
            return;
        if (this.direction == Direction.Right && direction == Direction.Left)
            return;
        if (this.direction == Direction.Up && direction == Direction.Down)
            return;
        if (this.direction == Direction.Down && direction == Direction.Up)
            return;
        this.direction = direction;
    }
}
