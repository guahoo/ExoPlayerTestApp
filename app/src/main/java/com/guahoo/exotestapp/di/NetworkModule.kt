package com.guahoo.exotestapp.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import com.guahoo.exotestapp.BuildConfig
import com.guahoo.exotestapp.network.AppApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val networkModule = module {




        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY

        }



        val gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create()

        val okHttpClient =
            OkHttpClient.Builder()

                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(httpLogging)
                .protocols(listOf(Protocol.HTTP_1_1))
                .build()


        val appApi = Retrofit.Builder()

            .addConverterFactory(ScalarsConverterFactory.create())
           // .addConverterFactory(ToStringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl("https://api.mobimusic.kz/?")
            .build().create(AppApi::class.java)


        factory<Gson> { gson }
        factory { httpLogging }
        factory<OkHttpClient> { okHttpClient }
        factory<AppApi> { appApi }


    }



