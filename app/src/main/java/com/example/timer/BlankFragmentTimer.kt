package com.example.timer

import android.R
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.timer.databinding.FragmentBlankTimerBinding
import java.util.Locale

class BlankFragmentTimer : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var binding: FragmentBlankTimerBinding
    private var timer: CountDownTimer? = null
    var timeStart: Long = 0
    private var paused = false
    private var remainingTimeMillis: Long = 0

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
            timeStart = it
        })

        binding.bPauseFragment.setOnClickListener {
            pauseCountDownTimer()
        }

        binding.bCloseFragment.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            dataModel.massage2.value = true
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragmentTimer()
    }

    private fun startCountDownTimer(timeMillis: Long) {
        if (paused) {
            timer?.cancel()
            timer = createCountDownTimer(remainingTimeMillis)
            timer?.start()
            paused = false
        }
        else {
            timer?.cancel()
            timer = createCountDownTimer(timeMillis)
            timer?.start()
        }
    }

    private fun createCountDownTimer(timeMillis: Long): CountDownTimer {
        return object : CountDownTimer(timeMillis, 10) {
            override fun onTick(timeM: Long) {
                remainingTimeMillis = timeM
                val hours = (timeM / 3600000).toInt()
                val minutes = ((timeM % 3600000) / 60000).toInt()
                val seconds = ((timeM % 60000) / 1000).toInt()
                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)

                val progressBar: ProgressBar = binding.progressBar
                var progress = 0
                if(timeM < 600000) {
                    progress = calculateProgress((timeM/10).toInt(), false)
                }
                else {
                    progress = calculateProgress((timeM/1000).toInt(), true)
                }
                progressBar.progress = progress

                binding.tvResultFragment.text = formattedTime
            }

            override fun onFinish() {
                binding.tvResultFragment.text = "Финиш!"
            }
        }
    }

    private fun pauseCountDownTimer() {
        timer?.cancel()
        val playIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_media_play)
        val imageViewDrawable = binding.bPauseFragment.drawable

        if (imageViewDrawable != null && imageViewDrawable.constantState != null && playIcon != null && imageViewDrawable.constantState == playIcon.constantState) {
            binding.bPauseFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_media_pause))
            startCountDownTimer(remainingTimeMillis)
        }
        else {
            paused = true
            binding.bPauseFragment.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_media_play))
        }
    }
    private fun calculateProgress(timeNow: Int, secondOrMillis: Boolean) :Int {
        if(secondOrMillis == true) {
            return ((24000 * timeNow)/(timeStart/1000)).toInt()
        }
        else {
            return ((24000 * timeNow)/(timeStart/10)).toInt()
        }
    }
}