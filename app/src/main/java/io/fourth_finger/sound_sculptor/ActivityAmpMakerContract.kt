package io.fourth_finger.sound_sculptor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ActivityAmpMakerContract: ActivityResultContract<FunctionView, EnvelopeData?>() {

    override fun createIntent(context: Context, input: FunctionView): Intent {
        val intent = Intent(
            context,
            ActivityAmplitudeMaker::class.java
        )
        intent.putExtra(
            FunctionView.COL_NUMBER_KEY,
            input.column
        )
        intent.putExtra(FunctionView.FUNCTION_View_WIDTH_KEY, input.width)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return if (resultCode == Activity.RESULT_OK) {
            val data = intent?.getFloatArrayExtra(FunctionView.FLOAT_ARRAY_KEY)
            
            EnvelopeData(

            )
            intent?.getStringExtra(
                FunctionView.FUNCTION_KEY
            )
        } else {
            null
        }
    }

}