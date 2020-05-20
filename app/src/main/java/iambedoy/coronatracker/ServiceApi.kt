package iambedoy.coronatracker

import iambedoy.coronatracker.dto.Country
import retrofit2.http.GET

/**
 * Corona Tracker
 *
 * Created by bedoy on 19/05/20.
 */
interface ServiceApi {
    @GET("/v2/countries")
    suspend fun getCountries(): List<Country>
}