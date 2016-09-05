package com.meisler.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.meisler.snake.others.CommonDivisors;
import com.meisler.snake.others.Resolution;

public class SettingsActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((CheckBox) findViewById(R.id.vibrationCB)).setChecked(getSharedPreferences("data",MODE_PRIVATE).getBoolean("vibration",true));
        switch (getResolution())
        {
            case Hd:
                ((SeekBar)findViewById(R.id.sizeIndexSB)).setProgress((int)((getSharedPreferences("hd",MODE_PRIVATE).getInt("sizeIndex",8) / (double)(CommonDivisors.hd.length - 1)) * 100));
                break;
            case Fhd:
                ((SeekBar)findViewById(R.id.sizeIndexSB)).setProgress((int)((getSharedPreferences("fhd",MODE_PRIVATE).getInt("sizeIndex",14) / (double)(CommonDivisors.fhd.length - 1)) * 100));
                break;
            case Qhd:
                ((SeekBar)findViewById(R.id.sizeIndexSB)).setProgress((int)((getSharedPreferences("qhd",MODE_PRIVATE).getInt("sizeIndex",10) / (double)(CommonDivisors.qhd.length - 1)) * 100));
                break;
            case None:
                (findViewById(R.id.sizeIndexSB)).setEnabled(false);
                break;
        }

        ((SeekBar)findViewById(R.id.sizeIndexSB)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getSharedPreferences("hd",MODE_PRIVATE).edit().putInt("sizeIndex",(int)((seekBar.getProgress() / 100.0) * (CommonDivisors.hd.length - 1))).commit();
                getSharedPreferences("fhd",MODE_PRIVATE).edit().putInt("sizeIndex",(int)((seekBar.getProgress() / 100.0) * (CommonDivisors.fhd.length - 1))).commit();
                getSharedPreferences("qhd",MODE_PRIVATE).edit().putInt("sizeIndex",(int)((seekBar.getProgress() / 100.0) * (CommonDivisors.qhd.length - 1))).commit();
            }

        });

        ((CheckBox) findViewById(R.id.vibrationCB)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("data",MODE_PRIVATE).edit().putBoolean("vibration",isChecked).commit();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
