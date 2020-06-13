package com.currency_converter.ui.home

import android.app.Activity
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.currency_converter.R
import kotlinx.android.synthetic.main.fragment_home.*
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat.getSystemService
import org.w3c.dom.Text


fun show_msg(text:String, long:Boolean = false, show_log:Boolean = true, activity: Activity?){
//    global
    val duration = Toast.LENGTH_SHORT
    if (long){val duration = Toast.LENGTH_LONG}

    Log.d("activity_debug", activity.toString() + "Showing ${text} messege, duration = ${duration.toString()}")
    val toast = Toast.makeText(activity, text, duration)
    toast.show()
}


fun log_Intval(value : Int, name : String){
    Log.d("values", "${name} = $value")
}

fun log_Boolval(value : Boolean, name : String){
    Log.d("values", "${name} = $value")
}

fun log_Floatval(value : Float, name : String){
    Log.d("values", "${name} = $value")
}


fun swap(b:Boolean):Boolean{
    if (b){return false}
    else{return true}
}

fun get_cost():Array<Float>{
    val USD : Float = 75.00.toFloat()
    val UAH : Float = 2.62.toFloat()
    val GBD : Float = 87.93.toFloat()
    val EUR : Float = 79.01.toFloat()
    val BIT : Float = 659000.1.toFloat()
    val RUB : Float = 1.0.toFloat()
    val values : Array<String> = arrayOf("USD", "UAH", "GBD", "EUR", "BIT", "RUB")
    val costs : Array<Float>   = arrayOf( USD,   UAH,   GBD,   EUR,   BIT,   RUB)
    return costs
}

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onViewCreated(view, savedInstanceState)

        var use_database_values: Boolean = true
        var DB_value_1_cost: Float = 75.00.toFloat()
        var DB_value_1_index: Int = 1
        var DB_value_2_cost: Float = 75.00.toFloat()
        var DB_value_2_index: Int = 5

        //Creating values arrays

        val costs = get_cost()
        val values: Array<String> = arrayOf("USD", "UAH", "GBD", "EUR", "BIT", "RUB")


        try {
            convert_from_spinner.adapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
            convert_to_spinner.adapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
            convert_to_spinner.setSelection(DB_value_2_index)
        } catch (e: Exception) {
            Log.d("exception_adapter", "Exception on creationing adapter $e")
        }

        var use_comission: Int = 0
        var comis_percent: Int = 2
        var not_less: Int = 10

        var comis_total: Float = 0.toFloat()

        result_editText.setEnabled(false);
        result_editText.setCursorVisible(false);
        Log.d("tag", result_editText.text.toString())

        //TODO Сделать форматирование что бы выводилоось только 2 знака после запятой в поле результата
        //TODO Исправить комиссию банка она не работае ткоректно


        cost_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("editText", "cost_editText 3")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("editText", "cost_editText 2")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("editText", "cost_editText changed")
                try {
                    use_database_values = false
                } catch (e: Exception) {
                    Log.d("Exception", "Use database exc. ${e}")
                }
            }
        })

        do_convert_button.setOnClickListener {
            Log.d("button", "Convert_button_pressed")
            try {
                var cost: Float = cost_editText.text.toString().toFloat()
                var sum: Float = sum_editText.text.toString().toFloat()
                var result: Float = cost * sum

                log_Intval(use_comission, "use comission")
                if (use_comission == 1) {
                    comis_percent = comission_editText.text.toString().toInt()
                    try {
                        not_less = not_less_editText.text.toString().toInt()
                    } catch (e: Exception) {
                        not_less = 0
                        not_less_editText.setText("0")
                    }

                    comis_total = ((comis_percent.toFloat() / 100) * sum * cost)
                    Log.d("number", "comis_total = ${comis_total}")
                    if (comis_total < not_less) {
                        Log.d("msg_info", "Comision is very low. Using not_less")
                        comis_total = not_less.toFloat()
                    } else {
                        Log.d("msg_info", "Comision is ok. Use %")
                    }
                } else {
                    comis_total = 0.toFloat()
                }
                if ((result - comis_total) >= 0) {
                    result_editText.setText((result - comis_total).toString())
                } else {
                    val text = "Невохможная операция. Результат < 0"
                    val activity: Activity? = activity
                    show_msg(text, activity = activity)
                }
            } catch (e: Exception) {
                val text = "Введите корректные значения."
                val activity: Activity? = activity
                show_msg(text, activity = activity)
            }
        }

        do_replace_button.setOnClickListener {
            Log.d("button", "replace_button_pressed")

            if (use_database_values) {
                Log.d("values", "Converting using DB")
                var tmp: Int = convert_from_spinner.selectedItemId.toInt()
                Log.d("values", "tmp = ${tmp}")
                convert_from_spinner.setSelection(convert_to_spinner.selectedItemId.toInt())
                Log.d(
                    "values",
                    "convert_to_spinner.selectedItemId = ${convert_to_spinner.selectedItemId.toInt()}"
                )
                try {
                    convert_to_spinner.setSelection(tmp)
                } catch (e: Exception) {
                    Log.d("Exception", "Exception on spinner changing $e")
                }
//                finally {
//                    Log.d("Exception", "Smth going wrong finaly in spinner")
//                }
            } else {
                Log.d("values", "Converting using HAND WRITING")
                try {
                    var cost: Float = cost_editText.text.toString().toFloat()
                    if (cost.toDouble() == 0.0) {
                        Log.d("values", "cost could't be ZERO, ${cost}")
                        val text = "Стоимость не может быть 0."
                        val activity: Activity? = activity
                        show_msg(text, activity = activity)
                    } else {
                        cost_editText.setText((1 / cost).toString())
                    }
                } catch (e: Exception) {
                    Log.d("values", "enter cost please")
                    val text = "Введите стоимость валюты."
                    val activity: Activity? = activity
                    show_msg(text, activity = activity)
                }
            }
        }
        comission_use_swich.setOnCheckedChangeListener() { _, isChecked ->
            if (isChecked) {
                Log.d("switch", "Use comission")
                use_comission = 1
            } else {
                Log.d("switch", "Don't use comission")
                use_comission = 0
            }
        }


        do_copy_button.setOnClickListener{}

        convert_from_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var pos2 = convert_to_spinner.selectedItemId.toInt()
                cost_editText.setText((costs.get(pos2) / costs.get(position)).toString())
                use_database_values = true
            }
        }

        convert_to_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("values", "Use DB values = ${use_database_values}. Convert to spinner item selected")
                var pos2 = convert_from_spinner.selectedItemId.toInt()
                cost_editText.setText((costs.get(pos2) / costs.get(position)).toString())
                use_database_values = true
            }
        }

    }
}




