package io.fourth_finger.sound_sculptor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import kotlin.properties.Delegates

class FunctionView : View {

    var isAmplitude by Delegates.notNull<Boolean>()
    var column by Delegates.notNull<Int>()

    constructor(context: Context) : super(context) {
        throw NotImplementedError()
    }

    private lateinit var lineColor: Paint
    private lateinit var borderColor: Paint
    private lateinit var textColor: Paint

    constructor(context: Context, isAmplitude: Boolean, column: Int) : super(context) {
        this.isAmplitude = isAmplitude
        this.column = column

        this.lineColor = Paint(Paint.ANTI_ALIAS_FLAG)
        this.borderColor = Paint(Paint.ANTI_ALIAS_FLAG)
        this.textColor = Paint(Paint.ANTI_ALIAS_FLAG)

        setOnClickListener(FunctionViewOnClickListener())
        setOnLongClickListener(OnLongClickListenerViewFunction())

        isConstant = false
        setBackgroundColor(Color.WHITE)
        lineColor.strokeWidth = 8f
        lineColor.color = resources.getColor(
            R.color.purple_700,
            context.theme
        )
        borderColor.strokeWidth = borderStrokeWidth.toFloat()
        borderColor.color = Color.BLACK
        textColor.textSize = 64f
        textColor.color = Color.BLACK
        selected = false
    }

    private var borderStrokeWidth = 4

    private var height = 0
    private var width = 0
    private var isConstant = false

    var isAddNew = false
        private set

    private var selected = false

    // The points to draw lines between
    private var data: FloatArray? = null
    var function: String? = null
    var start = -1.0
    var end = -1.0
    var length = -1.0
    var cycles = -1.0
    var min = -1.0
    var max = -1.0


    fun getFunction(): String? {
        return function
    }

    fun setFunction(function: String?) {
        require(
            !(!function.contentEquals(resources.getString(R.string.Constant))
                    && !function.contentEquals(resources.getString(R.string.Exponential))
                    && !function.contentEquals(resources.getString(R.string.Linear))
                    && !function.contentEquals(resources.getString(R.string.Logarithm))
                    && !function.contentEquals(resources.getString(R.string.Nth_Root))
                    && !function.contentEquals(resources.getString(R.string.Power))
                    && !function.contentEquals(resources.getString(R.string.Quadratic))
                    && function.contentEquals(resources.getString(R.string.Sine)))
        ) { "function passed to setFunction() is not a function from R.string" }
        this.function = function
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val xtoYRatio = xToYRatio
        width = Math.round(h.toDouble() * xtoYRatio).toInt()
        height = h
        if (width > w) {
            width = w
            height = Math.round(w.toDouble() / xtoYRatio).toInt()
            if (height > h) {
                height = h
            }
        }
        if (isConstant) {
            width = w
            height = h
        }
        invalidate()
    }

    /**
     * Gets the XtoYRatio of the data to keep drawing to scale.
     */
    val xToYRatio: Double
        get() {
            var time = 1.0 / 4.0
            if (isAmp) {
                if (data != null || dataS != null) time =
                    NativeMethods.getAmpTime(col, NativeMethods.getSampleRate())
            } else {
                if (data != null || dataS != null) time =
                    NativeMethods.getFreqTime(col, NativeMethods.getSampleRate())
            }
            return time * 4.0
        }

    override fun isSelected(): Boolean {
        return selected
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var w: Int
        val h: Int
        var xtoYRatio = xToYRatio
        if (isAddNew) {
            xtoYRatio = 1.0
        }
        w = Math.round(MeasureSpec.getSize(heightMeasureSpec) * xtoYRatio).toInt()
        if (w > MeasureSpec.getSize(widthMeasureSpec)) {
            w = resolveSizeAndState(
                w, widthMeasureSpec, measuredState
            ) or MEASURED_SIZE_MASK
        }
        if (w > Math.round(MeasureSpec.getSize(heightMeasureSpec) * xtoYRatio).toInt()) {
            w = Math.round(MeasureSpec.getSize(heightMeasureSpec) * xtoYRatio).toInt()
        }
        h = MeasureSpec.getSize(heightMeasureSpec)
        if (w % 2 == 1) {
            w--
        }
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        height = getHeight()
        width = getWidth()
        if (!isAddNew) {
            drawBetweenPoints(canvas)
            drawYValues(canvas)
        } else {
            drawPlus(canvas)
        }
        drawBorder(canvas)
    }

