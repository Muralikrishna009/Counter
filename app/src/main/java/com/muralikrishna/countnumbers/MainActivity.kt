package com.muralikrishna.countnumbers

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.muralikrishna.countnumbers.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var runnable = Runnable { }
    private var myHandler = Handler(Looper.getMainLooper()!!)
    private var num = 0
    private val lastDigit = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnAdd.setOnClickListener{
//            if(num < lastDigit){
//                num++
//                binding.textView.text = dateFormat(num)
//            }
//        }

        binding.btnPause.setOnClickListener{
            num = 0
            binding.textView.text = dateFormat(num)
        }

        binding.btnStart.setOnClickListener{startCount()}

        binding.btnStop.setOnClickListener{stopCount()}


    }

    private fun startCount(){
        runnable = Runnable {
            if(num >= lastDigit){
                myHandler.removeCallbacks(runnable)
            } else {
                num++
                binding.textView.text = dateFormat(num)
                myHandler.postDelayed(runnable, 1000)
            }
        }
        myHandler.post(runnable)
    }

    private fun dateFormat(num: Int): CharSequence {
        val hrs = TimeUnit.SECONDS.toHours(num.toLong())
        val mins = TimeUnit.SECONDS.toMinutes(num.toLong())
        val secs = num%60
        var retString = if(hrs<10){
            "0$hrs :"
        } else {
            "$hrs :"
        }

        retString += if(mins < 10){
            " 0$mins :"
        } else {
            " $mins :"
        }

        retString += if(secs < 10){
            " 0$secs "
        } else {
            " $secs "
        }
        return retString
    }

    private fun stopCount(){
        myHandler.removeCallbacks(runnable)
    }

}