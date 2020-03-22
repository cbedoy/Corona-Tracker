package iambedoy.coronatracker

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
object CoronaRepository {
    val service by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://corona.lmao.ninja/")
            .build()
            .create(CoronaService::class.java)
    }

    suspend fun getCountries(): List<Corona> {
        return service.getCountries()
    }
}