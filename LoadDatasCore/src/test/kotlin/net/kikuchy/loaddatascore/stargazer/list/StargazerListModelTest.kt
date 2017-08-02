package net.kikuchy.loaddatascore.stargazer.list

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModelContract
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModelState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class StargazerListModelTest {

    class NetModelStub : StargazerNetworkModelContract {
        val subject: PublishSubject<StargazerNetworkModelState> = PublishSubject.create()

        override val stateChanged: Observable<StargazerNetworkModelState> = subject

        override fun load(page: Int) {
        }
    }


    var netModel: StargazerNetworkModelContract? = null
    var listModel: StargazerListModelContract? = null

    @Before
    fun setUp() {
        netModel = NetModelStub()
        listModel = StargazerListModel(netModel!!)
    }


    @Test
    fun initialStateIsZeroPageAndEmptyList() {
        assertEquals(StargazerListModelState.NeverFetched, listModel!!.stateChanged.blockingFirst())
    }
}