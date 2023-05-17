package io.fourth_finger.sound_sculptor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import io.fourth_finger.sound_sculptor.databinding.ActivityAmplitudeMakerBinding
import kotlin.properties.Delegates

class ActivityAmplitudeMaker : AppCompatActivity() {

    private lateinit var binding: ActivityAmplitudeMakerBinding

    private var column by Delegates.notNull<Int>()
    private var functionViewWidth by Delegates.notNull<Int>()
    private var function by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmplitudeMakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        column = intent.getIntExtra(FunctionView.COL_NUMBER_KEY, -1)
        functionViewWidth = intent.getIntExtra(FunctionView.FUNCTION_View_WIDTH_KEY, -1)
        function = intent.getStringExtra(FunctionView.FUNCTION_KEY)?: ""

        setUpAutoCompleteTextView()
        setUpButtonResizing()
        populateForm()
    }

    /**
     * Sets up the autocompletion for selecting a function.
     * Also changes the visibility of input fields based on the selected function.
     */
    private fun setUpAutoCompleteTextView() {
        val arrayAdapterAmplitude = ArrayAdapter.createFromResource(
            this,
            R.array.functions,
            R.layout.text_view_auto_complete_function
        )

        val autoCompleteTextViewAmplitudeFunction =
            (binding.textInputLayoutAmplitudeFunction.editText as AutoCompleteTextView)
        autoCompleteTextViewAmplitudeFunction.setAdapter(arrayAdapterAmplitude)
        autoCompleteTextViewAmplitudeFunction.onItemClickListener =
            autoCompleteTextViewAmplitudeFunctionOnClickListener

        binding.textInputLayoutAmplitudeFunction.requestFocus()
    }

    private val autoCompleteTextViewAmplitudeFunctionOnClickListener = OnItemClickListener {
            adapterView, _, i, _ ->
        if ((adapterView.getItemAtPosition(i) as String).contentEquals(
                resources.getString(R.string.Constant)
            )
        ) {
            setUpFormForConstantFunction()
        }
    }

    /**
     * Changes the visibility of text fields to match
     * the parameters needed to create a constant function.
     */
    private fun setUpFormForConstantFunction() {
        binding.textInputLayoutStartAmplitude.visibility = View.VISIBLE
        binding.textInputLayoutAmplitudeLength.visibility = View.VISIBLE

        binding.textInputLayoutEndAmplitude.visibility = View.INVISIBLE
        binding.textInputLayoutMinAmplitude.visibility = View.INVISIBLE
        binding.textInputLayoutMaxAmplitude.visibility = View.INVISIBLE
        binding.textInputLayoutAmplitudeCycles.visibility = View.INVISIBLE
    }


    /**
     * Resizes the buttons to the height of the text fields before the view tree is drawn.
     */
    private fun setUpButtonResizing() {
        val viewTreeObserver = binding.constraintLayoutAmplitude.viewTreeObserver
        viewTreeObserver.addOnPreDrawListener {

            val createButtonLayoutParams = binding.buttonCreateAmplitude.layoutParams
            createButtonLayoutParams.height = binding.textInputLayoutStartAmplitude.height
            binding.buttonCreateAmplitude.requestLayout()

            val cancelButtonLayoutParams = binding.buttonCancelAmplitude.layoutParams
            cancelButtonLayoutParams.height = binding.textInputLayoutStartAmplitude.height
            binding.buttonCancelAmplitude.requestLayout()
            true
        }
    }

    private fun populateForm() {
        if (function.isNotEmpty()) {
            binding.autoCompleteTextViewAmplitudeFunction.setText(function)
            binding.autoCompleteTextViewAmplitudeFunction.performCompletion()
            if (function.contentEquals(resources.getString(R.string.Constant))) {
                setUpFormForConstantFunction()
            } else if (function.contentEquals(resources.getString(R.string.Exponential))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Linear))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Logarithm))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Nth_Root))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Power))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Quadratic))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Sine))) {
                // TODO
            }
        }
        val start = intent.getDoubleExtra(FunctionView.START_KEY, -1.0)
        val end = intent.getDoubleExtra(FunctionView.END_KEY, -1.0)
        val length = intent.getDoubleExtra(FunctionView.LENGTH_KEY, -1.0)
        val cycles = intent.getDoubleExtra(FunctionView.CYCLES_Key, -1.0)
        val min = intent.getDoubleExtra(FunctionView.MIN_DATA, -1.0)
        val max = intent.getDoubleExtra(FunctionView.MAX_DATA, -1.0)

        if (start != -1.0) {
            binding.textInputEditStartAmplitude.setText((start * 100).toString())
        }
        if (end != -1.0) {
            binding.textInputEditEndAmplitude.setText(end.toString())
        }
        if (length != -1.0) {
            binding.editTextAmplitudeLength.setText(length.toString())
        }
        if (cycles != -1.0) {
            binding.editTextAmplitudeCycles.setText(cycles.toString())
        }
        if (min != -1.0) {
            binding.editTextMinAmplitude.setText(min.toString())
        }
        if (max != -1.0) {
            binding.editTextMaxAmplitude.setText(max.toString())
        }
    }

    fun onButtonAmpMakerCreate(view: View?) {
        function = binding.autoCompleteTextViewAmplitudeFunction.text.toString()
        if (function.isEmpty()) {
            binding.textInputLayoutAmplitudeFunction.error = "Function needed"
        } else {
            var start = -1.0
            var end = -1.0
            var length = -1.0
            var cycles = -1.0
            var min = -1.0
            var max = -1.0

            val startAmpString = binding.textInputEditStartAmplitude.text.toString()
            if (startAmpString.isNotEmpty()) {
                start = startAmpString.toDouble()
            }
            val endAmpString = binding.textInputEditEndAmplitude.text.toString()
            if (endAmpString.isNotEmpty()) {
                end = endAmpString.toDouble()
            }
            val ampLengthString = binding.editTextAmplitudeLength.text.toString()
            if (ampLengthString.isNotEmpty()) {
                length = ampLengthString.toDouble()
            }
            val ampCyclesString = binding.editTextAmplitudeCycles.text.toString()
            if (ampCyclesString.isNotEmpty()) {
                cycles = ampCyclesString.toDouble()
            }
            val minAmpString = binding.editTextMinAmplitude.text.toString()
            if (minAmpString.isNotEmpty()) {
                min = minAmpString.toDouble()
            }
            val maxAmpString = binding.editTextMaxAmplitude.text.toString()
            if (maxAmpString.isNotEmpty()) {
                max = maxAmpString.toDouble()
            }

            var data: FloatArray? = null
            var dataIsBad = false
            if (function.contentEquals(resources.getString(R.string.Constant))) {
                if (start == -1.0) {
                    dataIsBad = true
                    binding.textInputEditStartAmplitude.error = "Positive amplitude needed"
                } else if (start > 100) {
                    dataIsBad = true
                    binding.textInputLayoutStartAmplitude.error = "Amplitude must be 100 or below"
                } else if (start < 0) {
                    binding.textInputLayoutStartAmplitude.error = "Amplitude must be 0 or above"
                } else {
                    start /= 100.0
                }
                if (length <= 0) {
                    dataIsBad = true
                    binding.editTextAmplitudeLength.error = "Positive length needed"
                }
                if (!dataIsBad) {
                    data = NativeMethods.generateConstant(start, length, 0, column, functionViewWidth)
                }
            } else if (function.contentEquals(resources.getString(R.string.Exponential))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Linear))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Logarithm))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Nth_Root))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Power))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Quadratic))) {
                // TODO
            } else if (function.contentEquals(resources.getString(R.string.Sine))) {
                // TODO
            }
            if(!dataIsBad) {
                val intent = Intent()
                intent.putExtra(FunctionView.FLOAT_ARRAY_KEY, data)
                intent.putExtra(FunctionView.MIN_Y_KEY, min)
                intent.putExtra(FunctionView.MAX_Y_KEY, max)
                intent.putExtra(FunctionView.COLUMN_KEY, column)
                intent.putExtra(FunctionView.FUNCTION_KEY, function)
                intent.putExtra(FunctionView.START_KEY, start)
                intent.putExtra(FunctionView.END_KEY, end)
                intent.putExtra(FunctionView.LENGTH_KEY, length)
                intent.putExtra(FunctionView.CYCLES_Key, cycles)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    fun onButtonAmpMakerCancel(view: View?) {
        finish()
    }
}