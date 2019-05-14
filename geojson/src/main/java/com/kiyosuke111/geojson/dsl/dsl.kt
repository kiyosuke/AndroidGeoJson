@file:JvmName("GeoJsonDslKt")

package com.kiyosuke111.geojson.dsl

import com.kiyosuke111.geojson.*
import com.kiyosuke111.geojson.dsl.GeoJsonDsl
import kotlin.properties.Delegates

@GeoJsonDsl
class GeoLineBuilder {
    private val points: MutableList<GeoPosition> = mutableListOf()

    operator fun GeoPosition.unaryPlus() {
        points.add(this)
    }

    fun point(latitude: Double, longitude: Double, altitude: Double = 0.0): GeoPosition =
        GeoPosition(latitude, longitude, altitude)

    fun build(): GeoLineString = GeoLineString(points)
}

@GeoJsonDsl
class GeoMultiLineBuilder {
    private val lines: MutableList<GeoLineString> = mutableListOf()

    operator fun GeoLineString.unaryPlus() {
        lines.add(this)
    }

    fun build(): GeoMultiLineString = GeoMultiLineString(lines)
}

@GeoJsonDsl
class GeoMultiPointBuilder {
    private val points: MutableList<GeoPoint> = mutableListOf()

    operator fun GeoPoint.unaryPlus() {
        points.add(this)
    }

    fun build(): GeoMultiPoint = GeoMultiPoint(points)
}

@GeoJsonDsl
class GeoPolygonBuilder {
    private val outline: MutableList<GeoPosition> = mutableListOf()
    private val innerLine: MutableList<GeoPosition> = mutableListOf()

    fun outline(build: PolygonBuilder.() -> Unit) {
        this.outline += PolygonBuilder().apply(build).polygons
    }

    fun innerline(build: PolygonBuilder.() -> Unit) {
        this.innerLine += PolygonBuilder().apply(build).polygons
    }

    @GeoJsonDsl
    class PolygonBuilder {
        private val _polygons: MutableList<GeoPosition> = mutableListOf()
        val polygons: List<GeoPosition> = _polygons
        operator fun GeoPosition.unaryPlus() {
            _polygons.add(this)
        }

        fun point(latitude: Double, longitude: Double): GeoPosition = GeoPosition(latitude, longitude)
    }

    fun build(): GeoPolygon = GeoPolygon(listOf(outline, innerLine))
}

@GeoJsonDsl
class GeoMultiPolygonBuilder {
    private val polygons: MutableList<GeoPolygon> = mutableListOf()
    operator fun GeoPolygon.unaryPlus() {
        polygons.add(this)
    }

    fun build(): GeoMultiPolygon = GeoMultiPolygon(polygons)
}

private typealias Prop = Pair<String, Any?>

@GeoJsonDsl
class GeoFeatureBuilder {
    private var geoJson: GeoJSONObject by Delegates.notNull()
    private val properties: MutableList<Prop> = mutableListOf()

    operator fun GeoJSONObject.unaryPlus() {
        geoJson = this
    }

    operator fun Prop.unaryPlus() {
        properties.add(this)
    }

    fun prop(key: String, value: Any?): Pair<String, Any?> = key to value

    fun build(): GeoFeature {
        return GeoFeature(geoJson).also { feature ->
            properties.forEach { prop ->
                feature.setProperty(prop.first, prop.second)
            }
        }
    }
}

@GeoJsonDsl
class GeoFeatureCollectionBuilder {
    private val features: MutableList<GeoFeature> = mutableListOf()

    operator fun GeoFeature.unaryPlus() {
        features.add(this)
    }

    fun build(): GeoFeatureCollection {
        return GeoFeatureCollection(features)
    }
}

@GeoJsonDsl
class GeoCollectionBuilder {
    private val geometries: MutableList<GeoJSONObject> = mutableListOf()

    operator fun GeoJSONObject.unaryPlus() {
        geometries.add(this)
    }

    fun build(): GeometryCollection = GeometryCollection(geometries)
}

fun geoLine(block: GeoLineBuilder.() -> Unit): GeoLineString {
    return GeoLineBuilder().apply(block).build()
}

fun geoMultiLine(block: GeoMultiLineBuilder.() -> Unit): GeoMultiLineString {
    return GeoMultiLineBuilder().apply(block).build()
}

fun geoPoint(latitude: Double, longitude: Double): GeoPoint =
    GeoPoint(GeoPosition(latitude, longitude))

fun geoMultiPoint(block: GeoMultiPointBuilder.() -> Unit): GeoMultiPoint {
    return GeoMultiPointBuilder().apply(block).build()
}

fun geoPolygon(block: GeoPolygonBuilder.() -> Unit): GeoPolygon {
    return GeoPolygonBuilder().apply(block).build()
}

fun geoMultiPolygon(block: GeoMultiPolygonBuilder.() -> Unit): GeoMultiPolygon {
    return GeoMultiPolygonBuilder().apply(block).build()
}

fun geoFeature(block: GeoFeatureBuilder.() -> Unit): GeoFeature {
    return GeoFeatureBuilder().apply(block).build()
}

fun geoFeatureCollection(block: GeoFeatureCollectionBuilder.() -> Unit): GeoFeatureCollection {
    return GeoFeatureCollectionBuilder().apply(block).build()
}

fun geometryCollection(block: GeoCollectionBuilder.() -> Unit): GeometryCollection {
    return GeoCollectionBuilder().also(block).build()
}