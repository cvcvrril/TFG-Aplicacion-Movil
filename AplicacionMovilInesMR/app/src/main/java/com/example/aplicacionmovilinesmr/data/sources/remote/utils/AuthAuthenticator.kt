package com.example.aplicacionmovilinesmr.data.sources.remote.utils


import com.example.aplicacionmovilinesmr.data.sources.remote.AuthService
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.TokenManager
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        return runBlocking {
            val newAcessToken = getNewToken(refreshToken)

            newAcessToken.body()?.let {
                tokenManager.saveAccessToken(it)
                response.request.newBuilder().header("Authorization", "Bearer " + "$it").build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<String> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            //.baseUrl("http://192.168.1.138:8081/auth/")
            .baseUrl("http://192.168.104.104:8081/auth/")
            //.baseUrl(BuildConfig.BASEURLAUTH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthService::class.java)
        val res = service.refreshToken("$refreshToken")
        return res
    }
}