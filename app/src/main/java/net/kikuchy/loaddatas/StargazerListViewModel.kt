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
 * Modelを公開したり、Modelへの司令を仲介するやつ。ライフサイクルの影響を受けたくないので使用。
 */
class StargazerListViewModel : ViewModel() {
    val model = StargazerListModel(
            StargazerNetworkModel(
                    StargazerRepository()
            )
    )
    val state: LiveData<StargazerListModelState> = StargazerListLiveData(model)

    fun loadNext() {
        model.loadNext()
    }

    fun reload() {
        model.reload()
    }
}