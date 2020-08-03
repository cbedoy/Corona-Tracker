package iambedoy.coronatracker.dto

/**
 * Corona Tracker
 *
 * Created by bedoy on 28/05/20.
 */
data class State(
    val updated: Double = 0.0,
    val state: String? = null,
    val cases: Int = 0,
    val todayCases: Int = 0,
    val deaths: Int = 0,
    val todayDeaths: Int = 0,
    val active: Int = 0,
    val critical: Int = 0,
    val casesPerOneMillion: Double = 0.0,
    val deathsPerOneMillion: Double = 0.0,
    val tests: Int = 0,
    val testsPerOneMillion: Double = 0.0
)