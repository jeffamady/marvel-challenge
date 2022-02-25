package com.amadydev.intermedia.data.repositories

import com.amadydev.intermedia.data.models.NetResult
import com.amadydev.intermedia.utils.Constants.PRIVATE_API_KEY
import com.amadydev.intermedia.utils.Constants.PUBLIC_API_KEY
import retrofit2.Response
import java.security.MessageDigest
import java.util.*

abstract class BaseRepository {

    protected val authParams = AuthParams(PUBLIC_API_KEY, 1, generateHash())

    protected fun <T> handleResult(result: Response<T>): NetResult<T> {
        if (result.isSuccessful)
            result.body()
                ?.let { content -> return NetResult.Success(content) }
        return NetResult.Error(result)
    }

    protected class AuthParams(
        private val apiKey: String,
        private val ts: Int,
        private val hash: String
    ) {
        fun getMap(): HashMap<String, String> {
            val hashMap = HashMap<String, String>()
            hashMap["apikey"] = apiKey
            hashMap["ts"] = ts.toString()
            hashMap["hash"] = hash
            return hashMap
        }
    }

    private fun generateHash(): String = MessageDigest.getInstance("MD5")
        .digest(("1${PRIVATE_API_KEY}${PUBLIC_API_KEY}").toByteArray())
        .joinToString("") { "%02x".format(it) }
}