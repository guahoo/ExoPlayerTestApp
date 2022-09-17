package com.guahoo.exotestapp.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.guahoo.exotestapp.R
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

fun convertSeconds(totalSecs: Int): String{
    val hours = totalSecs / 3600
    val minutes = (totalSecs % 3600) / 60
    val seconds = totalSecs % 60
    return if (hours < 1){
        String.format("%02d:%02d", minutes, seconds)
    } else {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


}


@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.loadGlidePhotoCard(
    path: Any?,
    loadPlaceHolder: Boolean = true
) {

    if (path == null || path == "") {
        Handler(Looper.getMainLooper()).post {

            Glide.with(context)
                .load(context.getDrawable(com.google.android.material.R.drawable.m3_appbar_background))
                .into(this@loadGlidePhotoCard)

        }

    } else {
        Glide.with(this)
            .load(path)
            .optionalCenterCrop()

            .addListener(object : RequestListener<Drawable> {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler(Looper.getMainLooper()).post {
                        if (loadPlaceHolder) {
                            Glide.with(context)
                                .load(context.getDrawable(R.drawable.audio))
                                .into(this@loadGlidePhotoCard)
                        }
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(this)
    }
}

