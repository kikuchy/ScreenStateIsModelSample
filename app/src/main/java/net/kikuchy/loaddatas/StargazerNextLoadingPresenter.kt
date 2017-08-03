package net.kikuchy.loaddatas

import android.arch.lifecycle.Observer
import android.view.View
import android.widget.ProgressBar
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState

/**
 * Created by hiroshi.kikuchi on 2017/08/03.
 */
class StargazerNextLoadingPresenter(private val progressRing: ProgressBar): Observer<StargazerListModelState> {
    override fun onChanged(t: StargazerListModelState?) {
        progressRing.visibility = when (t) {
            is StargazerListModelState.NeverFetched -> View.GONE
            is StargazerListModelState.Fetching -> View.VISIBLE
            is StargazerListModelState.Fetched -> View.GONE
            else -> View.GONE
        }
    }
}