package com.example.timer

import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.View
import android.widget.NumberPicker
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.timer.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
    private val dataModel: DataModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hourPicker = findViewById<NumberPicker>(R.id.hourPicker)
        val minutePicker = findViewById<NumberPicker>(R.id.minutePicker)
        val secondPicker = findViewById<NumberPicker>(R.id.secondPicker)

        val hourValues = (0..23).map { it.toString() }.toTypedArray()
        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        hourPicker.displayedValues = hourValues
        hourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        hourPicker.textSize = 80f
        hourPicker.setTextColor(ContextCompat.getColor(this, R.color.white))

        val minuteValues = (0..59).map { String.format("%02d", it) }.toTypedArray()
        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.displayedValues = minuteValues
        minutePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        minutePicker.textSize = 80f
        minutePicker.setTextColor(ContextCompat.getColor(this, R.color.white))

        val secondValues = (0..59).map { String.format("%02d", it) }.toTypedArray()
        secondPicker.minValue = 0
        secondPicker.maxValue = 59
        secondPicker.displayedValues = secondValues
        secondPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        secondPicker.textSize = 80f
        secondPicker.setTextColor(ContextCompat.getColor(this, R.color.white))

        binding.bStart.setBackgroundColor(ContextCompat.getColor(this, R.color.violet))

        binding.apply {
            bStart.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentTimer,BlankFragmentTimer.newInstance())
                    .commit()
                hourPicker.visibility = View.GONE
                minutePicker.visibility = View.GONE
                secondPicker.visibility = View.GONE
                bStart.visibility = View.GONE

                val selectedHour = hourPicker.value
                val selectedMinute = minutePicker.value
                val selectedSecond = secondPicker.value
                dataModel.massage.value = (selectedHour * 60 * 60 * 1000 + selectedMinute * 60 * 1000 + selectedSecond * 1000).toLong()
            }
        }

        dataModel.massage2.observe(this, {
            if(it) {
                hourPicker.visibility = View.VISIBLE
                minutePicker.visibility = View.VISIBLE
                secondPicker.visibility = View.VISIBLE
                binding.bStart.visibility = View.VISIBLE
            }
        })
    }
}