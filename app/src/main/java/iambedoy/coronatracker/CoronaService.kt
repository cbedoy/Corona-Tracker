package iambedoy.coronatracker

import retrofit2.http.GET

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
interface CoronaService {
    @GET("countries")
    suspend fun getCountries() : List<Corona>
}