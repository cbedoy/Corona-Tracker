package iambedoy.coronatracker.dto

import com.squareup.moshi.Json

/**
 * Corona Tracker
 *
 * Created by bedoy on 19/05/20.
 */
data class CountryInfo(
    @field:Json(name = "_id") val id: Long? = null,
    @field:Json(name = "iso2") val iso2: String? = null,
    @field:Json(name = "iso3") val iso3: String? = null,
    @field:Json(name = "lat") val lat: Double? = null,
    @field:Json(name = "long") val long: Double? = null,
    @field:Json(name = "flag") val flag: String? = null
)