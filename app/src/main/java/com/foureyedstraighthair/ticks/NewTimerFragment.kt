package com.foureyedstraighthair.ticks


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NewTimerFragment : Fragment() {

    private lateinit var hoursLabel: TextView
    private lateinit var minutesLabel: TextView
    private lateinit var secondsLabel: TextView
    private lateinit var titleLabel: TextView

    private val defaultInputs = listOf(0, 0, 0, 0, 0, 0) // hh, mm, ss
    private val inputs = defaultInputs.toMutableList()
    private var inputsCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_new_timer, container, false)
        val timeInput = view.findViewById<View>(R.id.time_input)
        val numPad = view.findViewById<View>(R.id.num_pad)

        titleLabel = view.findViewById(R.id.title)
        hoursLabel = timeInput.findViewById(R.id.hours)
        minutesLabel = timeInput.findViewById(R.id.minutes)
        secondsLabel = timeInput.findViewById(R.id.seconds)
        invalidateLabels()

        view.findViewById<View>(R.id.button_options).setOnClickListener { onShowOptions() }
        numPad.findViewById<View>(R.id.button_num_0).setOnClickListener { onNumberInputted(0) }
        numPad.findViewById<View>(R.id.button_num_1).setOnClickListener { onNumberInputted(1) }
        numPad.findViewById<View>(R.id.button_num_2).setOnClickListener { onNumberInputted(2) }
        numPad.findViewById<View>(R.id.button_num_3).setOnClickListener { onNumberInputted(3) }
        numPad.findViewById<View>(R.id.button_num_4).setOnClickListener { onNumberInputted(4) }
        numPad.findViewById<View>(R.id.button_num_5).setOnClickListener { onNumberInputted(5) }
        numPad.findViewById<View>(R.id.button_num_6).setOnClickListener { onNumberInputted(6) }
        numPad.findViewById<View>(R.id.button_num_7).setOnClickListener { onNumberInputted(7) }
        numPad.findViewById<View>(R.id.button_num_8).setOnClickListener { onNumberInputted(8) }
        numPad.findViewById<View>(R.id.button_num_9).setOnClickListener { onNumberInputted(9) }
        numPad.findViewById<View>(R.id.button_check).setOnClickListener { onStartNewTimer() }
        numPad.findViewById<View>(R.id.button_backspace).apply {
            setOnClickListener { onBackspace() }
            setOnLongClickListener { onBackspaceLong(); true }
        }

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

    private fun onStartNewTimer() {

    }

    private fun onShowOptions() {

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
