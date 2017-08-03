package net.kikuchy.loaddatascore.api

/**
 * Created by hiroshi.kikuchi on 2017/08/02.
 */
data class Cursor<T>(
        val currentPage: Int,
        val lastPage: Int,
        val data: T
) {
    val isPageEnd: Boolean
        get() = currentPage >= lastPage
}