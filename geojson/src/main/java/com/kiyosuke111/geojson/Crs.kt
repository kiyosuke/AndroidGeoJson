package com.kiyosuke111.geojson


sealed class Crs(val properties: Map<String, Any?>) {
    abstract val type: CrsType
}

class NameCrs(properties: Map<String, Any?>) : Crs(properties) {
    override val type: CrsType get() = CrsType.NAME
    val name: String
        get() {
            val name = properties[KEY_NAME] as? String
            return requireNotNull(name) {
                "Crs内にnameデータがありません"
            }
        }

    companion object {
        private const val KEY_NAME = "name"
    }
}

class LinkCrs(properties: Map<String, Any?>) : Crs(properties) {
    override val type: CrsType get() = CrsType.LINK
    val href: String
        get() {
            val href = properties[KEY_HREF] as? String
            return requireNotNull(href) {
                "Crs内にhrefデータがありません"
            }
        }
    val crsType: String
        get() {
            val type = properties[KEY_TYPE] as? String
            return requireNotNull(type) {
                "Crs内にtypeデータがありません"
            }
        }

    companion object {
        private const val KEY_HREF = "href"
        private const val KEY_TYPE = "type"
    }
}