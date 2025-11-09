//
//package com.example.myapplication
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import androidx.activity.ComponentActivity
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.myapplication.ui.theme.MyApplicationTheme
//import android.widget.Button
//import android.widget.ListView
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.list_based_widget)
//
//        // 1. Chuan bi du lieu
//        val items = mutableListOf<String>()
//        repeat(times = 50) {
//            items.add("Item $it")
//        }
//
//        // 2. tao adapter
////        val adapter = ArrayAdapter(
////            context = this,
////            resource = android.R.layout.simple_list_item_1,
////            objects = items
////        )
//        val adapter = ArrayAdapter(
//            this,
//            R.layout.item_row,
//            R.id.textContent,
//            items
//        )
//
//        // Gan adapter cho listview
//        val listView = findViewById<ListView>(R.id.ListView)
//        listView.adapter = adapter
//
//        // Xu ly su kien on-click
//        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Log.v("Test", "${items[position]} was clicked")
//            }
//        })
//    }
//}
//
