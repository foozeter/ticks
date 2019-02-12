package com.foureyedstraighthair.ticks


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CreateTimerFragment : Fragment() {

    private lateinit var hoursLabel: TextView
    private lateinit var minutesLabel: TextView
    private lateinit var secondsLabel: TextView

    private val defaultInputs = listOf(0, 0, 0, 0, 0, 0) // hh, mm, ss
    private val inputs = defaultInputs.toMutableList()
    private var inputsCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_timer, container, false)
        val timeInput = view.findViewById<View>(R.id.time_input)
        val numPad = view.findViewById<View>(R.id.num_pad)

        hoursLabel = timeInput.findViewById(R.id.hours)
        minutesLabel = timeInput.findViewById(R.id.minutes)
        secondsLabel = timeInput.findViewById(R.id.seconds)
        invalidateLabels()

        numPad.findViewById<View>(R.id.num_button_0).setOnClickListener { onNumberInputted(0) }
        numPad.findViewById<View>(R.id.num_button_1).setOnClickListener { onNumberInputted(1) }
        numPad.findViewById<View>(R.id.num_button_2).setOnClickListener { onNumberInputted(2) }
        numPad.findViewById<View>(R.id.num_button_3).setOnClickListener { onNumberInputted(3) }
        numPad.findViewById<View>(R.id.num_button_4).setOnClickListener { onNumberInputted(4) }
        numPad.findViewById<View>(R.id.num_button_5).setOnClickListener { onNumberInputted(5) }
        numPad.findViewById<View>(R.id.num_button_6).setOnClickListener { onNumberInputted(6) }
        numPad.findViewById<View>(R.id.num_button_7).setOnClickListener { onNumberInputted(7) }
        numPad.findViewById<View>(R.id.num_button_8).setOnClickListener { onNumberInputted(8) }
        numPad.findViewById<View>(R.id.num_button_9).setOnClickListener { onNumberInputted(9) }

        numPad.findViewById<View>(R.id.backspace).apply {
            setOnClickListener { onBackspace() }
            setOnLongClickListener { onBackspaceLong(); true }
        }

//        numPad.findViewById<View>(R.id.button_ok).setOnClickListener {
//            Log.d("mylog", "check")
//        }

        return view
    }

    private fun onNumberInputted(num: Int) {
        if ((inputsCount == 0 && num != 0)
            || (1..5).contains(inputsCount)) {
            inputs.add(num)
            inputs.removeAt(0)
            ++inputsCount
            invalidateLabels()
        }
    }

    private fun onBackspace() {
        if (0 < inputsCount) {
            inputs.add(0, 0)
            inputs.removeAt(inputs.lastIndex)
            --inputsCount
            invalidateLabels()
        }
    }

    private fun onBackspaceLong() {
        inputs.clear()
        inputs.addAll(defaultInputs)
        inputsCount = 0
        invalidateLabels()
    }

    private fun invalidateLabels() {
        var label = "${inputs[0]}${inputs[1]}"
        hoursLabel.text = label
        label = "${inputs[2]}${inputs[3]}"
        minutesLabel.text = label
        label = "${inputs[4]}${inputs[5]}"
        secondsLabel.text = label
    }
}
