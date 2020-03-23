package iambedoy.coronatracker.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import iambedoy.coronatracker.models.Country
import iambedoy.coronatracker.models.Global
import iambedoy.coronatracker.service.CoronaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
object CoronaRepository {
    private val service by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://corona.lmao.ninja/")
            .build()
            .create(CoronaService::class.java)
    }

    suspend fun requestCoronaData(completion: (Global, List<Country>) -> Unit){
        val all = service.getAll()
        val countries = service.getCountries()

        completion(all, countries)
    }
}