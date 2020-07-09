package com.example.hellooboe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TitleActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);
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
        if(NativeMethods.isFloat()) {
            float[] data = NativeMethods.loadDataFloat();

           // functionView.setData(NativeMethods.getMinAmp(), NativeMethods.getMaxAmp(), data);
        } else{
            short[] data = NativeMethods.loadDataShort();
        }
        // DestroyStream();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onNewButtonClicked(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void onLoadButtonClicked(View view) {
        Intent loadFileIntent = new Intent(this, LoadFileActivity.class);
        startActivity(loadFileIntent);
    }


    public void log(String message) {
        Log.i("Main", message);
    }

}
