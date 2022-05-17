package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{ calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)  }
    }
    private fun calculateTip()
    {
        binding.tipResult.text = ""
        var stringInTextField = binding.costOfServiceEditText.text.toString()
        var cost = stringInTextField.toDoubleOrNull();
        if (cost == null)
            return
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId)
        {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        
        var tip = cost * tipPercentage + cost

        if(binding.roundUpSwitch.isChecked)
            tip = kotlin.math.ceil(tip)

        val tipFormatted = NumberFormat.getCurrencyInstance().format(tip)
        val tipResult = getString(R.string.tip_amount, tipFormatted)
        binding.tipResult.text= tipResult
    }

    //Helper function to hide the keyboard after pressing enter
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}