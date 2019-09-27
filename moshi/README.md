# moshi adapter

## Usage

```kt
// moshi setup
val moshi = Moshi.Builder()
    // add GeoJsonAdapters.FACTORY
    .add(GeoJsonAdapters.FACTORY)
    .build()

// parse
val place = moshi.adapter(Place::class.java).fromJson(sample)

// Sample data object
data class Place(
    val id: Int,
    val name: String,
    val address: String,
    @Json(name = "postal_code") val postalCode: String,
    @Json(name = "geom_point") val geomPoint: GeoFeature
)


// Sample Json object
val sample = """
{
  "id": 1,
  "name": "SkyTREE",
  "address": "東京都墨田区押上１丁目１−２",
  "postal_code": "131-0045",
  "geom_point": {
    "type": "Feature",
    "geometry": {
      "type": "Point",
      "coordinates": [
        139.8087662,
        35.7102098
      ]
    },
    "properties": null
  }
}
""".trimIndent()
```
