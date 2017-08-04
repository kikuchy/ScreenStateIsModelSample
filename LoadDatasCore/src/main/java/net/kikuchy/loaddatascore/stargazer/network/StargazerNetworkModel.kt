package net.kikuchy.loaddatascore.stargazer.network

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.api.StargazerRepositoryContract

/**
 * 通信状況だけ管理するモデル。今何ページ目を読んでるのか、とかは知らない。
 */
class StargazerNetworkModel(
        private val repository: StargazerRepositoryContract
) : StargazerNetworkModelContract {

    private val stateHolder: BehaviorSubject<StargazerNetworkModelState> =
            BehaviorSubject.createDefault(StargazerNetworkModelState.NeverFetched)

    override val stateChanged: Observable<StargazerNetworkModelState> = stateHolder

    override fun load(page: Int) {
        val v = stateHolder.value
        when (v) {
            is StargazerNetworkModelState.NeverFetched -> {
                stateHolder.onNext(StargazerNetworkModelState.Fetching)
                loadImpl(page)
            }
            is StargazerNetworkModelState.Fetching -> return
            is StargazerNetworkModelState.Fetched -> {
                stateHolder.onNext(StargazerNetworkModelState.Fetching)
                loadImpl(page)
            }
        }
    }

    private fun loadImpl(page: Int) {
        repository.
                getStargazers(page)
                .map { stargazers ->
                    StargazerNetworkModelState.Fetched(Result.Success(stargazers))
                }
                .onErrorReturn { error ->
                    StargazerNetworkModelState.Fetched(Result.Failure(error))
                }
                .subscribe({ state ->
                    stateHolder.onNext(state)
                })
    }
}