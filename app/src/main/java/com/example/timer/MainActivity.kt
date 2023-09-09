package com.example.timer

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.timer.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
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
                val selectedHour = hourPicker.value
                val selectedMinute = minutePicker.value
                val selectedSecond = secondPicker.value
                startCountDownTimer(
                    (selectedHour * 60 * 60 * 1000 + selectedMinute * 60 * 1000 + selectedSecond * 1000).toLong()
                )
            }
        }
    }

    private fun startCountDownTimer(timeMillis: Long){
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(timeM: Long) {
                val hours = (timeM / 3600000).toInt()
                val minutes = ((timeM % 3600000) / 60000).toInt()
                val seconds = ((timeM % 60000) / 1000).toInt()
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)

                binding.tvResult.text = formattedTime
            }

            override fun onFinish() {
                binding.tvResult.text = "Финиш!"
            }
        }.start()
    }
}