package com.kiyosuke111.moshi

import com.kiyosuke111.geojson.*
import com.kiyosuke111.geojson.extension.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.json.JSONObject

/**
 * MoshiがJSONをパースする時のGeoJson用Adapters
 * JSONからGeoJSONObjectにパースする処理は作成しましたが
 * GeoJSONObjectからJSONにパースする処理は作成していません
 */
object GeoJsonAdapters {

    /**
     * JsonからGeoJSONObjectに変換するAdapterの基底クラス
     */
    private abstract class GeoJsonAdapter<T : GeoJSONObject> : JsonAdapter<T>() {
        abstract fun fromJson(json: JSONObject?): T?

        override fun fromJson(reader: JsonReader): T? {
            // データクラスのTypeがGeoJSON系に一致するkeyから取れるJSONデータが
            // JSONObjectの場合LinkedHashTreeMapで返却されるためMap<String, Any?>型にキャストする
            val geomMap = reader.readJsonValue()?.let {
                it as? Map<String, Any?>
            }
            // Map<String, Any?>をJSONObjectにする拡張関数を定義しているので
            // MapをJSONObjectに変換
            val json = geomMap?.toJson()
            // JSONObjectを受け取ってGeoJSONObjectに変換するように再定義した抽象メソッドを
            // 呼び出してJson -> GeoJSONObjectへの変換処理を終了
            return fromJson(json)
        }
    }

    private val GEO_FEATURE_ADAPTER = object : GeoJsonAdapter<GeoFeature>() {
        override fun fromJson(json: JSONObject?): GeoFeature? {
            return json?.toGeoFeature()
        }

        override fun toJson(writer: JsonWriter, value: GeoFeature?) {
            throwUnsupportedException()
        }
    }

    private val GEO_FEATURE_COLLECTION_ADAPTER = object : GeoJsonAdapter<GeoFeatureCollection>() {
        override fun fromJson(json: JSONObject?): GeoFeatureCollection? {
            return json?.toGeoFeatureCollection()
        }

        override fun toJson(writer: JsonWriter, value: GeoFeatureCollection?) {
            throwUnsupportedException()
        }
    }

    private val GEO_LINE_ADAPTER = object : GeoJsonAdapter<GeoLineString>() {
        override fun fromJson(json: JSONObject?): GeoLineString? {
            return json?.toGeoLineString()
        }

        override fun toJson(writer: JsonWriter, value: GeoLineString?) {
            throwUnsupportedException()
        }
    }

    private val GEO_MULTILINE_ADAPTER = object : GeoJsonAdapter<GeoMultiLineString>() {
        override fun fromJson(json: JSONObject?): GeoMultiLineString? {
            return json?.toGeoMultiLineString()
        }

        override fun toJson(writer: JsonWriter, value: GeoMultiLineString?) {
            throwUnsupportedException()
        }
    }

    private val GEO_POINT_ADAPTER = object : GeoJsonAdapter<GeoPoint>() {
        override fun fromJson(json: JSONObject?): GeoPoint? {
            return json?.toGeoPoint()
        }

        override fun toJson(writer: JsonWriter, value: GeoPoint?) {
            throwUnsupportedException()
        }
    }

    private val GEO_MULTI_POINT_ADAPTER = object : GeoJsonAdapter<GeoMultiPoint>() {
        override fun fromJson(json: JSONObject?): GeoMultiPoint? {
            return json?.toGeoMultiPoint()
        }

        override fun toJson(writer: JsonWriter, value: GeoMultiPoint?) {
            throwUnsupportedException()
        }
    }

    private val GEO_POLYGON_ADAPTER = object : GeoJsonAdapter<GeoPolygon>() {
        override fun fromJson(json: JSONObject?): GeoPolygon? {
            return json?.toGeoPolygon()
        }

        override fun toJson(writer: JsonWriter, value: GeoPolygon?) {
            throwUnsupportedException()
        }
    }

    private val GEO_MULTI_POLYGON_ADAPTER = object : GeoJsonAdapter<GeoMultiPolygon>() {
        override fun fromJson(json: JSONObject?): GeoMultiPolygon? {
            return json?.toGeoMultiPolygon()
        }

        override fun toJson(writer: JsonWriter, value: GeoMultiPolygon?) {
            throwUnsupportedException()
        }
    }

    private val GEO_COLLECTION_ADAPTER = object : GeoJsonAdapter<GeometryCollection>() {
        override fun fromJson(json: JSONObject?): GeometryCollection? {
            return json?.toGeometryCollection()
        }

        override fun toJson(writer: JsonWriter, value: GeometryCollection?) {
            throwUnsupportedException()
        }
    }

    private fun throwUnsupportedException(): Nothing =
        throw UnsupportedOperationException("GeoJSONObject to Json function isn't implemented.")

    val FACTORY: JsonAdapter.Factory =
        JsonAdapter.Factory { type, _, _ ->
            when (type) {
                GeoLineString::class.java -> GEO_LINE_ADAPTER
                GeometryCollection::class.java -> GEO_COLLECTION_ADAPTER
                GeoMultiLineString::class.java -> GEO_MULTILINE_ADAPTER
                GeoMultiPoint::class.java -> GEO_MULTI_POINT_ADAPTER
                GeoMultiPolygon::class.java -> GEO_MULTI_POLYGON_ADAPTER
                GeoPoint::class.java -> GEO_POINT_ADAPTER
                GeoPolygon::class.java -> GEO_POLYGON_ADAPTER
                GeoFeature::class.java -> GEO_FEATURE_ADAPTER
                GeoFeatureCollection::class.java -> GEO_FEATURE_COLLECTION_ADAPTER
                else -> null
            }
        }

}