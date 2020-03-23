package iambedoy.coronatracker.models

/**
 * Corona Tracker
 *
 * Created by bedoy on 22/03/20.
 */
data class Global(
    val cases: Long = 0,
    val deaths: Long = 0,
    val recovered: Long = 0,
    val updated: Long = 0
)