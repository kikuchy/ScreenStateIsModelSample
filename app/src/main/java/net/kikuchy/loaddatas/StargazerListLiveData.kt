package net.kikuchy.loaddatas

import android.arch.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.stargazer.Stargazer
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelContract
import net.kikuchy.loaddatascore.stargazer.list.StargazerListModelState

/**
 * RxLifecycleを使っていればLiveDataを使う必要はない。
 */
class StargazerListLiveData(
        private val model: StargazerListModelContract
) : LiveData<StargazerListModelState>() {
    private var disposeBag = CompositeDisposable()

    init {
        model.loadNext()
    }

    override fun onInactive() {
        super.onInactive()
        disposeBag.dispose()
        disposeBag = CompositeDisposable()
    }

    override fun onActive() {
        super.onActive()

        disposeBag.add(
                model.stateChanged.observeOn(AndroidSchedulers.mainThread()).subscribe({ state ->
                    value = state
                })
        )
    }
}