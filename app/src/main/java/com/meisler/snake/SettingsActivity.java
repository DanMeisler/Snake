package com.meisler.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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
        switch (getSharedPreferences("data",MODE_PRIVATE).getInt("gesture",0))
        {
            case 0:
                ((RadioButton) findViewById(R.id.swipeRB)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.pointerRB)).setChecked(true);
                break;
        }
        ((SeekBar)findViewById(R.id.speedSB)).setProgress(getSharedPreferences("data",MODE_PRIVATE).getInt("speed",50));
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
                getSharedPreferences("hd",MODE_PRIVATE).edit().putInt("sizeIndex",(int)((seekBar.getProgress() / 100.0) * (CommonDivisors.hd.length - 1))).apply();
                getSharedPreferences("fhd",MODE_PRIVATE).edit().putInt("sizeIndex",(int)((seekBar.getProgress() / 100.0) * (CommonDivisors.fhd.length - 1))).apply();
                getSharedPreferences("qhd",MODE_PRIVATE).edit().putInt("sizeIndex",(int)((seekBar.getProgress() / 100.0) * (CommonDivisors.qhd.length - 1))).apply();
            }

        });

        ((SeekBar)findViewById(R.id.speedSB)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getSharedPreferences("data",MODE_PRIVATE).edit().putInt("speed",seekBar.getProgress()).commit();
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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.swipeRB:
                if (checked)
                    getSharedPreferences("data",MODE_PRIVATE).edit().putInt("gesture",0).commit();
                    break;
            case R.id.pointerRB:
                if (checked)
                    getSharedPreferences("data",MODE_PRIVATE).edit().putInt("gesture",1).commit();
                    break;
        }
    }
}
