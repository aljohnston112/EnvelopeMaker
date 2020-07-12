package com.example.hellooboe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTitle extends AppCompatActivity {

    static final String TITLE_TEXT_SIZE = "com.example.TITLE_TEXT_SIZE";
    static final String TITLE_BUNDLE = "com.example.TITLE_BUNDLE";

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            AudioManager myAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            String sampleRateStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            int defaultSampleRate = Integer.parseInt(sampleRateStr);
            String framesPerBurstStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
            int defaultFramesPerBurst = Integer.parseInt(framesPerBurstStr);
            native_setDefaultStreamValues(defaultSampleRate, defaultFramesPerBurst);
        }
        */
        NativeMethods.createStream();
        if (NativeMethods.isFloat()) {
            float[] data = NativeMethods.loadDataFloat();

        } else {
            short[] data = NativeMethods.loadDataShort();
        }
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

    public void log(String message) {
        Log.i("Main", message);
    }

}
