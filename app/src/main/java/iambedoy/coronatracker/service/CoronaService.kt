package iambedoy.coronatracker.service

import iambedoy.coronatracker.models.Country
import iambedoy.coronatracker.models.Global
import retrofit2.http.GET

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
interface CoronaService {
    @GET("countries")
    suspend fun getCountries() : List<Country>

    @GET("all")
    suspend fun getAll() : Global
}