    private fun drawBetweenPoints(canvas: Canvas) {
        val xScale: Double
        xScale = if (NativeMethods.audioDataIsFloat()) {
            width.toDouble() / (data!!.size.toDouble() - 1)
        } else {
            width.toDouble() / (dataS!!.size.toDouble() - 1)
        }
        var minY: Double
        var maxY: Double
        if (isAmp) {
            minY = NativeMethods.getMinAmp() - NativeMethods.getMinAmp() / 4.0
            maxY = NativeMethods.getMaxAmp() + NativeMethods.getMinAmp() / 4.0
            (minY -= NativeMethods.getMaxAmp() - NativeMethods.getMinAmp()) / 4.0
            (minY += NativeMethods.getMaxAmp() - NativeMethods.getMinAmp()) / 4.0
        } else {
            minY = NativeMethods.getMinFreq() - NativeMethods.getMinFreq() / 4.0
            maxY = NativeMethods.getMaxFreq() + NativeMethods.getMinFreq() / 4.0
            (minY -= NativeMethods.getMaxFreq() - NativeMethods.getMinFreq()) / 4.0
            (minY += NativeMethods.getMaxFreq() - NativeMethods.getMinFreq()) / 4.0
        }
        if (minY == maxY) {
            minY += 1.0
            maxY += 1.0
        }
        val yScale = height.toDouble() / (maxY - minY)
        // Draw between the points
        for (i in 1 until data!!.size) {
            canvas.drawLine(
                ((i.toDouble() - 1) * xScale).toFloat(),
                (height - (data!![i - 1].toDouble() - minY) * yScale).toFloat(),
                (i.toDouble() * xScale).toFloat(),
                (height - (data!![i].toDouble() - minY) * yScale).toFloat(),
                lineColor
            )
        }
    }

    private fun drawPlus(canvas: Canvas) {
        canvas.drawRect(
            Math.round(3.5 * width / 8.0).toInt().toFloat(),
            Math.round(2.0 * width / 8.0).toInt().toFloat(),
            Math.round(4.5 * width / 8.0).toInt().toFloat(),
            Math.round(6.0 * width / 8.0).toInt().toFloat(),
            lineColor
        )
        canvas.drawRect(
            Math.round(2.0 * height / 8.0).toInt().toFloat(),
            Math.round(3.5 * height / 8.0).toInt().toFloat(),
            Math.round(6.0 * width / 8.0).toInt().toFloat(),
            Math.round(4.5 * width / 8.0).toInt().toFloat(),
            lineColor
        )
    }

    private fun drawYValues(canvas: Canvas) {
        // Draw the y values for the end points
        val startText: String
        val endText: String
        if (NativeMethods.audioDataIsFloat()) {
            if (isAmp) {
                startText = String.format("(%.2f)", data!![0] * 100.0)
                endText = String.format("(%.2f)", data!![data!!.size - 1] * 100.0)
            } else {
                startText = String.format("(%.2f)", data!![0])
                endText = String.format("(%.2f)", data!![data!!.size - 1])
            }
        } else {
            if (isAmp) {
                startText = String.format("(%.2f)", dataS!![0] * 100.0)
                endText = String.format("(%.2f)", dataS!![dataS!!.size - 1] * 100.0)
            } else {
                startText = String.format("(%.2f)", dataS!![0])
                endText = String.format("(%.2f)", dataS!![dataS!!.size - 1])
            }
        }
        var textWidthStart = textColor.measureText(startText)
        var textWidthEnd = textColor.measureText(endText)
        while ((textWidthStart + textWidthEnd + borderStrokeWidth) * 2.0 > width) {
            textColor.textSize = textColor.textSize - 1
            textWidthStart = textColor.measureText(startText)
            textWidthEnd = textColor.measureText(endText)
        }
        canvas.drawText(
            startText,
            Math.round(borderStrokeWidth / 2.0).toInt().toFloat(),
            Math.round(height / 2.0).toInt().toFloat(),
            textColor
        )
        canvas.drawText(
            endText,
            Math.round(width - textWidthEnd - borderStrokeWidth / 2.0).toInt().toFloat(),
            Math.round(height / 2.0).toInt().toFloat(),
            textColor
        )
    }

