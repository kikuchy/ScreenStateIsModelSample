package net.kikuchy.loaddatascore.stargazer.list

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.mapSuccess
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModelContract
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModelState

/**
 * どのページまで読んだのか、と、今まで読んだページ内容を保持するモデル。
 */
class StargazerListModel(
        private val networkModel: StargazerNetworkModelContract
) : StargazerListModelContract {
    private val stateHolder: BehaviorSubject<StargazerListModelState> =
            BehaviorSubject.createDefault(StargazerListModelState.NeverFetched)

    override val stateChanged: Observable<StargazerListModelState> = stateHolder

    init {
        networkModel.stateChanged.subscribe({ state ->
            val prev = stateHolder.value
            when (state) {
                is StargazerNetworkModelState.Fetched -> {
                    val newGazers = state.result
                    stateHolder.onNext(
                            StargazerListModelState.Fetched(prev.page, newGazers.mapSuccess { prev.stargazers + it.data }, newGazers.unwrapped.isPageEnd)
                    )
                    // TODO: 今までの結果保持どうするか考える
                }
                is StargazerNetworkModelState.NeverFetched -> return@subscribe
                is StargazerNetworkModelState.Fetching -> return@subscribe
            }
        })
    }

    override fun reload() {
        stateHolder.onNext(StargazerListModelState.NeverFetched)
        loadNext()
    }

    override fun loadNext() {
        val v = stateHolder.value
        when (v) {
            is StargazerListModelState.NeverFetched -> {
                stateHolder.onNext(
                        StargazerListModelState.Fetching(v.page + 1, Result.Success(listOf()), false)
                )
                networkModel.load(v.page + 1)
            }
            is StargazerListModelState.Fetching -> return
            is StargazerListModelState.Fetched -> if (!v.isPageEnd) {
                stateHolder.onNext(
                        StargazerListModelState.Fetching(v.page + 1, v.lastResult, false)
                )
                networkModel.load(v.page + 1)
            }
        }
    }
}