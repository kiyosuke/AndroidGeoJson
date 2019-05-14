package com.kiyosuke111.geojson

import com.kiyosuke111.geojson.extension.toGeoJson
import com.kiyosuke111.geojson.extension.toJson
import com.kiyosuke111.geojson.extension.toMap
import org.json.JSONArray
import org.json.JSONObject


sealed class GeoJSONObject {
    abstract val type: GeoType
    open fun toJson(): JSONObject {
        return JSONObject().apply {
            put(GeoJsonKeys.TYPE, type.key)
        }
    }
}

data class GeoLineString(val coordinates: List<GeoPosition>) : GeoJSONObject() {

    constructor(json: JSONArray) : this(json.toPositionList())

    override val type: GeoType get() = GeoType.LINE_STRING
    override fun toJson(): JSONObject {
        val json = super.toJson()
        json.put(GeoJsonKeys.COORDINATES, coordinates.toJsonArray())
        return json
    }

    companion object {
        private fun JSONArray.toPositionList(): List<GeoPosition> {
            val geoCoordinates: MutableList<GeoPosition> = mutableListOf()
            for (i in 0 until this.length()) {
                val geoArray = this.getJSONArray(i)
                geoCoordinates.add(GeoPosition(geoArray))
            }
            return geoCoordinates
        }
    }
}

data class GeometryCollection(
    val geometries: List<GeoJSONObject>
) : GeoJSONObject() {

    constructor(geometries: JSONArray) : this(geometries.toGeoJsonList())

    override val type: GeoType = GeoType.GEOMETRY_COLLECTION
    override fun toJson(): JSONObject {
        val json = super.toJson()
        val array = JSONArray()
        geometries.forEach {
            array.put(it.toJson())
        }
        json.put(GeoJsonKeys.GEOMETRIES, array)
        return json
    }

    companion object {
        private fun JSONArray.toGeoJsonList(): List<GeoJSONObject> {
            val result: MutableList<GeoJSONObject> = mutableListOf()
            for (i in 0 until this.length()) {
                val geometry = this.getJSONObject(i)
                result.add(geometry.toGeoJson())
            }
            return result
        }
    }
}

data class GeoMultiLineString(val coordinates: List<GeoLineString>) : GeoJSONObject() {

    constructor(json: JSONArray) : this(json.toGeoLineStringList())

    override val type: GeoType = GeoType.MULTI_LINE_STRING
    override fun toJson(): JSONObject {
        val json = super.toJson()
        val array = JSONArray()
        coordinates.forEach { lineStrings ->
            array.put(lineStrings.coordinates.toJsonArray())
        }
        json.put(GeoJsonKeys.COORDINATES, array)
        return json
    }

    companion object {
        private fun JSONArray.toGeoLineStringList(): List<GeoLineString> {
            val result: MutableList<GeoLineString> = mutableListOf()
            for (i in 0 until this.length()) {
                val lineJson = this.getJSONArray(i)
                result.add(GeoLineString(lineJson))
            }
            return result
        }
    }
}

data class GeoMultiPoint(
    val coordinates: List<GeoPoint>
) : GeoJSONObject() {

    constructor(json: JSONArray) : this(json.toGeoPositionList())

    override val type: GeoType get() = GeoType.MULTI_LINE_STRING
    override fun toJson(): JSONObject {
        val json = super.toJson()
        json.put(GeoJsonKeys.COORDINATES, coordinates.map(GeoPoint::coordinates).toJsonArray())
        return json
    }

    companion object {
        private fun JSONArray.toGeoPositionList(): List<GeoPoint> {
            val result: MutableList<GeoPoint> = mutableListOf()
            for (i in 0 until this.length()) {
                val array = this.getJSONArray(i)
                result.add(GeoPoint(GeoPosition(array)))
            }
            return result
        }
    }
}

