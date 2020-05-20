package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.repository.CoronaRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaViewModel(private val repository: CoronaRepository) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    val countries : LiveData<List<Country>>
        get() = _countries

    fun loadCoronaList(filter: Filter = Filter.cases){
        GlobalScope.launch {
            _countries.postValue(repository.requestCountries(filter))
        }
    }
}