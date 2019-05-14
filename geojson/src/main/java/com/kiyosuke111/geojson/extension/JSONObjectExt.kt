package com.kiyosuke111.geojson.extension

import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.toMap(): Map<String, Any?> {
    val keys = this.keys()
    if (!keys.hasNext()) return emptyMap()
    val result: MutableMap<String, Any?> = mutableMapOf()
    while (keys.hasNext()) {
        val key = keys.next()
        val value = this.get(key)
        result[key] = value
    }
    return result
}

fun Map<String, Any?>.toJson(): JSONObject {
    val json = JSONObject()
    this.forEach { (key, value) ->
        if (value != null) {
            json.put(key, value)
        } else {
            json.put(key, JSONObject.NULL)
        }
    }
    return json
}

fun JSONObject.getStringOrNull(name: String): String? {
    return if (isNull(name)) null else this.optString(name)
}

fun JSONObject.getIntOrNull(name: String): Int? {
    return if (isNull(name)) null else this.optInt(name)
}

fun JSONObject.getLongOrNull(name: String): Long? {
    return if (isNull(name)) null else this.optLong(name)
}

fun JSONObject.getJSONObjectOrNull(name: String): JSONObject? {
    return if (isNull(name)) null else this.optJSONObject(name)
}

fun JSONObject.getJSONArrayOrNull(name: String): JSONArray? {
    return if (isNull(name)) null else this.optJSONArray(name)
}