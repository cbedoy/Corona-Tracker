package iambedoy.coronatracker.repository

import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.ServiceApi
import iambedoy.coronatracker.dto.Country


/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaRepository(private val serviceApi: ServiceApi) {
    suspend fun requestCountries(filter: Filter = Filter.cases): List<Country> {
        return serviceApi.getCountries().sortedBy {
            if (filter == Filter.deaths){
                return@sortedBy it.deaths
            }else if(filter == Filter.recovered){
                return@sortedBy it.recovered
            }
            return@sortedBy it.cases
        }.reversed()
    }
}