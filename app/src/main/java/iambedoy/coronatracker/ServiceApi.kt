package iambedoy.coronatracker

import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.coronatracker.dto.State
import retrofit2.http.GET

/**
 * Corona Tracker
 *
 * Created by bedoy on 19/05/20.
 */
interface ServiceApi {
    @GET("/v2/countries")
    suspend fun getCountries(): List<Country>

    @GET("/v2/states")
    suspend fun getStates(): List<State>

    @GET("v2/jhucsse")
    suspend fun getJHUCountryState(): List<JHUCountryState>
}