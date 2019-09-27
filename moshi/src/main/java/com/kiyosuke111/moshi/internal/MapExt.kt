package com.kiyosuke111.moshi.internal

import org.json.JSONObject

internal fun Map<String, Any?>.toJson(): JSONObject {
    val json = JSONObject()
    this.forEach { (key, value) ->
        when (value) {
            is String -> json.put(key, value)
            is Int -> json.put(key, value)
            is Long -> json.put(key, value)
            is Double -> json.put(key, value)
            is Boolean -> json.put(key, value)
            is Map<*, Any?> -> json.put(key, (value as Map<String, Any?>).toJson())
            is List<*> -> json.put(key, (value as List<Any?>).toJsonArray())
            else -> json.put(key, JSONObject.NULL)
        }
    }
    return json
}