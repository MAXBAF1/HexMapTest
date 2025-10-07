package com.baf1.hexmaptest.cells

import android.util.Log
import com.baf1.hexmaptest.toPosition
import com.baf1.hexmaptest.toUberLatLngList
import com.uber.h3core.H3Core
import com.uber.h3core.PolygonToCellsFlags
import com.uber.h3core.exceptions.H3Exception
import io.github.dellisd.spatialk.geojson.BoundingBox
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.Polygon

class CellFieldProvider(private val h3: H3Core) {

    fun getFeaturesForBounds(bounds: BoundingBox, zoom: Int): FeatureCollection {
        if (zoom < 2) return FeatureCollection(emptyList())

        val resolution = when (zoom) {
            in 0..3 -> 2
            4 -> 3
            5 -> 4
            in 6..8 -> 5
            9 -> 6
            10 -> 7
            11 -> 8
            12 -> 9
            13 -> 10
            else -> 11
        }

        return try {
            val cells = h3.polygonToCellAddressesExperimental(
                bounds.toUberLatLngList(),
                emptyList(),
                resolution,
                PolygonToCellsFlags.containment_overlapping
            )

            val features = cells.map { id ->
                val latLngs = h3.cellToBoundary(id)
                Feature(Polygon(listOf(latLngs.map { it.toPosition() } + latLngs
                    .first()
                    .toPosition())))
            }

            FeatureCollection(features)
        } catch (e: H3Exception) {
            Log.e("MyLog", e.toString())
            FeatureCollection(emptyList())
        }
    }
}
