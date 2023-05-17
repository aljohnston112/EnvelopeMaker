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

    override fun parseResult(resultCode: Int, intent: Intent?): EnvelopeData? {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            EnvelopeData(
                envelopeArray = intent.getFloatArrayExtra(FunctionView.FLOAT_ARRAY_KEY)?: FloatArray(0),
                function = intent.getStringExtra(FunctionView.FUNCTION_KEY)?:"",
                column = intent.getIntExtra(FunctionView.COLUMN_KEY, -1),
                start = intent.getDoubleExtra(FunctionView.START_KEY, -1.0),
                end = intent.getDoubleExtra(FunctionView.END_KEY, -1.0),
                length = intent.getDoubleExtra(FunctionView.LENGTH_KEY, -1.0),
                minY = intent.getDoubleExtra(FunctionView.MIN_Y_KEY, -1.0),
                maxY = intent.getDoubleExtra(FunctionView.MAX_Y_KEY, -1.0),
                cycles = intent.getDoubleExtra(FunctionView.CYCLES_Key, -1.0)
            )
        } else {
            null
        }
    }

}