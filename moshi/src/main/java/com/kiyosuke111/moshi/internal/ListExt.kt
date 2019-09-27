package com.kiyosuke111.moshi.internal

import org.json.JSONArray
import org.json.JSONObject

internal fun List<Any?>.toJsonArray(): JSONArray {
    val json = JSONArray()
    this.forEach { value ->
        when (value) {
            is String -> json.put(value)
            is Int -> json.put(value)
            is Long -> json.put(value)
            is Double -> json.put(value)
            is Boolean -> json.put(value)
            is Map<*, Any?> -> json.put((value as Map<String, Any?>).toJson())
            is List<*> -> json.put((value as List<Any>).toJsonArray())
            else -> json.put(JSONObject.NULL)
        }
    }
    return json
}