package io.fourth_finger.sound_sculptor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityLoadFile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val recyclerViewLoadFile = findViewById<View>(R.id.recycler_view_load_file) as RecyclerView
        val linearLayoutManagerLoadFile: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerViewLoadFile.layoutManager = linearLayoutManagerLoadFile
        //TODO Load strings from c++ SaveFiles
        val saveFileNames = arrayOf("a", "b", "c", "d", "e")
        val recyclerViewAdapterLoadFile: RecyclerView.Adapter<*> = AdapterLoadFile(saveFileNames)
        recyclerViewLoadFile.adapter = recyclerViewAdapterLoadFile
    }
}