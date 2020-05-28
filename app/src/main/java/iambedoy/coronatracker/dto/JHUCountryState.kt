package iambedoy.coronatracker.dto

/**
 * Corona Tracker
 *
 * Created by bedoy on 28/05/20.
 */
data class JHUCountryState(
    val country: String? = null,
    val updatedAt : String? = null,
    val stats : Stats = Stats(),
    val province : String? = null
)