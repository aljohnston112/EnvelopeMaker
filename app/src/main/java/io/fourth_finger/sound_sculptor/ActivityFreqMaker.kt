package io.fourth_finger.sound_sculptor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Objects

class ActivityFreqMaker : AppCompatActivity() {
    private var col = 0
    var constraintLayoutFreq: ConstraintLayout? = null
    var textInputLayoutFreqFunction: TextInputLayout? = null
    var textInputLayoutStartFreq: TextInputLayout? = null
    var textInputLayoutEndFreq: TextInputLayout? = null
    var textInputLayoutFreqLength: TextInputLayout? = null
    var textInputLayoutMinFreq: TextInputLayout? = null
    var textInputLayoutMaxFreq: TextInputLayout? = null
    var textInputLayoutFreqCycles: TextInputLayout? = null
    var autoCompleteTextViewFreqFunction: AutoCompleteTextView? = null
    var textInputEditStartFreq: TextInputEditText? = null
    var textInputEditEndFreq: TextInputEditText? = null
    var textInputEditFreqLength: TextInputEditText? = null
    var textInputEditMinFreq: TextInputEditText? = null
    var textInputEditMaxFreq: TextInputEditText? = null
    var textInputEditFreqCycles: TextInputEditText? = null
    var buttonCreate: Button? = null
    var buttonCancel: Button? = null
    var function: String? = null
    var start = -1.0
    var end = -1.0
    var length = -1.0
    var cycles = -1.0
    var min = -1.0
    var max = -1.0
    var width = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freq_maker)
        findViews()
        setUpFunctionAutoComplete()
        resizeButtons()
        textInputLayoutStartFreq!!.visibility = View.INVISIBLE
        textInputLayoutEndFreq!!.visibility = View.INVISIBLE
        textInputLayoutFreqLength!!.visibility = View.INVISIBLE
        textInputLayoutMinFreq!!.visibility = View.INVISIBLE
        textInputLayoutMaxFreq!!.visibility = View.INVISIBLE
        textInputLayoutFreqCycles!!.visibility = View.INVISIBLE
        populate()
        col = intent.getIntExtra(FunctionView.Companion.COL_NUMBER_KEY, -1)
        width = intent.getIntExtra(FunctionView.Companion.FUNCTION_View_WIDTH_KEY, -1)
    }

    private fun populate() {
        function = intent.getStringExtra(FunctionView.Companion.FUNCTION_KEY)
        if (function != null) {
            autoCompleteTextViewFreqFunction!!.setText(function)
            autoCompleteTextViewFreqFunction!!.performCompletion()
            if (function.contentEquals(resources.getString(R.string.Constant))) {
                setUpConstant()
            } else if (function.contentEquals(resources.getString(R.string.Exponential))) {
            } else if (function.contentEquals(
                    resources.getString(R.string.Linear)
                )
            ) {
            } else if (function.contentEquals(
                    resources.getString(R.string.Logarithm)
                )
            ) {
            } else if (function.contentEquals(
                    resources.getString(R.string.Nth_Root)
                )
            ) {
            } else if (function.contentEquals(
                    resources.getString(R.string.Power)
                )
            ) {
            } else if (function.contentEquals(
                    resources.getString(R.string.Quadratic)
                )
            ) {
            } else if (function.contentEquals(
                    resources.getString(R.string.Sine)
                )
            ) {
            }
        }
        start = intent.getDoubleExtra(FunctionView.Companion.START_KEY, -1.0)
        if (start != -1.0) {
            textInputEditStartFreq!!.setText(start.toString())
        }
        end = intent.getDoubleExtra(FunctionView.Companion.END_DATA, -1.0)
        if (end != -1.0) {
            textInputEditEndFreq!!.setText(end.toString())
        }
        length = intent.getDoubleExtra(FunctionView.Companion.LENGTH_KEY, -1.0)
        if (length != -1.0) {
            textInputEditFreqLength!!.setText(length.toString())
        }
        cycles = intent.getDoubleExtra(FunctionView.Companion.CYCLES_DATA, -1.0)
        if (cycles != -1.0) {
            textInputEditFreqCycles!!.setText(cycles.toString())
        }
        min = intent.getDoubleExtra(FunctionView.Companion.MIN_DATA, -1.0)
        if (min != -1.0) {
            textInputEditMinFreq!!.setText(min.toString())
        }
        max = intent.getDoubleExtra(FunctionView.Companion.MAX_DATA, -1.0)
        if (max != -1.0) {
            textInputEditMaxFreq!!.setText(max.toString())
        }
    }

    private fun findViews() {
        constraintLayoutFreq = findViewById(R.id.constraint_layout_freq)
        textInputLayoutFreqFunction = findViewById(R.id.text_input_layout_freq_function)
        textInputLayoutStartFreq = findViewById(R.id.text_input_layout_start_freq)
        textInputLayoutEndFreq = findViewById(R.id.text_input_layout_end_freq)
        textInputLayoutFreqLength = findViewById(R.id.text_input_layout_freq_length)
        textInputLayoutMinFreq = findViewById(R.id.text_input_layout_min_freq)
        textInputLayoutMaxFreq = findViewById(R.id.text_input_layout_max_freq)
        textInputLayoutFreqCycles = findViewById(R.id.text_input_layout_freq_cycles)
        autoCompleteTextViewFreqFunction = findViewById(R.id.auto_complete_text_view_freq_function)
        textInputEditStartFreq = findViewById(R.id.text_input_edit_start_freq)
        textInputEditEndFreq = findViewById(R.id.edit_text_end_freq)
        textInputEditFreqLength = findViewById(R.id.edit_text_freq_length)
        textInputEditMinFreq = findViewById(R.id.edit_text_min_freq)
        textInputEditMaxFreq = findViewById(R.id.edit_text_max_freq)
        textInputEditFreqCycles = findViewById(R.id.edit_text_freq_cycles)
        buttonCreate = findViewById(R.id.button_create_freq)
        buttonCancel = findViewById(R.id.button_cancel_freq)
    }

    private fun resizeButtons() {
        val obs = constraintLayoutFreq!!.viewTreeObserver
        obs.addOnPreDrawListener {
            val params = buttonCreate!!.layoutParams
            params.height = textInputLayoutStartFreq!!.height
            buttonCreate!!.requestLayout()
            val params2 = buttonCancel!!.layoutParams
            params2.height = textInputLayoutStartFreq!!.height
            buttonCancel!!.requestLayout()
            true
        }
    }

    private fun setUpFunctionAutoComplete() {
        val arrayAdapterFreq = ArrayAdapter.createFromResource(
            this,
            R.array.functions, R.layout.text_view_auto_complete_function
        )
        val autoCompleteTextViewFreqFunction =
            (textInputLayoutFreqFunction!!.editText as AutoCompleteTextView?)!!
        autoCompleteTextViewFreqFunction.setAdapter(arrayAdapterFreq)
        autoCompleteTextViewFreqFunction.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                if (arrayAdapterFreq.getItem(i).toString()
                        .contentEquals(resources.getString(R.string.Constant))
                ) {
                    setUpConstant()
                }
            }
        textInputLayoutFreqFunction!!.requestFocus()
    }

    private fun setUpConstant() {
        textInputLayoutStartFreq!!.visibility = View.VISIBLE
        textInputLayoutEndFreq!!.visibility = View.INVISIBLE
        textInputLayoutFreqLength!!.visibility = View.VISIBLE
        textInputLayoutMinFreq!!.visibility = View.INVISIBLE
        textInputLayoutMaxFreq!!.visibility = View.INVISIBLE
        textInputLayoutFreqCycles!!.visibility = View.INVISIBLE
    }

    fun onButtonFreqMakerCreate(view: View?) {
        function = autoCompleteTextViewFreqFunction!!.text.toString()
        val startFreqString = Objects.requireNonNull(
            textInputEditStartFreq!!.text
        ).toString()
        if (!startFreqString.isEmpty()) {
            start = startFreqString.toDouble()
        }
        val endFreqString = Objects.requireNonNull(
            textInputEditEndFreq!!.text
        ).toString()
        if (!endFreqString.isEmpty()) {
            end = endFreqString.toDouble()
        }
        val freqLengthString = Objects.requireNonNull(
            textInputEditFreqLength!!.text
        ).toString()
        if (!freqLengthString.isEmpty()) {
            length = freqLengthString.toDouble()
        }
        val freqCyclesString = Objects.requireNonNull(
            textInputEditFreqCycles!!.text
        ).toString()
        if (!freqCyclesString.isEmpty()) {
            cycles = freqCyclesString.toDouble()
        }
        val minFreqString = Objects.requireNonNull(
            textInputEditMinFreq!!.text
        ).toString()
        if (!minFreqString.isEmpty()) {
            min = minFreqString.toDouble()
        }
        val maxFreqString = Objects.requireNonNull(
            textInputEditMaxFreq!!.text
        ).toString()
        if (!maxFreqString.isEmpty()) {
            max = maxFreqString.toDouble()
        }
        var mustReturn = false
        var data: FloatArray? = null
        if (function.contentEquals(resources.getString(R.string.Constant))) {
            if (start == -1.0) {
                mustReturn = true
                textInputLayoutStartFreq!!.error = "Frequency needed"
            }
            if (length <= 0) {
                mustReturn = true
                textInputLayoutStartFreq!!.error = "Positive length needed"
            }
            if (mustReturn) {
                return
            }
            data = NativeMethods.loadConstant(start, length, 1, col, width)
        } else if (function.contentEquals(resources.getString(R.string.Exponential))) {
        } else if (function.contentEquals(
                resources.getString(R.string.Linear)
            )
        ) {
        } else if (function.contentEquals(resources.getString(R.string.Logarithm))) {
        } else if (function.contentEquals(
                resources.getString(R.string.Nth_Root)
            )
        ) {
        } else if (function.contentEquals(resources.getString(R.string.Power))) {
        } else if (function.contentEquals(
                resources.getString(R.string.Quadratic)
            )
        ) {
        } else if (function.contentEquals(
                resources.getString(R.string.Sine)
            )
        ) {
        }
        val minY = NativeMethods.getMinFreq()
        val maxY = NativeMethods.getMaxFreq()
        val intent = Intent()
        intent.putExtra(FunctionView.Companion.FLOAT_ARRAY_KEY, data)
        intent.putExtra(FunctionView.Companion.MIN_Y_KEY, minY)
        intent.putExtra(FunctionView.Companion.MAX_Y_KEY, maxY)
        intent.putExtra(FunctionView.Companion.COLUMN_KEY, col)
        intent.putExtra(FunctionView.Companion.FUNCTION_KEY, function)
        intent.putExtra(FunctionView.Companion.START_KEY, start)
        intent.putExtra(FunctionView.Companion.LENGTH_KEY, length)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onButtonFreqMakerCancel(view: View?) {
        finish()
    }
}