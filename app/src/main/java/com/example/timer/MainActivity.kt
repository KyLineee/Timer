package com.example.timer

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.NumberPicker
import android.widget.TextClock
import android.widget.TimePicker
import com.example.timer.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timer: CountDownTimer? = null
    private lateinit var timePicker: TimePicker
    private  lateinit var textClock: TextClock
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        timePicker = binding.timePicker

        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)
        var minut = 0
        var hour = 0

        timePicker.hour = currentHour
        timePicker.minute = currentMinute

        timePicker.setIs24HourView(true)

        val minutePicker = timePicker.findViewById<View>(Resources.getSystem().getIdentifier("minute", "id", "android"))
        if (minutePicker is NumberPicker) {
            minutePicker.maxValue = 59
            minutePicker.minValue = 0
        }

        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.set(Calendar.MINUTE, minute)

            minut = minute
            hour = hourOfDay
        }

        binding.apply {
            bStart.setOnClickListener {
                startCountDownTimer((minut * 60 * 1000 + hour * 60 * 60 * 1000).toLong())
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