package com.example.media

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by sheteng on 2017/12/13.
 */
class RecyclerViewAdapter(var resId: Int, var function: (View,Int) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHodler>() {
    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
        with(holder?.itemView!!) {
            function(holder?.itemView!!,position)
        }
    }
    var count : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHodler {
        return ViewHodler(View.inflate(parent?.context, resId, null))
    }

    override fun getItemCount(): Int {
        return count
    }

    class ViewHodler(item: View) : RecyclerView.ViewHolder(item)
}