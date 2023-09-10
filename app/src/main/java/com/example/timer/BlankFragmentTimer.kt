package com.example.timer

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.timer.databinding.FragmentBlankTimerBinding
import java.util.Locale

class BlankFragmentTimer : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var binding: FragmentBlankTimerBinding
    private var timer: CountDownTimer? = null
    var time_Millis: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankTimerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataModel.massage.observe(activity as LifecycleOwner, {
            startCountDownTimer(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragmentTimer()
    }

    private fun startCountDownTimer(timeMillis: Long){
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(timeM: Long) {
                val hours = (timeM / 3600000).toInt()
                val minutes = ((timeM % 3600000) / 60000).toInt()
                val seconds = ((timeM % 60000) / 1000).toInt()
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)

                binding.tvResultFragment.text = formattedTime
            }

            override fun onFinish() {
                binding.tvResultFragment.text = "Финиш!"
            }
        }.start()
    }
}