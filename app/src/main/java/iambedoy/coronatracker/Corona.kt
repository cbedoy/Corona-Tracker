package iambedoy.coronatracker

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
data class Corona(
    val country: String,
    val cases: Long = 0,
    val totalCases: Long = 0,
    val deaths: Long = 0,
    val todayCases: Long = 0,
    val todayDeaths: Long = 0,
    val recovered: Long = 0,
    val active: Long = 0,
    val critical: Long = 0,
    val casesPerOneMillion: Long = 0
)