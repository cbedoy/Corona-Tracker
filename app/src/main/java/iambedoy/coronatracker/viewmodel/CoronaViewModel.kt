package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import iambedoy.coronatracker.models.Country
import iambedoy.coronatracker.repository.CoronaRepository
import iambedoy.coronatracker.models.SortBy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaViewModel : ViewModel() {

    private val _coronaList = MutableLiveData<List<Any>>()
    val coronaList : LiveData<List<Any>> = _coronaList

    private var currentSortBy : SortBy = SortBy.CASES

    fun loadCoronaList(){
        GlobalScope.launch {
            CoronaRepository.requestCoronaData { global, list ->
                val items = mutableListOf<Any>()

                global?.let {
                    items.add(global)
                }

                val sortCountries = sortCountries(list)

                if (sortCountries.isNotEmpty()){
                    items.add(currentSortBy)
                    items.addAll(sortCountries)
                }

                _coronaList.postValue(items)
            }
        }
    }

    fun filterBy(sort: SortBy) {
        GlobalScope.launch {
            CoronaRepository.requestCoronaData { global, list ->
                val items = mutableListOf<Any>()

                global?.let {
                    items.add(global)
                }

                currentSortBy = sort

                val sortCountries = sortCountries(list)

                if (sortCountries.isNotEmpty()){
                    items.add(currentSortBy)
                    items.addAll(sortCountries)
                }

                _coronaList.postValue(items)
            }
        }
    }

    private fun sortCountries(list: List<Country>): Collection<Country> {
        return when (currentSortBy) {
            SortBy.DEATHS -> {
                list.sortedByDescending { it.deaths }
            }
            SortBy.CASES -> {
                list.sortedByDescending { it.cases }
            }
            SortBy.TODAY_CASES -> {
                list.sortedByDescending { it.todayCases }
            }
            SortBy.TODAY_DEATHS -> {
                list.sortedByDescending { it.todayDeaths }
            }
            else -> {
                list.sortedByDescending { it.recovered }
            }
        }
    }

}