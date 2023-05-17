package io.fourth_finger.sound_sculptor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ActivityTitle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        NativeMethods.createStream()
    }

    override fun onDestroy() {
        super.onDestroy()
        NativeMethods.destroyStream()
    }

    fun onButtonTitleNewClicked(view: View?) {
        startActivity(Intent(this, ActivityMain::class.java))
    }

    fun onButtonTitleLoadClicked(view: View?) {
        startActivity(Intent(this, ActivityLoadFile::class.java))
    }

    fun onButtonTitleSettingsClicked(view: View?) {
        startActivity(Intent(this, ActivitySettings::class.java))
    }

    companion object {
        init {
            System.loadLibrary("sound_sculptor")
        }
    }
}