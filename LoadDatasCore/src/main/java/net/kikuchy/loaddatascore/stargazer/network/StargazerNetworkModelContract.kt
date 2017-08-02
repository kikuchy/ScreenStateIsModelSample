package net.kikuchy.loaddatascore.stargazer.network

import io.reactivex.Observable

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
interface StargazerNetworkModelContract {
    val stateChanged: Observable<StargazerNetworkModelState>

    fun load(page: Int)
}