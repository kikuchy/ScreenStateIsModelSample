package net.kikuchy.loaddatascore.stargazer.list

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.stargazer.Stargazer
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModelContract
import net.kikuchy.loaddatascore.stargazer.network.StargazerNetworkModelState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.URI

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


    var netModel: NetModelStub? = null
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

    @Test
    fun firstFetch() {
        listModel!!.loadNext()
        assertEquals(
                StargazerListModelState.Fetching(1, Result.Success(emptyList())),
                listModel!!.stateChanged.blockingFirst()
        )
    }

    @Test
    fun firstFetched() {
        listModel!!.loadNext()
        val stargazers = listOf(
                Stargazer("AAA", URI.create("http://example.com/aaa.png")),
                Stargazer("BBB", URI.create("http://example.com/bbb.png"))
        )
        netModel!!.subject.onNext(StargazerNetworkModelState.Fetched(Result.Success(stargazers)))
        assertEquals(
                StargazerListModelState.Fetched(1, Result.Success(stargazers)),
                listModel!!.stateChanged.blockingFirst()
        )
    }

    @Test
    fun secondFetched() {
        listModel!!.loadNext()
        val firstStargazers = listOf(
                Stargazer("AAA", URI.create("http://example.com/aaa.png")),
                Stargazer("BBB", URI.create("http://example.com/bbb.png"))
        )
        netModel!!.subject.onNext(StargazerNetworkModelState.Fetched(Result.Success(firstStargazers)))

        listModel!!.loadNext()
        val secondStargazers = listOf(
                Stargazer("CCC", URI.create("http://example.com/ccc.png")),
                Stargazer("DDD", URI.create("http://example.com/ddd.png"))
        )
        netModel!!.subject.onNext(StargazerNetworkModelState.Fetched(Result.Success(secondStargazers)))
        assertEquals(
                StargazerListModelState.Fetched(2, Result.Success(firstStargazers + secondStargazers)),
                listModel!!.stateChanged.blockingFirst()
        )
    }

    @Test
    fun reloadClearsStargazers() {
        listModel!!.loadNext()
        val firstStargazers = listOf(
                Stargazer("AAA", URI.create("http://example.com/aaa.png")),
                Stargazer("BBB", URI.create("http://example.com/bbb.png"))
        )
        netModel!!.subject.onNext(StargazerNetworkModelState.Fetched(Result.Success(firstStargazers)))

        listModel!!.reload()
        val secondStargazers = listOf(
                Stargazer("CCC", URI.create("http://example.com/ccc.png")),
                Stargazer("DDD", URI.create("http://example.com/ddd.png"))
        )
        netModel!!.subject.onNext(StargazerNetworkModelState.Fetched(Result.Success(secondStargazers)))
        assertEquals(
                StargazerListModelState.Fetched(1, Result.Success(secondStargazers)),
                listModel!!.stateChanged.blockingFirst()
        )
    }
}