package com.example.aplicacionmovilinesmr.data.sources.remote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.aplicacionmovilinesmr.data.repositories.AuthRepository
import com.example.aplicacionmovilinesmr.data.repositories.UbiRepository
import com.example.aplicacionmovilinesmr.data.sources.remote.AuthService
import com.example.aplicacionmovilinesmr.data.sources.remote.UbiService
import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterMoshiFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

    @Singleton
    @Provides
    fun provideRetrofitAuth(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.138:8081/auth/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("ubi")
    fun provideRetrofitUbi(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.138:8081/ubi/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofitAuth: Retrofit): AuthService =
        retrofitAuth.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideUbiService(@Named("ubi") retrofitUbi: Retrofit): UbiService =
        retrofitUbi.create(UbiService::class.java)


    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
