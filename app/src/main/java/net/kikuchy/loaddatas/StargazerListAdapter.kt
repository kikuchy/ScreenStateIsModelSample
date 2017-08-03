package net.kikuchy.loaddatas

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.kikuchy.loaddatascore.stargazer.Stargazer
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class StargazerListAdapter(
        context: Context
) : RecyclerView.Adapter<StargazerListAdapter.ViewHolder>(), Observer<StargazerListModelState> {
    private val inflater = LayoutInflater.from(context)
    private val imageLoader = Picasso.with(context)
    private val stargazers: MutableList<Stargazer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StargazerListAdapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return stargazers.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder == null) return

        holder.name.text = stargazers[position].loginName
        imageLoader.load(stargazers[position].avatarUrl.toASCIIString()).into(holder.avatar)
    }

    override fun onChanged(t: StargazerListModelState?) {
        t ?: return

        stargazers.clear()
        stargazers.addAll(t.stargazers)
        notifyDataSetChanged()
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView = item.findViewById(R.id.name)
        val avatar: ImageView = item.findViewById(R.id.avatar)
    }
}