package iambedoy.coronatracker.fragment.countries

import iambedoy.coronatracker.dto.Country
import iambedoy.coronatracker.dto.Global
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem

/**
 * Corona Tracker
 *
 * Created by bedoy on 12/07/20.
 */

data class CountryItem(val country: Country) : YashaItem
data class GlobalItem(val global: Global) : YashaItem

class CountryListDataSource : YashaDataSource()