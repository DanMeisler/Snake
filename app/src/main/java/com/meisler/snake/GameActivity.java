package com.meisler.snake;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meisler.snake.objects.Direction;
import com.meisler.snake.objects.Food;
import com.meisler.snake.objects.Point;
import com.meisler.snake.objects.Snake;
import com.meisler.snake.others.CircleView;
import com.meisler.snake.others.CommonDivisors;
import com.meisler.snake.others.OnSwipeTouchListener;
import com.meisler.snake.others.Resolution;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {
    Timer t;
    int score;
    Snake snake;
    Food food;
    RelativeLayout rl;
    boolean isScreenUpdated;
    int pointSize;

    private int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }

    private Resolution getResolution()
    {
        if((getResources().getDisplayMetrics().widthPixels == 1080) && (getResources().getDisplayMetrics().heightPixels == 1920))
            return Resolution.Fhd;
        else if((getResources().getDisplayMetrics().widthPixels == 1440 ) && (getResources().getDisplayMetrics().heightPixels == 2560))
            return Resolution.Qhd;
        else if ((getResources().getDisplayMetrics().widthPixels == 720 ) && (getResources().getDisplayMetrics().heightPixels == 1280))
            return Resolution.Hd;
        else
            return Resolution.None;
    }

    private void vibrate(int duration)
    {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(duration);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        switch (getResolution())
        {
            case Hd:
                pointSize = CommonDivisors.hd[getSharedPreferences("hd",MODE_PRIVATE).getInt("sizeIndex",8)];
                break;
            case Fhd:
                pointSize = CommonDivisors.fhd[getSharedPreferences("fhd",MODE_PRIVATE).getInt("sizeIndex",14)];
                break;
            case Qhd:
                pointSize = CommonDivisors.qhd[getSharedPreferences("qhd",MODE_PRIVATE).getInt("sizeIndex",10)];
                break;
            case None:
                pointSize = gcd(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels) / 2;
                break;
        }
        Point.setRadius(pointSize / 2);
        Point.setMaxX(getResources().getDisplayMetrics().widthPixels / pointSize);
        Point.setMaxY(getResources().getDisplayMetrics().heightPixels / pointSize);
        rl = (RelativeLayout) findViewById(R.id.snakeRL);
        score = 0;
        ((TextView)findViewById(R.id.scoreTV)).setText(String.valueOf(score));
        snake = new Snake();
        food = new Food(snake.points);
        switch (getSharedPreferences("data",MODE_PRIVATE).getInt("gesture",0))
        {
            case 0:
                rl.setOnTouchListener(new OnSwipeTouchListener(){
                    @Override
                    public void onLeftToRightSwipe() {
                        if(!isScreenUpdated)
                            return;
                        snake.setDirection(Direction.Right);
                        isScreenUpdated = false;
                    }

                    @Override
                    public void onRightToLeftSwipe() {
                        if(!isScreenUpdated)
                            return;
                        snake.setDirection(Direction.Left);
                        isScreenUpdated = false;
                    }

                    @Override
                    public void onTopToBottomSwipe() {
                        if(!isScreenUpdated)
                            return;
                        snake.setDirection(Direction.Down);
                        isScreenUpdated = false;
                    }

                    @Override
                    public void onBottomToTopSwipe() {
                        if(!isScreenUpdated)
                            return;
                        snake.setDirection(Direction.Up);
                        isScreenUpdated = false;
                    }
                });
                break;
            case 1:
                rl.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent ev) {
                        if(!isScreenUpdated)
                            return true;
                        float deltaWidth = Math.abs(ev.getX() - snake.points.get(0).getRealX());
                        float deltaHight = Math.abs(ev.getY() - snake.points.get(0).getRealY());
                        if(deltaWidth > deltaHight)
                        {
                            if(ev.getX() > snake.points.get(0).getRealX())
                                snake.setDirection(Direction.Right);
                            else
                                snake.setDirection(Direction.Left);
                        }
                        else
                        {
                            if(ev.getY() > snake.points.get(0).getRealY())
                                snake.setDirection(Direction.Down);
                            else
                                snake.setDirection(Direction.Up);
                        }
                        isScreenUpdated = false;
                        return true;
                    }
                });
                break;

        }


        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                snake.move();
                if ((snake.points.get(0).getY() == food.point.getY()) && (snake.points.get(0).getX() == food.point.getX()))
                {
                    if (getSharedPreferences("data",MODE_PRIVATE).getBoolean("vibration",true))
                        vibrate(100);
                    score++;
                    if (score > getSharedPreferences("data",MODE_PRIVATE).getInt("bestScore",0))
                    {
                        getSharedPreferences("data",MODE_PRIVATE).edit().putInt("bestScore",score).commit();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.scoreTV)).setText(String.valueOf(score));
                        }
                    });
                    food = new Food(snake.points);
                    snake.eat();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        rl.removeAllViews();
                        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(pointSize,pointSize);
                        lp1.topMargin = food.point.getRealY() - Point.getRadius();
                        lp1.leftMargin = food.point.getRealX() - Point.getRadius();
                        rl.addView(new CircleView(getApplicationContext(),null, ContextCompat.getColor(GameActivity.this, R.color.colorFood)),lp1);
                        for (Point p:snake.points) {
                            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(pointSize,pointSize);
                            lp2.topMargin = p.getRealY() - Point.getRadius();
                            lp2.leftMargin = p.getRealX() - Point.getRadius();
                            rl.addView(new CircleView(getApplicationContext(),null,ContextCompat.getColor(GameActivity.this, R.color.colorSnake)),lp2);
                        }
                        isScreenUpdated = true;
                    }
                });
                for ( int i = 1; i < snake.points.size() ; ++ i )
                {
                    if((snake.points.get(0).getX() == snake.points.get(i).getX()) && (snake.points.get(0).getY() == snake.points.get(i).getY()))
                    {
                        if (getSharedPreferences("data",MODE_PRIVATE).getBoolean("vibration",true))
                            vibrate(400);
                        finish();
                    }
                }
            }
        },200,400 - getSharedPreferences("data",MODE_PRIVATE).getInt("speed",50)*4);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
    }
}
