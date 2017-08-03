package net.kikuchy.loaddatas

import android.arch.lifecycle.Observer
import android.support.design.widget.Snackbar
import android.view.View
import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState

/**
 * エラー表示するだけのやつ
 */
class StargazerAlertPresenter(private val parentView: View) : Observer<StargazerListModelState> {

    var callback: (() -> Unit)? = null

    override fun onChanged(t: StargazerListModelState?) {
        t ?: return

        when (t) {
            is StargazerListModelState.Fetched -> {
                val lastResult = t.lastResult
                when (lastResult) {
                    is Result.Failure ->
                        Snackbar
                                .make(parentView, "読み込み中にエラーが起きました", Snackbar.LENGTH_INDEFINITE)
                                .setAction("再読み込み") {
                                    callback?.invoke()
                                }
                                .show()
                }
            }
        }

    }
}