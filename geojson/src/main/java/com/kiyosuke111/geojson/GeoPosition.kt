package com.kiyosuke111.geojson

import org.json.JSONArray

class GeoPosition(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double? = null
) {

    constructor(jsonArray: JSONArray) : this(
        jsonArray.getDouble(INDEXES.LATITUDE),
        jsonArray.getDouble(INDEXES.LONGITUDE),
        jsonArray.optDouble(INDEXES.ALTITUDE).ifNaN { null }
    )

    fun toJsonArray(): JSONArray {
        return JSONArray().apply {
            put(INDEXES.LATITUDE, latitude)
            put(INDEXES.LONGITUDE, longitude)
            altitude?.let { altitude -> put(INDEXES.ALTITUDE, altitude) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeoPosition

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (altitude != other.altitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + altitude.hashCode()
        return result
    }

    override fun toString(): String {
        return "GeoPosition(latitude=$latitude, longitude=$longitude, altitude=$altitude)"
    }

    private object INDEXES {
        const val LONGITUDE = 0
        const val LATITUDE = 1
        const val ALTITUDE = 2
    }
}

private inline fun Double.ifNaN(defaultValue: () -> Double?): Double? {
    return if (this.isNaN()) defaultValue() else this
}