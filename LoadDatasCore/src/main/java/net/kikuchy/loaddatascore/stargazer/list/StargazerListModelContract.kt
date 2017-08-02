package net.kikuchy.loaddatascore.stargazer.list

import io.reactivex.Observable


/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
interface StargazerListModelContract {
    val stateChanged: Observable<StargazerListModelState>

    fun reload()
    fun loadNext()
}