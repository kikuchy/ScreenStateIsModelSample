package net.kikuchy.loaddatascore.stargazer.list

import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.stargazer.Stargazer

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
sealed class StargazerListModelState {
    object NeverFetched : StargazerListModelState() {
        override val page: Int = 0
        override val stargazers: List<Stargazer> = emptyList()
    }

    data class Fetching(
            override val page: Int,
            val lastResult: Result<List<Stargazer>, Throwable>,
            val isPageEnd: Boolean
    ) : StargazerListModelState() {
        override val stargazers: List<Stargazer>
            get() = lastResult.unwrapped
    }

    data class Fetched(
            override val page: Int,
            val lastResult: Result<List<Stargazer>, Throwable>,
            val isPageEnd: Boolean
    ) : StargazerListModelState() {
        override val stargazers: List<Stargazer>
            get() = lastResult.unwrapped
    }

    abstract val page: Int
    abstract val stargazers: List<Stargazer>
}