package io.fourth_finger.sound_sculptor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.fourth_finger.sound_sculptor.AdapterLoadFile.LoadFileItem

class AdapterLoadFile(private val fileNames: Array<String>) : RecyclerView.Adapter<LoadFileItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadFileItem {
        val textViewLoadFile = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_view_load_file, parent, false) as TextView
        return LoadFileItem(textViewLoadFile)
    }

    override fun onBindViewHolder(holder: LoadFileItem, position: Int) {
        holder.textViewFileName.text = fileNames[position]
    }

    override fun getItemCount(): Int {
        return fileNames.size
    }

    class LoadFileItem(var textViewFileName: TextView) : RecyclerView.ViewHolder(
        textViewFileName
    )
}