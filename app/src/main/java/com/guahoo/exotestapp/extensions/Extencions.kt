package com.guahoo.exotestapp.extensions

import androidx.lifecycle.MutableLiveData
import com.guahoo.exotestapp.extensions.utils.ApiErrorUtils
import retrofit2.Response


fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.postValue(oldValue) }
var errorMessage: String = ""

sealed class AppResult<out T> {
    data class Success<out T>(val successData: T) : AppResult<T>()

    open class Error(
        open var exception: java.lang.Exception,
        open var message: String? = exception.localizedMessage
    ) : AppResult<Nothing>() {
    }


}

fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
    val error = ApiErrorUtils.parseError(resp)
    println("Эксептион" + Exception(error.message))

    errorMessage = Exception(error.message).toString()

    return AppResult.Error(Exception(error.message))
}


fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
    response.body()?.let {
        return AppResult.Success(it)
    } ?: return handleApiError(response)
}

