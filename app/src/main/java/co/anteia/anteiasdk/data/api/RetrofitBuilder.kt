package co.anteia.anteiasdk.data.api

/*
 Created by arenas on 10/05/21.
*/

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//singleton
object RetrofitBuilder {

    private const val BASE_URL = "https://test-api.anteia.co/"

    private fun getRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }



        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .callTimeout(120,TimeUnit.SECONDS)
            .connectTimeout(120,TimeUnit.SECONDS)
            .readTimeout(120,TimeUnit.SECONDS)
            .writeTimeout(120,TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }


    val apiService : ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }


}