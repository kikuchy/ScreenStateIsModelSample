package net.kikuchy.loaddatascore.api

import io.reactivex.Single
import net.kikuchy.loaddatascore.stargazer.Stargazer

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
interface StargazerRepositoryContract {
    fun getStargazers(page: Int): Single<List<Stargazer>>
}