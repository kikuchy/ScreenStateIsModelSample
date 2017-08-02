package net.kikuchy.loaddatascore.stargazer.network

import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.stargazer.Stargazer

/**
 * Stargazer読み込みの通信状況。
 */
sealed class StargazerNetworkModelState {
    /** まだ読み込みをしていない */
    object NeverFetched : StargazerNetworkModelState()
    /** 読み込み中 */
    object Fetching : StargazerNetworkModelState()
    /** 読み込みが終わって結果が出た */
    data class Fetched(val result: Result<List<Stargazer>, Throwable>) : StargazerNetworkModelState()
}