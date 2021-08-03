package es.webandroid.xing.framework.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkServiceProvider {
    companion object {
        private const val CONNECT_TIMEOUT: Long = 15
        private const val READ_TIMEOUT: Long = 15
        private const val BASE_URL = "https://api.github.com/orgs/"
    }

    private fun client(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .build()
    }

    private fun moshi() = Moshi.Builder().build()

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .client(client())
            .build()
    }

    fun networkApi(): NetworkApi = retrofit().create(NetworkApi::class.java)
}
