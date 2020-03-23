package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    fun loadCoronaList(){
        GlobalScope.launch {
            CoronaRepository.requestCoronaData { global, list ->
                val items = mutableListOf<Any>()

                items.add(global)
                items.addAll(list)

                _coronaList.postValue(items)
            }
        }
    }

    fun filterBy(sort: SortBy) {
        GlobalScope.launch {
            CoronaRepository.requestCoronaData { global, list ->
                val items = mutableListOf<Any>()

                items.add(global)

                val sortedCountries = when (sort) {
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
                items.addAll(sortedCountries)

                _coronaList.postValue(items)
            }
        }
    }

}