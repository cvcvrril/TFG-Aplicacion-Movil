package com.example.aplicacionmovilinesmr.data.sources.remote.utils


import com.example.aplicacionmovilinesmr.data.sources.remote.AuthService
import com.example.aplicacionmovilinesmr.data.sources.remote.managerds.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = runBlocking {
            tokenManager.getAccessToken().first()
        }

        return runBlocking {
            val newAcessToken = getNewToken(accessToken)

            if (!newAcessToken.isSuccessful || newAcessToken.body() == null) { //Couldn't refresh the token, so restart the login process
                tokenManager.deleteAccessToken()
            }

            newAcessToken.body()?.let {
                tokenManager.saveAccessToken(it.toString())
                response.request.newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<Unit> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.104.104:8081/auth/")
            //.baseUrl(BuildConfig.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthService::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }

}