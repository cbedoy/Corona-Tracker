package iambedoy.coronatracker.dto


/**
 * Corona Tracker
 *
 * Created by bedoy on 19/05/20.
 */
data class Country(
    val countryInfo: CountryInfo?= null,
    val updated: Double = 0.0,
    val country: String? = null,
    val cases: Int = 0,
    val todayCases: Int = 0,
    val deaths: Int = 0,
    val todayDeaths: Int = 0,
    val recovered:Int = 0,
    val active: Int = 0,
    val critical: Int = 0,
    val casesPerOneMillion: Double = 0.0,
    val deathsPerOneMillion: Double = 0.0,
    val tests: Int = 0,
    val testsPerOneMillion: Double = 0.0,
    val population: Int = 0,
    val continent: String = ""
)