    private fun drawBorder(canvas: Canvas) {
        // Draw the border
        borderColor.strokeWidth = borderStrokeWidth.toFloat()
        if (selected) {
            borderStrokeWidth *= 4
            borderColor.strokeWidth = borderStrokeWidth.toFloat()
        }
        //Right
        canvas.drawLine(width.toFloat(), 0f, width.toFloat(), height.toFloat(), borderColor)
        //Left
        canvas.drawLine(0f, height.toFloat(), 0f, 0f, borderColor)
        // Bottom
        if (isAmp) {
            borderColor.strokeWidth = (borderStrokeWidth / 2.0).toFloat()
        }
        canvas.drawLine(width.toFloat(), height.toFloat(), 0f, height.toFloat(), borderColor)
        //Top
        if (!isAmp) {
            borderColor.strokeWidth = (borderStrokeWidth / 2.0).toFloat()
        } else {
            borderColor.strokeWidth = borderStrokeWidth.toFloat()
        }
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, borderColor)
        if (selected) {
            borderStrokeWidth /= 4
            borderColor.strokeWidth = borderStrokeWidth.toFloat()
        }
    }

    fun setData(values: FloatArray?) {
        data = FloatArray(values!!.size)
        for (i in data!!.indices) {
            data!![i] = values[i]
        }
        isAddNew = false
        isConstant = false
        val params = layoutParams
        params.width = Math.round(height * xToYRatio).toInt()
        requestLayout()
        invalidate()
    }

    fun setData(values: ShortArray?) {
        dataS = ShortArray(values!!.size)
        for (i in dataS!!.indices) {
            dataS!![i] = values[i]
        }
        isAddNew = false
        isConstant = false
        val params = layoutParams
        params.width = Math.round(height * xToYRatio).toInt()
        requestLayout()
        invalidate()
    }

    fun setAsDefaultAmp(ampOn: Boolean, audioDataIsFloat: Boolean, samples: Int) {
        if (ampOn) {
            if (audioDataIsFloat) {
                data = FloatArray(samples)
                for (i in 0 until samples) {
                    data!![i] = 1f
                }
            } else {
                dataS = ShortArray(samples)
                for (i in 0 until samples) {
                    dataS!![i] = 1
                }
            }
        } else {
            if (audioDataIsFloat) {
                data = FloatArray(samples)
                for (i in 0 until samples) {
                    data!![i] = 0f
                }
            } else {
                dataS = ShortArray(samples)
                for (i in 0 until samples) {
                    dataS!![i] = 0
                }
            }
        }
        width = samples
        height = samples
        isConstant = true
        invalidate()
    }

    fun getNumSamples(isFloat: Boolean): Int {
        return if (isFloat) {
            data!!.size
        } else {
            dataS!!.size
        }
    }

    fun setAsAddNew() {
        isAddNew = true
        isConstant = false
        invalidate()
    }

    private inner class OnLongClickListenerViewFunction : OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            if (!isAddNew) {
                (context as ActivityMain).longClicked(true)
                selected = true
                (context as ActivityMain).selected(true)
                invalidate()
            }
            return true
        }
    }

    fun select(select: Boolean) {
        selected = select
        invalidate()
    }

    companion object {
        const val ACTIVITY_AMP_MAKER = 591
        const val ACTIVITY_FREQ_MAKER = 592
        const val ACTIVITY_AMP_MAKER_FILL = 598
        const val ACTIVITY_FREQ_MAKER_FILL = 599
        const val COL_NUMBER_KEY = "590"
        const val FLOAT_ARRAY_KEY = "593"
        const val SHORT_DATA = "594"
        const val COLUMN_KEY = "595"
        const val MIN_Y_KEY = "596"
        const val MAX_Y_KEY = "597"
        const val FUNCTION_KEY = "605"
        const val START_KEY = "600"
        const val END_DATA = "601"
        const val LENGTH_KEY = "602"
        const val CYCLES_DATA = "605"
        const val MIN_DATA = "603"
        const val MAX_DATA = "604"
        const val FUNCTION_View_WIDTH_KEY = "606"
    }
}