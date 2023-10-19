package mhha.sample.changeunits

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import mhha.sample.changeunits.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var inputNumber = 0
    var cmToM = true // true cm - m

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
//        setContentView(R.layout.activity_main)
        setContentView(view)

        val inputTextView = binding.editText01
        val outputTextView = binding.textView01
        val unitTextView01 = binding.unitsTextView01
        val unitTextView02 = binding.unitsTextView02
        val swapImageButton = binding.swapImageButton

        // 기본적인 형택
//        inputTextView.addTextChangedListener { textvar ->
//            inputNumber = try {
//                textvar.toString().toInt()
//            }catch (e: NumberFormatException) {
//                0
//            } //inputTextView.addTextChangedListener
//
//            var outputNumber = if(cmToM){
//                inputNumber.times(0.01)
//            }else{
//                inputNumber.times(100)
//            }//if(cmToM)
//            outputTextView.text = outputNumber.toString()
//        } //inputTextView.addTextChangedListener

        // 발전적인 형태
        inputTextView.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                inputTextView.setTextSize(15f)
            } //override fun beforeTextChanged

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputTextView.setTextSize(30f)

                inputNumber = if(s.isNullOrEmpty()){ //null 도는 empty 일때
                    inputTextView.setTextSize(15f)
                    0
                }else{
                    s.toString().toInt()
                }

                // NumberFormatException 만 예외 처리한 것
//                inputNumber = try {
//                    s.toString().toInt()
//                }catch (e: NumberFormatException) {
//                    inputTextView.setTextSize(15f)
//                    0
//                }

                var outputNumber = if(cmToM){
                    inputNumber.times(0.01)
                }else{
                    inputNumber.times(100)
                }//if(cmToM)
                outputTextView.text = outputNumber.toString()
            } //override fun onTextChanged
            override fun afterTextChanged(s: Editable?) {

            } //override fun afterTextChanged
        }) //inputTextView.addTextChangedListener

        swapImageButton.setOnClickListener {
            cmToM = cmToM.not() // 반대값을 널을 때 == !cmToM
          if(cmToM){
              unitTextView01.text = "cm"
              unitTextView02.text = "m"
              outputTextView.text = inputNumber.times(0.01).toString()
          } else{
              unitTextView01.text = "m"
              unitTextView02.text = "cm"
              outputTextView.text = inputNumber.times(100).toString()
          }// if(cmToM)

        } //swapImageButton.setOnClickListener
    } //onCreate

    //임시저장하는 방법
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("cmToM", cmToM)
        super.onSaveInstanceState(outState)
    }//onSaveInstanceState

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmToM = savedInstanceState.getBoolean("cmToM")
        binding.unitsTextView01.text=if(cmToM) "cm" else "m"
        binding.unitsTextView02.text=if(cmToM) "m" else "cm"
        super.onRestoreInstanceState(savedInstanceState)
    }//onRestoreInstanceState

} //MainActivity