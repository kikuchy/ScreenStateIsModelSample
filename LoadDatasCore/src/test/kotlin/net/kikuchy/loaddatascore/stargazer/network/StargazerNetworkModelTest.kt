package net.kikuchy.loaddatascore.stargazer.network

import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import net.kikuchy.loaddatascore.Result
import net.kikuchy.loaddatascore.api.Cursor
import net.kikuchy.loaddatascore.api.StargazerRepositoryContract
import net.kikuchy.loaddatascore.stargazer.Stargazer
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.net.URI
import java.util.concurrent.TimeoutException

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class StargazerNetworkModelTest {

    class RepositoryStub : StargazerRepositoryContract {
        var ret: SingleSubject<Cursor<List<Stargazer>>> = SingleSubject.create()
        var givenPage: Int = -1

        override fun getStargazers(page: Int): Single<Cursor<List<Stargazer>>> {
            givenPage = page
            return ret
        }
    }

    var model: StargazerNetworkModelContract? = null
    var repository: RepositoryStub? = null

    @Before
    fun setUp() {
        val repo = RepositoryStub()
        repository = repo
        model = StargazerNetworkModel(repo)
    }

    @Test
    fun initialStateIsNeverFetched() {
        assertEquals(StargazerNetworkModelState.NeverFetched, model!!.stateChanged.blockingFirst())
    }

    @Test
    fun fetchingAfterCalledLoad() {
        model!!.load(1)
        assertEquals(StargazerNetworkModelState.Fetching, model!!.stateChanged.blockingFirst())
    }

    @Test
    fun cancelAnyCommandsDuringFetching() {
        model!!.load(1)
        assertEquals(StargazerNetworkModelState.Fetching, model!!.stateChanged.blockingFirst())
        model!!.load(10)
        assertEquals(StargazerNetworkModelState.Fetching, model!!.stateChanged.blockingFirst())
        assertEquals(1, repository!!.givenPage)
    }

    @Test
    fun fetchedAfterLoadingSucceeded() {
        model!!.load(1)
        val stargazers = listOf(
                Stargazer("AAA", URI.create("http://example.com/aaa.png")),
                Stargazer("BBB", URI.create("http://example.com/bbb.png"))
        )
        repository!!.ret.onSuccess(Cursor(1, 5, stargazers))
        assertEquals(
                StargazerNetworkModelState.Fetched(Result.Success(stargazers)),
                model!!.stateChanged.blockingFirst()
        )
    }

    @Test
    fun fetchedAfterLoadingFailure() {
        model!!.load(1)
        val exception = TimeoutException("toooooooooooo loooooooooooooong!!!!!!!!")
        repository!!.ret.onError(exception)
        assertEquals(
                StargazerNetworkModelState.Fetched(Result.Failure(exception)),
                model!!.stateChanged.blockingFirst()
        )
    }

    @Test
    fun anotherPageReturnsAnotherResult() {
        model!!.load(1)
        val firstStargazers = listOf(
                Stargazer("AAA", URI.create("http://example.com/aaa.png")),
                Stargazer("BBB", URI.create("http://example.com/bbb.png"))
        )
        repository!!.ret.onSuccess(Cursor(1, 5, firstStargazers))

        repository!!.ret = SingleSubject.create()
        model!!.load(2)
        val secondStargazers = listOf(
                Stargazer("CCC", URI.create("http://example.com/ccc.png")),
                Stargazer("DDD", URI.create("http://example.com/ddd.png"))
        )
        repository!!.ret.onSuccess(Cursor(2, 5, secondStargazers))

        assertEquals(
                StargazerNetworkModelState.Fetched(Result.Success(secondStargazers)),
                model!!.stateChanged.blockingFirst()
        )
    }
}