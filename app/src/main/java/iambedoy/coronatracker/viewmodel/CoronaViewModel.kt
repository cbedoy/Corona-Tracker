package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.coronatracker.repository.CoronaRepository
import kotlinx.coroutines.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaViewModel(private val repository: CoronaRepository) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    var countries : LiveData<List<Country>> = _countries

    private val _global = MutableLiveData<Global>()
    var global : LiveData<Global> = _global

    private val _states = MutableLiveData<Map<String, MutableList<JHUCountryState>> >()
    var states : LiveData<Map<String, MutableList<JHUCountryState>>> = _states

    private val _loadingState = MutableLiveData<Boolean>()
    var loadingState : LiveData<Boolean> = _loadingState

    private val networkScope = CoroutineScope(Job() + Dispatchers.IO)

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
        networkScope.launch {
            _loadingState.postValue(true)
            repository.requestJHUData().let {
                _jhuDataSource = it
            }
        }
    }

    fun loadJHUDataWithCountry(country: String) {
        networkScope.launch {
            _loadingState.postValue(true)
            repository.requestJHUDataWithCountry(country).let {
                _jhuDataSource = it
            }
        }
    }

    fun loadCountryList(filter: Filter = Filter.cases){
        networkScope.launch {
            _loadingState.postValue(true)
            _dataSource = repository.requestCountries(filter)
            repository.requestGlobal()?.let {
                _global.postValue(it)
            }
        }
    }

    fun filterCoronaListWithQuery(query: String) {
        _searching = query.length > 1

        if (_searching ){
            networkScope.launch (Dispatchers.Main){
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