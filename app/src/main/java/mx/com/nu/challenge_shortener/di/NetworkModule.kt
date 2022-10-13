package mx.com.nu.challenge_shortener.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.nu.challenge_shortener.data.network.QuoteApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val READ_TIMEOUT: Long = 120
    private val CONNECT_TIMEOUT: Long = 120

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://url-shortener-nu.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().apply {
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                addInterceptor(
                    HttpLoggingInterceptor { message ->
                        Log.d(this.javaClass.name, "provideOkHttpClient: $message")
                    }.apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): QuoteApiClient {
        return retrofit.create(QuoteApiClient::class.java)
    }
}