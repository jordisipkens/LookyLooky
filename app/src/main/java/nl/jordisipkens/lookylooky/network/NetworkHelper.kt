package nl.jordisipkens.lookylooky.network

import retrofit2.Retrofit

object NetworkHelper {
    private val baseUrl = "https://api.github.com/"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).build()
    }
}