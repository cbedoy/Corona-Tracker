package iambedoy.coronatracker.viewmodel

import androidx.lifecycle.ViewModel
import iambedoy.coronatracker.repository.CoronaRepository

class CountryDetailViewModel (
    private val repository: CoronaRepository
): ViewModel(){

    val historical = repository.requestAllHistorical()

    val global = repository.requestGlobal2()
}