data class GeoMultiPolygon(
    val coordinates: List<GeoPolygon>
) : GeoJSONObject() {

    constructor(json: JSONArray) : this(json.toGeoPolygonList())

    override val type: GeoType = GeoType.MULTI_POLYGON
    override fun toJson(): JSONObject {
        val json = super.toJson()
        val array = JSONArray()
        coordinates.forEach { geoPolygon ->
            array.put(geoPolygon.toJson())
        }
        json.put(GeoJsonKeys.COORDINATES, array)
        return json
    }

    companion object {
        private fun JSONArray.toGeoPolygonList(): List<GeoPolygon> {
            val result: MutableList<GeoPolygon> = mutableListOf()
            for (i in 0 until this.length()) {
                val array = this.getJSONArray(i)
                result.add(GeoPolygon(array))
            }
            return result
        }
    }
}

data class GeoPoint(
    val coordinates: GeoPosition
) : GeoJSONObject() {
    override val type: GeoType get() = GeoType.POINT
    override fun toJson(): JSONObject {
        val json = super.toJson()
        json.put(GeoJsonKeys.COORDINATES, coordinates.toJsonArray())
        return json
    }
}

data class GeoPolygon(val coordinates: List<List<GeoPosition>>) : GeoJSONObject() {

    constructor(json: JSONArray) : this(json.toGeoPolygon())

    override val type: GeoType = GeoType.POLYGON
    override fun toJson(): JSONObject {
        val json = super.toJson()
        val array = JSONArray()
        coordinates.forEach { polygon ->
            array.put(polygon.toJsonArray())
        }
        json.put(GeoJsonKeys.COORDINATES, array)
        return json
    }

    companion object {
        private fun JSONArray.toGeoPolygon(): List<List<GeoPosition>> {
            val result: MutableList<List<GeoPosition>> = mutableListOf()
            for (i in 0 until this.length()) {
                val l1: MutableList<GeoPosition> = mutableListOf()
                val array = this.getJSONArray(i)
                for (j in 0 until array.length()) {
                    l1.add(GeoPosition(array.getJSONArray(j)))
                }
                result.add(l1)
            }
            return result
        }
    }
}

data class GeoFeature(val geometry: GeoJSONObject?) : GeoJSONObject() {
    override val type: GeoType get() = GeoType.FEATURE

    private val properties: MutableMap<String, Any?> = mutableMapOf()

    constructor(json: JSONObject) : this(json.getJSONObject(GeoJsonKeys.GEOMETRY).toGeoJson()) {
        properties.putAll(json.optJSONObject(GeoJsonKeys.PROPERTIES).toMap())
    }

    fun getProperty(property: String): Any? = properties[property]

    fun setProperty(property: String, propertyValue: Any?) {
        properties[property] = propertyValue
    }

    override fun toJson(): JSONObject {
        val json = super.toJson()
        val geometry = this.geometry?.toJson()
        if (geometry != null) {
            json.put(GeoJsonKeys.GEOMETRY, geometry)
        } else {
            json.put(GeoJsonKeys.GEOMETRY, JSONObject.NULL)
        }
        val properties = this.properties.toJson()
        json.put(GeoJsonKeys.PROPERTIES, properties)
        return json
    }
}

data class GeoFeatureCollection(val features: List<GeoFeature>) : GeoJSONObject() {

    constructor(json: JSONArray) : this(json.toGeoFeatureList())

    override val type: GeoType get() = GeoType.FEATURE_COLLECTION
    override fun toJson(): JSONObject {
        val json = super.toJson()
        val features = JSONArray()
        this.features.forEach { feature ->
            features.put(feature.toJson())
        }
        json.put(GeoJsonKeys.FEATURES, features)
        return json
    }

    companion object {
        private fun JSONArray.toGeoFeatureList(): List<GeoFeature> {
            val features: MutableList<GeoFeature> = mutableListOf()
            for (i in 0 until this.length()) {
                val featureObj = this.getJSONObject(i)
                if (featureObj != null) {
                    features.add(GeoFeature(featureObj))
                }
            }
            return features
        }
    }
}

fun List<GeoPosition>.toJsonArray(): JSONArray {
    val array = JSONArray()
    this.forEach { geoPosition ->
        array.put(geoPosition.toJsonArray())
    }
    return array
}
