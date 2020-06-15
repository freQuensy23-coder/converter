package com.currency_converter.ui.dashboard

import android.app.Activity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.currency_converter.R
import com.currency_converter.ui.home.show_msg
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jsoup.Jsoup


class DashboardFragment : Fragment() {
    private lateinit var dashboardViewModel: DashboardViewModel

    val url = "https://www.cbr.ru/scripts/XML_daily.asp"  //Курсы с сайта цб


//    fun get_data(){
////            update_text.setText(URL("https://google.com").readText().toString())
//        Log.d("button", "update_DB_clicked")
//        try{
//            var doc = Jsoup.connect(url).get()
//        }
//        catch (e : Exception){
//            Log.d("Exception", "Exception on get DB clicked = ${e}")
//        }
//    }





    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        do_update_button.setOnClickListener{
//            get_data()

            val act : Activity? = activity
            show_msg("Пока недоступно", long = false, activity = act)
        }
    }



}
