package iambedoy.coronatracker.repository

import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.Filter.*
import iambedoy.coronatracker.ServiceApi
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.JHUCountryState


/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaRepository(private val serviceApi: ServiceApi) {
    suspend fun requestCountries(filter: Filter = cases): List<Country> {
        return serviceApi.getCountries().sortedBy {
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

    suspend fun requestJHUData(): Map<String, MutableList<JHUCountryState>> {
        val mapOf = sortedMapOf<String, MutableList<JHUCountryState>>()

        serviceApi.getJHUCountryState().forEach {
            val country = it.country ?: ""
            if (!mapOf.containsKey(country)){
                val states = mutableListOf<JHUCountryState>()
                states.add(it)

                mapOf[country] = states
            }else{
                mapOf[country]?.let { list ->
                    list.add(it)
                    mapOf.remove(country)
                    mapOf[country] = list.sortedByDescending { item -> item.stats.confirmed }.toMutableList()
                }
            }
        }
        return mapOf
    }

    suspend fun requestJHUDataWithCountry(country: String = "Mexico"): Map<String, MutableList<JHUCountryState>> {
        return requestJHUData().toMutableMap().apply {
            val get = get(country)
            clear()
            set(country, get?: mutableListOf())
        }
    }
}