package com.kiyosuke111.geojson

enum class GeoType(val key: String) {
    /**
     * { "type": "Point", "coordinates": [100.0, 0.0] }
     */
    POINT("Point"),
    /**
     * {
     *   "type": "LineString",
     *   "coordinates": [ [100.0, 0.0], [101.0, 1.0] ]
     * }
     */
    LINE_STRING("LineString"),
    /**
     * {
     *   "type": "Polygon",
     *   "coordinates": [
     *     [[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]
     *   ]
     * }
     */
    POLYGON("Polygon"),
    /**
     * { "type": "MultiPoint","coordinates": [ [100.0, 0.0], [101.0, 1.0] ]}
     */
    MULTI_POINT("MultiPoint"),
    /**
     * {
     *   "type": "MultiLineString",
     *   "coordinates": [
     *     [[100.0, 0.0], [101.0, 1.0]],
     *     [[102.0, 2.0], [103.0, 3.0]]
     *   ]
     * }
     */
    MULTI_LINE_STRING("MultiLineString"),
    /**
     * {
     *   "type": "MultiPolygon",
     *   "coordinates": [
     *   [[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],
     *   [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],
     *   [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]
     *   ]
     * }
     */
    MULTI_POLYGON("MultiPolygon"),
    /**
     * {
     *   "type": "GeometryCollection",
     *   "geometries": [
     *     {
     *       "type": "Point",
     *       "coordinates": [100.0, 0.0]
     *     },
     *     {
     *       "type": "LineString",
     *       "coordinates": [ [101.0, 0.0], [102.0, 1.0] ]
     *     }
     *   ]
     * }
     */
    GEOMETRY_COLLECTION("GeometryCollection"),
    /**
     * {
     *   "type": "Feature",
     *   "geometry": {
     *     "type": "LineString",
     *     "coordinates": [
     *       [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]
     *     ]
     *   },
     *   "properties": {
     *     "prop0": "value0",
     *     "prop1": 0.0
     *   }
     * }
     */
    FEATURE("Feature"),
    /**
     * {
     *   "type": "FeatureCollection",
     *   "features": [
     *     {
     *       "type": "Feature",
     *       "geometry": {
     *         "type": "Point",
     *         "coordinates": [102.0, 0.5]
     *       },
     *       "properties": {"prop0": "value0"}
     *     },
     *     {
     *       "type": "Feature",
     *       "geometry": {
     *         "type": "LineString",
     *         "coordinates": [
     *           [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]
     *         ]
     *       },
     *       "properties": {
     *         "prop0": "value0",
     *         "prop1": 0.0
     *       }
     *     },{
     *       "type": "Feature",
     *       "geometry": {
     *         "type": "Polygon",
     *         "coordinates": [
     *           [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0],
     *           [100.0, 1.0], [100.0, 0.0] ]
     *         ]
     *       },
     *       "properties": {
     *         "prop0": "value0",
     *         "prop1": {"this": "that"}
     *       }
     *     }
     *   ]
     * }
     */
    FEATURE_COLLECTION("FeatureCollection")
    ;

    companion object {
        fun of(type: String): GeoType {
            return when (type) {
                "Point" -> POINT
                "LineString" -> LINE_STRING
                "Polygon" -> POLYGON
                "MultiPoint" -> MULTI_POINT
                "MultiLineString" -> MULTI_LINE_STRING
                "MultiPolygon" -> MULTI_POLYGON
                "GeometryCollection" -> GEOMETRY_COLLECTION
                "Feature" -> FEATURE
                "FeatureCollection" -> FEATURE_COLLECTION
                else -> throw IllegalArgumentException("type=${type} はGeoJsonのフォーマットに存在しません。")
            }
        }
    }
}