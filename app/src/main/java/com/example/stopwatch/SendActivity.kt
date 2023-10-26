package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.stopwatch.databinding.ActivitySendBinding
import java.util.Locale

class SendActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendBinding

    private var seconds = 0
    private var running = false
    private var preAction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            var a = it.getString(IntentType.IMP.name)?.toInt() ?: 0
            var b = it.getString(IntentType.EXP.name)?.toInt() ?: 0
            seconds = Math.max(a, b)
        }

        with(binding){
            savedInstanceState?.let {
                seconds = it.getInt(State.Seconds.name)
                running = it.getBoolean(State.Running.name)
            }
            runTimer()
            startButton?.setOnClickListener{
                startClick()
            }
            stopButton?.setOnClickListener{
                stopClick()
            }
            resetButton?.setOnClickListener {
                resetClick()
            }
        }
        Log.e(this.javaClass.name, ">>> OnCreate ${running}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(State.Seconds.name, seconds)
        outState.putBoolean(State.Running.name, preAction)
        super.onSaveInstanceState(outState)
    }

    private fun runTimer(){
        val  handler = Handler(Looper.getMainLooper())
        handler.post(object: Runnable {
            override fun run() {
                if(seconds >= 0){
                    val minutes = seconds / 60
                    val time = String.format(Locale.getDefault(), "%02d : %02d", minutes, seconds % 60)
                    binding.timeview?.setText(time)
                    if(running){
                        seconds--
                    }
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun startClick(){
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show()
        running = true
    }

    private fun stopClick(){
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
        running = false
    }

    private fun resetClick(){
        Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show()
        seconds = 0
        running = false
    }

    override fun onStart() {
        super.onStart()
        if(preAction){
            startClick()
        }
        Log.e(this.javaClass.name, ">>> OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(this.javaClass.name, ">>> OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(this.javaClass.name, ">>> OnPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(this.javaClass.name, ">>> OnRestart")
    }

    override fun onStop() {
        super.onStop()
        preAction = running
        stopClick()
        Log.e(this.javaClass.name, ">>> OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(this.javaClass.name, ">>> OnDestroy ${running}")
    }
}


enum class State{
    Running, Seconds
}