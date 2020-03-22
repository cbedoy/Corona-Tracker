package iambedoy.coronatracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
class CoronaViewModel : ViewModel() {

    private val _coronaList = MutableLiveData<List<Corona>>()
    val coronaList : LiveData<List<Corona>> = _coronaList


    fun loadCoronaList(){
        GlobalScope.launch {
            val countries = CoronaRepository.getCountries()
            _coronaList.postValue(countries)
        }
    }

}