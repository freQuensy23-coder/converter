package com.currency_converter.ui.notifications

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.strictmode.NonSdkApiUsedViolation
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.currency_converter.R
import com.currency_converter.ui.home.get_cost
import com.currency_converter.ui.home.show_msg
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : Fragment() {

    val True = true
    val False = false

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "com.currency_converter.ui.notifications"
    private val descrption = "currencies"


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var value_own_cost :Float = 1.toFloat()
        var value_o2n_index:Int = 1
        var value_2_cost :Float = 75.00.toFloat()
        var value_2_index:Int = 5
        var value_info_cost :Float = 75.00.toFloat()
        var value_info_index : Int = 5

        val values : Array<String> = arrayOf("USD", "UAH", "GBD", "EUR", "BIT", "RUB")
        val costs = get_cost()
        select_own_value_for_notification_spinner.adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
        select_value_for_notification_spinner.adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
        select_value_for_info_spinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
        select_value_for_notification_spinner.setSelection(value_2_index)
        select_value_for_info_spinner.setSelection(value_info_index)


        notify_value_change_swithch.setOnCheckedChangeListener() { _, isChecked ->
            try {
                if (isChecked)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //На курсе была реализация уведомлений на старой версии, а я это заметил только под конец, и не успел обновить
                        var act: Activity? = activity
                        show_msg(
                            "Не пддерживается на Android Oreo и выше (SDK = 27)",
                            activity = act,
                            long = True
                        )
                    } else {
                        val builder = NotificationCompat.Builder(requireContext())
                            .setSmallIcon(android.R.drawable.alert_dark_frame)
                            .setContentTitle("Курс валют на сегодня")
                            .setOngoing(true)
                            .setContentText(
                                "${values.get(select_value_for_notification_spinner.selectedItemId.toInt())} " +
                                        " стоит на данный момент  " +
                                        "${(costs.get(select_value_for_notification_spinner.selectedItemId.toInt())) / costs.get(
                                            select_own_value_for_notification_spinner.selectedItemId.toInt()
                                        )}"
                            )
                        var notification = builder.build()

                        Log.d("notify", "Showing notification")
//                        convert_from_spinner.adapter =
//                            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
//                        convert_to_spinner.adapter =
//                            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
//                        convert_to_spinner.setSelection(DB_value_2_index)
//                        var notificationManager =
//                            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.notify(1, notification)
                    }
            }
            catch (e : Exception){
                Log.d("Exception", "Exception in notification showing ${e}")}
        }
        show_cost_switch.setOnCheckedChangeListener() { _, isChecked ->
        show_msg("Log.d('values', 'Перевод show_cost в стотсояние ${isChecked}); show_msg('Пока недоступно", activity = activity, long = True)}
    }
}

