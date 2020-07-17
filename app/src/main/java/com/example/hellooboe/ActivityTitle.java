package com.example.hellooboe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTitle extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        NativeMethods.createStream();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NativeMethods.destroyStream();
    }

    public void onButtonTitleNewClicked(View view) {
        startActivity(new Intent(this, ActivityMain.class));
    }

    public void onButtonTitleLoadClicked(View view) {
        startActivity(new Intent(this, ActivityLoadFile.class));
    }

    public void onButtonTitleSettingsClicked(View view) {
        startActivity(new Intent(this, ActivitySettings.class));
    }

}
