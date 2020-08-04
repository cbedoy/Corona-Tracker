package iambedoy.coronatracker

import com.haroldadmin.cnradapter.NetworkResponse
import iambedoy.coronatracker.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

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

    @GET("/v3/covid-19/jhucsse")
    suspend fun getJHUCountryState(): NetworkResponse<List<JHUCountryState>, Void>

    @GET("/v2/all")
    suspend fun getAll(): NetworkResponse<Global, String>

    @GET("/v3/covid-19/historical/all")
    suspend fun getHistorical(@Query("lastdays") lastDays: Int = 30) : NetworkResponse<Historical, Void>
}