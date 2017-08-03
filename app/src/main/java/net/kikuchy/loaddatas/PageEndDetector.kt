package net.kikuchy.loaddatas

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by hiroshi.kikuchi on 2017/08/03.
 */
class PageEndDetector(val callback: OnReachPageEnd) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val bottomOffset = 50

        if (dy > 0 && recyclerView.computeVerticalScrollRange() - bottomOffset <= recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent()) {
            callback.onReach()
        }
    }

    interface OnReachPageEnd {
        fun onReach()
    }
}