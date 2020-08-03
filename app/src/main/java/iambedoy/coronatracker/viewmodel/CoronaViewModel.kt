package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xwray.groupie.kotlinandroidextensions.Item
import iambedoy.coronatracker.Filter
import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
import iambedoy.coronatracker.dto.JHUCountryState
import iambedoy.coronatracker.fragment.countries.items.CountryItem
import iambedoy.coronatracker.fragment.countries.items.GlobalItem
import iambedoy.coronatracker.repository.CoronaRepository
import kotlinx.coroutines.*

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaViewModel(private val repository: CoronaRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Item>> ()
    val items: LiveData<List<Item>>
        get() = _items

    private val _states = MutableLiveData<Map<String, MutableList<JHUCountryState>> >()
    var states : LiveData<Map<String, MutableList<JHUCountryState>>> = _states

    private val _loadingState = MutableLiveData<Boolean>()
    var loadingState : LiveData<Boolean> = _loadingState

    private val networkScope = CoroutineScope(Job() + Dispatchers.IO)

    private var _dataSource : List<Country> = emptyList()
        set(value) {
            field = value

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

            val globalRequest = async { repository.requestGlobal() }
            val countriesRequest = async { repository.requestCountries(filter) }

            _dataSource = countriesRequest.await()

            val mutableListOf = mutableListOf<Item>()

            globalRequest.await()?.let {
                mutableListOf.add(GlobalItem(it))
            }

            _dataSource.map {
                mutableListOf.add(CountryItem(it))
            }

            _items.postValue(mutableListOf)
        }
    }

    fun filterCoronaListWithQuery(query: String) {
        _searching = query.length > 1

        if (_searching ){
            networkScope.launch (Dispatchers.Main){
                val filteredCountries = _dataSource.filter {
                    it.country?.contains(query, ignoreCase = true) == true
                }
                //_countries.postValue(filteredCountries)
            }
        }else{
            //_countries.postValue(_dataSource)
        }
    }
}