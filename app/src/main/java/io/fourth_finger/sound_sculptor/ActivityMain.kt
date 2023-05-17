package io.fourth_finger.sound_sculptor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActivityMain : AppCompatActivity() {
    private var isLongClicked = false

    private lateinit var linearLayoutAmplitudeRow: LinearLayout
    private lateinit var linearLayoutFrequencyRow: LinearLayout

    private var functionAmplitudeViews = mutableListOf<FunctionView>()
    private var functionFrequencyViews = mutableListOf<FunctionView>()

    private var selected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        NativeMethods.createStream()
        init()
        initFAB()
    }

    private fun init() {
        isLongClicked = false
        linearLayoutAmplitudeRow = findViewById(R.id.linear_layout_amp_row)
        linearLayoutFrequencyRow = findViewById(R.id.linear_layout_freq_row)

        val amplitudeFunctionView = FunctionView(
            this,
            true,
            0
        )
        amplitudeFunctionView.setAsAddNew()
        functionAmplitudeViews.add(amplitudeFunctionView)
        linearLayoutAmplitudeRow.addView(amplitudeFunctionView)

        val frequencyFunctionView = FunctionView(
            this,
            false,
            0
        )
        frequencyFunctionView.setAsAddNew()
        functionFrequencyViews.add(frequencyFunctionView)
        linearLayoutFrequencyRow.addView(frequencyFunctionView)
    }

    private inner class FunctionViewOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            if (view is FunctionView) {
                val function = view.function
                val start = view.start
                val length = view.length
                val column = view.column
                if (isLongClicked) {
                    if (view.isAmplitude) {
                        if (function == null) {
                            val contract = registerForActivityResult(ActivityAmpMakerContract()) {
                                if (column != -1) {
                                        (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                            view.setData(generateFunctionSegment(it))
                                    try {
                                        (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                            .setFunction(it)
                                    } catch (ignored: NullPointerException) {
                                    }
                                    (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                        .setStart(data.getDoubleExtra(FunctionView.START_KEY, -1.0))
                                    (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                        .setEnd(data.getDoubleExtra(FunctionView.END_DATA, -1.0))
                                    (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                        .setLength(data.getDoubleExtra(FunctionView.LENGTH_KEY, -1.0))
                                    (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                        .setCycles(data.getDoubleExtra(FunctionView.CYCLES_DATA, -1.0))
                                    (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                        .setMin(data.getDoubleExtra(FunctionView.MIN_DATA, -1.0))
                                    (linearLayoutAmplitudeRow.getChildAt(column) as FunctionView)
                                        .setMax(data.getDoubleExtra(FunctionView.MAX_DATA, -1.0))
                                    if (requestCode == FunctionView.ACTIVITY_AMP_MAKER) {
                                        val functionViewAmp = FunctionView(this, true, column + 1)
                                        functionViewAmp.setAsAddNew()
                                        linearLayoutAmplitudeRow.addView(functionViewAmp)
                                    }
                                    linearLayoutAmplitudeRow.invalidate()
                                }
                            }
                            contract.launch(view)
                        } else if (
                            function.contentEquals(resources.getString(R.string.Constant)) &&
                            start != -1.0 && length != -1.0
                        ) {
                            intent.putExtra(FunctionView.FUNCTION_KEY, function)
                            intent.putExtra(FunctionView.START_KEY, start)
                            intent.putExtra(FunctionView.LENGTH_KEY, length)
                            (context as ActivityMain).startActivityForResult(
                                intent,
                                FunctionView.ACTIVITY_AMP_MAKER_FILL
                            )
                        } else if (function.contentEquals(
                                resources.getString(R.string.Exponential)
                            )
                        ) {
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
                    } else {
                        val intent = Intent(context, ActivityFreqMaker::class.java)
                        intent.putExtra(FunctionView.COL_NUMBER_KEY, (view as FunctionView).col)
                        intent.putExtra(FunctionView.FUNCTION_View_WIDTH_KEY, width)
                        if (function == null) {
                            (context as ActivityMain).startActivityForResult(
                                intent,
                                FunctionView.ACTIVITY_FREQ_MAKER
                            )
                        } else if (function.contentEquals(resources.getString(R.string.Constant)) && start != -1.0 && length != -1.0) {
                            intent.putExtra(FunctionView.FUNCTION_KEY, view.getFunction())
                            intent.putExtra(FunctionView.START_KEY, view.start)
                            intent.putExtra(FunctionView.LENGTH_KEY, view.length)
                            (context as ActivityMain).startActivityForResult(
                                intent,
                                FunctionView.ACTIVITY_FREQ_MAKER_FILL
                            )
                        }
                    }
                } else {
                    if (!isAddNew) {
                        selected = !selected
                        if (selected) {
                            (context as ActivityMain).selected(true)
                        } else {
                            (context as ActivityMain).selected(false)
                        }
                        invalidate()
                    }
                }
            }
        }
    }

    private fun generateFunctionSegment(it: String): Any {
        when(it) {
            resources.getString(R.string.Constant) -> {
                if (start == -1.0) {
                    mustReturn = true
                    textInputLayoutStartAmp!!.error = "Positive amplitude needed"
                } else if (start > 100) {
                    mustReturn = true
                    textInputLayoutStartAmp!!.error = "Amplitude must be 100 or below"
                } else if (start < 0) {
                    textInputLayoutStartAmp!!.error = "Amplitude must be 0 or above"
                } else {
                    start /= 100.0
                }
                if (length <= 0) {
                    mustReturn = true
                    textInputLayoutAmpLength!!.error = "Positive length needed"
                }
                if (mustReturn) {
                    return
                }
                data = NativeMethods.loadConstant(start, length, 0, col, width)
            }
        }
    }


    private fun initFAB() {
            val fab = findViewById<FloatingActionButton>(R.id.fab)
            fab.setOnClickListener { view: View? ->
                NativeMethods.makeSound()
                NativeMethods.startStream()
            }
        }

        fun addView(functionView: FunctionView?, channel: Int, columnIndex: Int) {
            (linearLayoutAmplitudeRow.getChildAt(channel) as LinearLayout)
                .addView(functionView, columnIndex)
        }

        fun addChannel(channel: Int) {
            linearLayoutAmplitudeRow.addView(
                LayoutInflater.from(this).inflate(
                    R.layout.linear_layout_row,
                    linearLayoutAmplitudeRow
                ),
                channel
            )
        }

        fun removeViewFunction(channel: Int, columnIndex: Int) {
            (linearLayoutAmplitudeRow.getChildAt(channel) as LinearLayout).removeViewAt(columnIndex)
        }

        fun removeChannel(channel: Int) {
            linearLayoutAmplitudeRow.removeViewAt(channel)
        }

        fun longClicked(longClicked: Boolean) {
            if (longClicked != isLongClicked) {
                isLongClicked = longClicked
                val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
                val registrarCopy = toolbar.menu.findItem(R.id.action_copy)
                val registrarPaste = toolbar.menu.findItem(R.id.action_paste)
                val registrarDelete = toolbar.menu.findItem(R.id.action_delete)
                if (isLongClicked) {
                    registrarCopy.isVisible = true
                    registrarPaste.isVisible = true
                    registrarDelete.isVisible = true
                } else {
                    registrarCopy.isVisible = false
                    registrarPaste.isVisible = false
                    registrarDelete.isVisible = false
                }
            }
        }

        fun selected(selected: Boolean) {
            val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
            val registrarCopy = toolbar.menu.findItem(R.id.action_copy)
            val registrarPaste = toolbar.menu.findItem(R.id.action_paste)
            if (selected) {
                this.selected += 1
                if (this.selected > 1) {
                    registrarCopy!!.isVisible = false
                    registrarPaste!!.isVisible = false
                }
            } else {
                this.selected -= 1
                if (this.selected == 1) {
                    if (this.selected > 1) {
                        registrarCopy!!.isVisible = true
                        registrarPaste!!.isVisible = true
                    }
                }
            }
        }

        override fun onBackPressed() {
            var goBack = true
            if (isLongClicked) {
                for (v in functionAmplitudeViews) {
                    if (v.isSelected) {
                        goBack = false
                    }
                    v.select(false)
                }
                for (v in functionFrequencyViews) {
                    if (v.isSelected) {
                        goBack = false
                    }
                    v.select(false)
                }
                longClicked(false)
                if (goBack) {
                    super.onBackPressed()
                }
            } else {
                super.onBackPressed()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return if (item.itemId == R.id.action_save) {
                startActivity(Intent(this, ActivitySaveFile::class.java))
                true
            } else if (item.itemId == R.id.action_copy) {
                true
            } else {
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK) {
                val col = data!!.getIntExtra(FunctionView.COLUMN_KEY, -1)
                if (requestCode == FunctionView.ACTIVITY_AMP_MAKER ||
                    requestCode == FunctionView.ACTIVITY_AMP_MAKER_FILL
                ) {

                } else if (requestCode == FunctionView.ACTIVITY_FREQ_MAKER || 
                    requestCode == FunctionView.ACTIVITY_FREQ_MAKER_FILL
                ) {
                    if (col != -1) {
                        if (NativeMethods.audioDataIsFloat()) {
                            val dataF = data.getFloatArrayExtra(FunctionView.FLOAT_ARRAY_KEY)
                            (linearLayoutFrequencyRow.getChildAt(col) as FunctionView).setData(dataF)
                        } else {
                            val dataS = data.getShortArrayExtra(FunctionView.SHORT_DATA)
                            (linearLayoutFrequencyRow.getChildAt(col) as FunctionView).setData(dataS)
                        }
                        try {
                            (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                                .setFunction(data.getStringExtra(FunctionView.FUNCTION_KEY))
                        } catch (ignored: NullPointerException) {
                        }
                        (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                            .setStart(data.getDoubleExtra(FunctionView.START_KEY, -1.0))
                        (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                            .setEnd(data.getDoubleExtra(FunctionView.END_DATA, -1.0))
                        (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                            .setLength(
                                data.getDoubleExtra(
                                    FunctionView.LENGTH_KEY,
                                    -1.0
                                )
                            )
                        (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                            .setCycles(
                                data.getDoubleExtra(
                                    FunctionView.CYCLES_DATA,
                                    -1.0
                                )
                            )
                        (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                            .setMin(data.getDoubleExtra(FunctionView.MIN_DATA, -1.0))
                        (linearLayoutFrequencyRow.getChildAt(col) as FunctionView)
                            .setMax(data.getDoubleExtra(FunctionView.MAX_DATA, -1.0))
                        if (requestCode == FunctionView.ACTIVITY_FREQ_MAKER) {
                            val functionViewFreq = FunctionView(this, false, col + 1)
                            functionViewFreq.setAsAddNew()
                            linearLayoutFrequencyRow.addView(functionViewFreq)
                        }
                        linearLayoutFrequencyRow.invalidate()
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // TODO Write your code if there's no result
                }
            }
        }
    }