package net.kikuchy.loaddatas

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState

class MainActivity : LifecycleActivity() {

    val adapter: StargazerListAdapter by lazy {
        StargazerListAdapter(this)
    }

    val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.stargazers)
    }

    val refreshLayout: SwipeRefreshLayout by lazy {
        findViewById<SwipeRefreshLayout>(R.id.refresh)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val vm = ViewModelProviders.of(this).get(StargazerListViewModel::class.java)
        vm.state.observe(this, adapter)
        vm.state.observe(this, Observer { state ->
            when (state) {
                is StargazerListModelState.Fetched -> refreshLayout.isRefreshing = false
            }
        })
        recyclerView.addOnScrollListener(PageEndDetector(object: PageEndDetector.OnReachPageEnd {
            override fun onReach() {
                vm.loadNext()
            }
        }))
        refreshLayout.setOnRefreshListener {
            vm.reload()
        }
    }
}
