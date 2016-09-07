package com.meisler.snake;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    private Bitmap getBitmap()
    {
        File imagePath = new File(this.getFilesDir(), "lastGame.png");
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        return BitmapFactory.decodeFile(imagePath.getPath(),opt);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (findViewById(R.id.startButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
        (findViewById(R.id.settingsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((TextView)findViewById(R.id.bestTV)).setText("Best score : " + getSharedPreferences("data",MODE_PRIVATE).getInt("bestScore",0));
        Bitmap b = getBitmap();
        if(b != null)
        {
            (findViewById(R.id.mainFL)).setBackground(new BitmapDrawable(getResources(),b));
            (findViewById(R.id.mainFL)).getBackground().setAlpha(51);
        }
    }

}
