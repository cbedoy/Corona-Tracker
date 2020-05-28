package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.coronatracker.repository.CoronaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaViewModel(private val repository: CoronaRepository) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    var countries : LiveData<List<Country>> = _countries

    private val _states = MutableLiveData<Map<String, MutableList<JHUCountryState>> >()
    var states : LiveData<Map<String, MutableList<JHUCountryState>> > = _states


    private val _loadingState = MutableLiveData<Boolean>()
    var loadingState : LiveData<Boolean> = _loadingState

    private var _dataSource : List<Country> = emptyList()
        set(value) {
            field = value

            _countries.postValue(value)
            _loadingState.postValue(false)
        }

    private var _jhuDataSource : Map<String, MutableList<JHUCountryState>>  = emptyMap()
        set(value) {
            field = value

            _states.postValue(value)
            _loadingState.postValue(false)
        }

    private var _searching = false

    fun loadJHUData() {
        GlobalScope.launch {
            _loadingState.postValue(true)
            withContext(Dispatchers.Default){
                repository.requestJHUData().let {
                    _jhuDataSource = it
                }
            }
        }
    }

    fun loadJHUDataWithCountry(country: String) {
        GlobalScope.launch {
            _loadingState.postValue(true)
            withContext(Dispatchers.Default){
                repository.requestJHUDataWithCountry(country).let {
                    _jhuDataSource = it
                }
            }
        }
    }

    fun loadCoronaList(filter: Filter = Filter.cases){
        GlobalScope.launch {
            _loadingState.postValue(true)
            withContext(Dispatchers.Default){
                _dataSource = repository.requestCountries(filter)
            }
        }
    }

    fun filterCoronaListWithQuery(query: String) {
        _searching = query.length > 1

        if (_searching ){
            GlobalScope.launch (Dispatchers.Main){
                val filteredCountries = _dataSource.filter {
                    it.country?.contains(query, ignoreCase = true) == true
                }
                _countries.postValue(filteredCountries)
            }
        }else{
            _countries.postValue(_dataSource)
        }
    }
}