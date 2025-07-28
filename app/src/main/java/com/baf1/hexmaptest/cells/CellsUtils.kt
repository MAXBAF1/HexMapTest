package com.baf1.hexmaptest.cells

import com.uber.h3core.util.LatLng

object CellsUtils {

    fun shrinkHexVertices(
        vertices: List<LatLng>,
        resolution: Int
    ): List<LatLng> {
        val factor = when (resolution) {
            6 -> 0.97
            7 -> 0.95
            8 -> 0.93
            9 -> 0.91
            10 -> 0.88
            11 -> 0.85
            else -> 1.0
        }
        val center = findCenter(vertices)
        val newVertices = vertices.map { vertex ->
            LatLng(
                center.lat + (vertex.lat - center.lat) * factor,
                center.lng + (vertex.lng - center.lng) * factor
            )
        }
        return newVertices
    }

    private fun findCenter(vertices: List<LatLng>): LatLng {
        val avgX = vertices.sumOf { it.lat } / vertices.size
        val avgY = vertices.sumOf { it.lng } / vertices.size
        return LatLng(avgX, avgY)
    }
}
