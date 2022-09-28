package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

public class Setting extends Activity {

    TextView password;
    SwitchButton settingSwitch;
    Button settingClose;

    public static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        Log.i("Setting", "onCreate()");

        ctx = this;

        password = (TextView) findViewById(R.id.password);

        settingClose = (Button) findViewById(R.id.settingClose);
        settingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingSwitch = (SwitchButton) findViewById(R.id.settingSwitch);
        settingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    password.setText("비밀번호 잠금 활성화");
                } else {
                    password.setText("비밀번호 잠금 비활성화");
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    protected void onStart() {
        super.onStart();
        Log.i("Setting", "onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.i("Setting", "onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i("Setting", "onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i("Setting", "onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Setting", "onDestroy()");
    }

}