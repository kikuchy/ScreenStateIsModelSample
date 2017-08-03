package net.kikuchy.loaddatascore.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import net.kikuchy.loaddatascore.stargazer.Stargazer
import okhttp3.*
import java.io.IOException

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
class StargazerRepository : StargazerRepositoryContract {
    private val client: OkHttpClient = OkHttpClient()
    private val jsonParser: Gson = Gson()

    override fun getStargazers(page: Int): Single<Cursor<List<Stargazer>>> {
        val request = Request.
                Builder().
                url("https://api.github.com/repos/DroidKaigi/conference-app-2017/stargazers?page=$page").
                get().
                build()
        return Single.create { emitter ->
            client.newCall(request).apply {
                enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        emitter.onError(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.code() / 100 != 2) {
                            emitter.onError(IllegalStateException("Request failed: response = $response"))
                            return
                        }

                        val links = response.
                                headers("Link").
                                flatMap { it.trim().split(", ") }.
                                map { GithubHeaderParser.parse(it) }
                        val last = links.find { it.rel == GithubHeaderParser.Type.LAST }?.run { this.page }
                        val responseBody = response.body()?.string()
                        val gazers = responseBody?.run {
                            jsonParser.fromJson<List<Stargazer>>(this, object : TypeToken<List<Stargazer>>() {}.type)
                        }
                        if (gazers == null) {
                            emitter.onError(IllegalStateException("last page index or stargazers are not found:" +
                                    " header = ${response.headers()}" +
                                    " body = $responseBody"))
                        } else {
                            emitter.onSuccess(Cursor(page, last ?: page, gazers))
                        }
                    }
                })
            }
        }
    }
}