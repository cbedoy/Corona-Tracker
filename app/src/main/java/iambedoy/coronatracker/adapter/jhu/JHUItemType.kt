package iambedoy.coronatracker.adapter.jhu

import iambedoy.coronatracker.dto.JHUCountryState

/**
 * Corona Tracker
 *
 * Created by bedoy on 29/05/20.
 */
sealed class JHUItemType {
    data class Header(val countryName: String) : JHUItemType()
    data class Item(val countryState: JHUCountryState) : JHUItemType()
}