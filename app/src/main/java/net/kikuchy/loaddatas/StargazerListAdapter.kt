package net.kikuchy.loaddatas

import android.arch.lifecycle.LiveData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.kikuchy.loaddatascore.stargazer.Stargazer

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class StargazerListAdapter(context: Context, val stargazers: MutableList<Stargazer>): RecyclerView.Adapter<StargazerListAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StargazerListAdapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return stargazers.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.name?.text = stargazers[position].loginName
    }

    class ViewHolder(item : View) : RecyclerView.ViewHolder(item) {
        val name: TextView = item.findViewById(R.id.name)

    }
}