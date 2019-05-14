package com.kiyosuke111.geojson

import com.kiyosuke111.geojson.extension.toGeoJson
import org.json.JSONObject

object GeoJson {

    fun parse(json: JSONObject): GeoJSONObject {
        return json.toGeoJson()
    }

    fun parse(json: String): GeoJSONObject {
        return JSONObject(json).toGeoJson()
    }
}