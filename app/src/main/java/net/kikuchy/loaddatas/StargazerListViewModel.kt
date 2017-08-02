package net.kikuchy.loaddatas

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import net.kikuchy.loaddatascore.api.StargazerRepositoryContract
import net.kikuchy.loaddatascore.stargazer.Stargazer
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModel
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModel
import java.net.URI

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class StargazerListViewModel : ViewModel() {
    val state: LiveData<StargazerListModelState> = StargazerListLiveData(
            StargazerListModel(
                    StargazerNetworkModel(
                            object: StargazerRepositoryContract {
                                override fun getStargazers(page: Int): Single<List<Stargazer>> {
                                    return Single.just(listOf(
                                            Stargazer("keima", URI.create("https://avatars3.githubusercontent.com/u/867470?v=4")),
                                            Stargazer("KeithYokoma", URI.create("https://avatars0.githubusercontent.com/u/872595?v=4")),
                                            Stargazer("tezooka", URI.create("https://avatars3.githubusercontent.com/u/2031516?v=4"))
                                    ))
                                }
                            }
                    )
            )
    )
}