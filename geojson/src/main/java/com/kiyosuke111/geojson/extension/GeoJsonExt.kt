package com.kiyosuke111.geojson.extension

import com.kiyosuke111.geojson.*
import org.json.JSONObject


fun JSONObject.getGeoJson(name: String): GeoJSONObject {
    val type = this.getString(GeoJsonKeys.TYPE)

    return when (GeoType.of(type)) {
        GeoType.POINT -> getGeoPoint(name)
        GeoType.LINE_STRING -> getGeoLineString(name)
        GeoType.POLYGON -> getGeoPolygon(name)
        GeoType.MULTI_POINT -> getGeoMultiPoint(name)
        GeoType.MULTI_LINE_STRING -> getGeoMultiLineString(name)
        GeoType.MULTI_POLYGON -> getGeoMultiPolygon(name)
        GeoType.GEOMETRY_COLLECTION -> getGeometryCollection(name)
        GeoType.FEATURE -> getGeoFeature(name)
        GeoType.FEATURE_COLLECTION -> getGeoFeatureCollection(name)
    }
}

fun JSONObject.getGeoPoint(name: String): GeoPoint {
    val obj = this.getJSONObject(name)
    return obj.toGeoPoint()
}

fun JSONObject.getGeoLineString(name: String): GeoLineString {
    val obj = this.getJSONObject(name)
    return obj.toGeoLineString()
}

fun JSONObject.getGeoPolygon(name: String): GeoPolygon {
    val obj = this.getJSONObject(name)
    return obj.toGeoPolygon()
}

fun JSONObject.getGeoMultiPoint(name: String): GeoMultiPoint {
    val obj = this.getJSONObject(name)
    return obj.toGeoMultiPoint()
}

fun JSONObject.getGeoMultiLineString(name: String): GeoMultiLineString {
    val obj = this.getJSONObject(name)
    return obj.toGeoMultiLineString()
}

fun JSONObject.getGeoMultiPolygon(name: String): GeoMultiPolygon {
    val obj = this.getJSONObject(name)
    return obj.toGeoMultiPolygon()
}

fun JSONObject.getGeometryCollection(name: String): GeometryCollection {
    val obj = this.getJSONObject(name)
    return obj.toGeometryCollection()
}

fun JSONObject.getGeoFeature(name: String): GeoFeature {
    val obj = this.getJSONObject(name)
    return obj.toGeoFeature()
}

fun JSONObject.getGeoFeatureCollection(name: String): GeoFeatureCollection {
    val obj = this.getJSONObject(name)
    return obj.toGeoFeatureCollection()
}

fun JSONObject.toGeoJson(): GeoJSONObject {
    val type = this.getString(GeoJsonKeys.TYPE)

    return when (GeoType.of(type)) {
        GeoType.POINT -> toGeoPoint()
        GeoType.LINE_STRING -> toGeoLineString()
        GeoType.POLYGON -> toGeoPolygon()
        GeoType.MULTI_POINT -> toGeoMultiPoint()
        GeoType.MULTI_LINE_STRING -> toGeoMultiLineString()
        GeoType.MULTI_POLYGON -> toGeoMultiPolygon()
        GeoType.GEOMETRY_COLLECTION -> toGeometryCollection()
        GeoType.FEATURE -> toGeoFeature()
        GeoType.FEATURE_COLLECTION -> toGeoFeatureCollection()
    }
}

fun JSONObject.toGeoPoint(): GeoPoint {
    checkGeoType(GeoType.POINT)
    val coordinates = this.getJSONArray(GeoJsonKeys.COORDINATES)
    if (coordinates.length() <= 0) throw IllegalArgumentException("coordinates is empty.")
    return GeoPoint(GeoPosition(coordinates))
}

fun JSONObject.toGeoLineString(): GeoLineString {
    checkGeoType(GeoType.LINE_STRING)
    val coordinates = this.getJSONArray(GeoJsonKeys.COORDINATES)
    return GeoLineString(coordinates)
}

fun JSONObject.toGeoPolygon(): GeoPolygon {
    checkGeoType(GeoType.POLYGON)
    val coordinates = this.getJSONArray(GeoJsonKeys.COORDINATES)
    return GeoPolygon(coordinates)
}

fun JSONObject.toGeoMultiPoint(): GeoMultiPoint {
    checkGeoType(GeoType.MULTI_POINT)
    val coordinates = this.getJSONArray(GeoJsonKeys.COORDINATES)
    return GeoMultiPoint(coordinates)
}

fun JSONObject.toGeoMultiLineString(): GeoMultiLineString {
    checkGeoType(GeoType.MULTI_LINE_STRING)
    val coordinates = this.getJSONArray(GeoJsonKeys.COORDINATES)
    return GeoMultiLineString(coordinates)
}

fun JSONObject.toGeoMultiPolygon(): GeoMultiPolygon {
    checkGeoType(GeoType.MULTI_POLYGON)
    val coordinates = this.getJSONArray(GeoJsonKeys.COORDINATES)
    return GeoMultiPolygon(coordinates)
}

fun JSONObject.toGeometryCollection(): GeometryCollection {
    checkGeoType(GeoType.GEOMETRY_COLLECTION)
    val geometries = this.getJSONArray(GeoJsonKeys.GEOMETRIES)
    return GeometryCollection(geometries)
}

fun JSONObject.toGeoFeature(): GeoFeature {
    checkGeoType(GeoType.FEATURE)
    return GeoFeature(this)
}

fun JSONObject.toGeoFeatureCollection(): GeoFeatureCollection {
    checkGeoType(GeoType.FEATURE_COLLECTION)
    val features = this.getJSONArray(GeoJsonKeys.FEATURES)
    return GeoFeatureCollection(features)
}

private fun JSONObject.checkGeoType(requireType: GeoType) {
    val type = this.getString(GeoJsonKeys.TYPE)
    val geoType = GeoType.of(type)
    if (geoType != requireType) throw IllegalArgumentException("type is not ${requireType.key}. (type=$type)")
}
