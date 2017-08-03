package net.kikuchy.loaddatas

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import net.kikuchy.loaddatascore.api.Cursor
import net.kikuchy.loaddatascore.api.StargazerRepository
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
                            StargazerRepository()
                    )
            )
    )
}