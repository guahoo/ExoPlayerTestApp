package com.guahoo.exotestapp.extensions.utils

import android.content.ContentValues
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtils {

    fun parseError(response: Response<*>): APIError {

        val gson = GsonBuilder().create()
        val error: APIError

        try {
            error = gson.fromJson(response.errorBody()?.string(), APIError::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d(ContentValues.TAG, it) }
            return APIError()
        }
        return error
    }


}

data class APIError(val message: String) {
    constructor() : this("")
}