package iambedoy.coronatracker.dto

/**
 * Corona Tracker
 *
 * Created by bedoy on 29/05/20.
 */
data class Global(
    val updated: Double = 0.0,
    val cases: Long = 0,
    val todayCases: Long = 0,
    val deaths: Long = 0,
    val todayDeaths: Long = 0,
    val recovered:Long = 0,
    val active: Long = 0,
    val critical: Long = 0,
    val casesPerOneMillion: Double = 0.0,
    val deathsPerOneMillion: Double = 0.0,
    val tests: Long = 0,
    val testsPerOneMillion: Double = 0.0,
    val population: Long = 0
)