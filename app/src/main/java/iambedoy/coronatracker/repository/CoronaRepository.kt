package iambedoy.coronatracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.Filter.*
import iambedoy.coronatracker.ServiceApi
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
import iambedoy.coronatracker.dto.Historical
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.repository


/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaRepository(private val serviceApi: ServiceApi) {

    suspend fun requestGlobal(): Global?{
        return when(val response = serviceApi.getAll()){
            is NetworkResponse.Success -> response.body
            is NetworkResponse.NetworkError -> {
                response.error.printStackTrace()

                return null
            }
            is NetworkResponse.ServerError -> {
                response.toString()

                return null
            }
            is NetworkResponse.UnknownError -> {
                response.error.printStackTrace()

                return null
            }
        }
    }

    fun requestGlobal2() : LiveData<Global>{
        return liveData {
            when(val response = serviceApi.getAll()){
                is NetworkResponse.Success -> {
                    emit(response.body)
                }
            }
        }
    }

    suspend fun requestCountries(filter: Filter = cases): List<Country> {
        return when(val response = serviceApi.getCountries()){
            is NetworkResponse.Success -> {
                return response.body.sortedBy {
                    when (filter) {
                        cases -> {
                            return@sortedBy it.cases
                        }
                        todayCases -> {
                            return@sortedBy it.todayCases
                        }
                        deaths -> {
                            return@sortedBy it.deaths
                        }
                        todayDeaths -> {
                            return@sortedBy it.todayDeaths
                        }
                        recovered -> {
                            return@sortedBy it.recovered
                        }
                        else -> return@sortedBy it.cases
                    }
                }.reversed()
            }
            else -> emptyList()
        }
    }

    suspend fun requestJHUData(): Map<String, MutableList<JHUCountryState>> {
        val mapOf = sortedMapOf<String, MutableList<JHUCountryState>>()

        return when(val response = serviceApi.getJHUCountryState()){
            is NetworkResponse.Success -> {
                response.body.forEach {
                    val country = it.country ?: ""
                    if (!mapOf.containsKey(country)){
                        val states = mutableListOf<JHUCountryState>()
                        states.add(it)

                        mapOf[country] = states
                    }else{
                        mapOf[country]?.let { list ->
                            list.add(it)
                            mapOf.remove(country)
                            mapOf[country] = list.sortedByDescending {
                                    item -> item.stats.confirmed
                            }.toMutableList()
                        }
                    }
                }
                mapOf
            }
            else -> {
                emptyMap()
            }
        }
    }

    suspend fun requestJHUDataWithCountry(country: String = "Mexico"): Map<String, MutableList<JHUCountryState>> {
        return requestJHUData().toMutableMap().apply {
            val get = get(country)
            clear()
            set(country, get?: mutableListOf())
        }
    }

    fun requestAllHistorical(): LiveData<Historical> {
        return liveData {
            when(val response = serviceApi.getHistorical(360)){
                is NetworkResponse.Success -> {
                    emit(response.body)
                }
            }
        }
    }
}