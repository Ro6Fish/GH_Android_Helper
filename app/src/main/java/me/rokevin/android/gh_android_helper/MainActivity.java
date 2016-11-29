package me.rokevin.android.gh_android_helper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.rokevin.android.lib.helper.util.RLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RLog.e("mainactivity", "啦啦啦啦啦啦!!!");
    }
}