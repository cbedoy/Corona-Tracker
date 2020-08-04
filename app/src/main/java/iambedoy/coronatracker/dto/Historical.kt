package iambedoy.coronatracker.dto

data class Historical(
    val cases: Map<String, Long> = emptyMap(),
    val deaths: Map<String, Long> = emptyMap(),
    val recovered: Map<String, Long> = emptyMap()
)