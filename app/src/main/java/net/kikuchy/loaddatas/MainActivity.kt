package net.kikuchy.loaddatas

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState

class MainActivity : LifecycleActivity() {

    val adapter: StargazerListAdapter by lazy {
        StargazerListAdapter(
                this,
                mutableListOf()
        )
    }

    val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.stargazers)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val vm = ViewModelProviders.of(this).get(StargazerListViewModel::class.java)
        vm.state.observe(this, Observer<StargazerListModelState> { state ->
            when (state) {
                is StargazerListModelState.Fetched -> {
                    Log.d("Hoge", state.lastResult.toString())
                    adapter.stargazers.addAll(state.lastResult.unwrapped)
                    adapter.notifyDataSetChanged()
                }
            }
        })

    }
}
