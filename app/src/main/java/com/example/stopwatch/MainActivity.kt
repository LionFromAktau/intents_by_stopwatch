package com.example.stopwatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var bundle: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bundle.root)
        setUpImpButton()
        setUpExpButton()
        Log.e(this.javaClass.name, ">>> OnCreate")
    }

    private fun setUpImpButton(){
        bundle.impIntentButton.setOnClickListener{
            if(isValid()){
                val intent = Intent(this, SendActivity::class.java)
                intent.putExtra(IntentType.IMP.name, bundle.secondCountText.text.toString())
                startActivity(intent)
            }else{
                Toast.makeText(this, "Enter Seconds", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpExpButton(){
        bundle.expIntentButton.setOnClickListener{
            if(isValid()){
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(IntentType.EXP.name, bundle.secondCountText.text.toString())
                intent.type = "text/plain"
                val chooseIntent = Intent.createChooser(intent, "Sending Seconds")
                startActivity(chooseIntent)
            }else{
                Toast.makeText(this, "Enter Seconds", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid() = !bundle.secondCountText.text.isNullOrBlank()

}

enum class IntentType(){
    IMP, EXP
}
