package iambedoy.coronatracker

import com.haroldadmin.cnradapter.NetworkResponse
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
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
    suspend fun getCountries(): NetworkResponse<List<Country>, Void>

    @GET("/v2/states")
    suspend fun getStates(): NetworkResponse<List<State>, Void>

    @GET("v2/jhucsse")
    suspend fun getJHUCountryState(): NetworkResponse<List<JHUCountryState>, Void>

    @GET("/v2/all")
    suspend fun getAll(): NetworkResponse<Global, String>
}