# AndroidGeoJson
GeoJSONを扱うAndroid用のライブラリです

### JSONObjectからGeoJSONの取得
<pre>
val json = JSONObject(/* GeoJSONの文字列 */)
val line = json.getGeoLineString("lineString")
val point = json.getGeoPoint("point")
val polygon = json.getGeoPolygon("polygon")
</pre>

### GeoJSONをJSONObjectに変換
<pre>
val geoJson = GeoJson.parse("""
    {
        "type": "LineString",
        "coordinates": [ [100.0, 0.0], [101.0, 1.0] ]
    }
""")
val json = geoJson.toJson()
</pre>

### GeoJSON生成
GeoJSONの生成は、各GeoJSONのコンストラクタに値を渡して生成します。
<pre>
val line = GeoLineString(listOf(GeoPosition(10.0, 100.0), GeoPosition(11.0, 110.0), GeoPosition(12.0, 120.0)))
val point = GeoPoint(GeoPosition(10.0, 100.0))
</pre>
DSLを使うことで生成することもできます。
<pre>
val line = geoLine {
    +point(100.0, 10.0)
    +point(110.0, 11.0)
    +point(120.0, 12.0)
}

val multiLine = geoMultiLine {
    +geoLine {
        +point(100.0, 10.0)
        +point(110.0, 11.0)
        +point(120.0, 12.0)
    }
    +geoLine {
        +point(100.0, 10.0)
        +point(110.0, 11.0)
        +point(120.0, 12.0)
    }
}

val point = geoPoint(100.0, 10.0)

val multiPoint = geoMultiPoint {
    +geoPoint(100.0, 10.0)
    +geoPoint(110.0, 11.0)
}

val geoPolygon = geoPolygon {
    outline {
        +point(100.0, 10.0)
        +point(110.0, 11.0)
        +point(120.0, 12.0)
        +point(130.0, 13.0)
    }
    innerline {
        +point(100.0, 10.0)
        +point(110.0, 11.0)
        +point(120.0, 12.0)
        +point(130.0, 13.0)
    }
}

val geoMultiPolygon = geoMultiPolygon {
    +geoPolygon {
        outline {
            +point(100.0, 10.0)
            +point(110.0, 11.0)
            +point(120.0, 12.0)
            +point(130.0, 13.0)
        }
    }
    +geoPolygon {
        outline {
            +point(100.0, 10.0)
            +point(110.0, 11.0)
            +point(120.0, 12.0)
            +point(130.0, 13.0)
        }
    }
}

val feature = geoFeature {
    +geoLine {
        +point(100.0, 10.0)
        +point(110.0, 20.0)
        +point(120.0, 30.0)
    }
    +prop("key", "value")
}

val featureCollection = geoFeatureCollection {
    +geoFeature {
        +geoLine {
            +point(100.0, 10.0)
            +point(110.0, 20.0)
            +point(120.0, 30.0)
        }
        +prop("key", "value")
    }
    +geoFeature {
        +geoLine {
            +point(120.0, 20.0)
            +point(130.0, 30.0)
            +point(140.0, 40.0)
        }
        +prop("id", 1)
    }
}

val geometryCollection = geometryCollection {
    +geoLine {
        +point(100.0, 10.0)
        +point(100.0, 10.0)
        +point(100.0, 10.0)
    }
    +geoPolygon {
        outline {
            +point(100.0, 10.0)
            +point(100.0, 10.0)
            +point(100.0, 10.0)
        }
    }
    +geoLine {
        +point(100.0, 10.0)
        +point(100.0, 10.0)
        +point(100.0, 10.0)
    }
}
</pre>

## 参考
GeoJSON フォーマット仕様
https://s.kitazaki.name/docs/geojson-spec-ja.html#id12
