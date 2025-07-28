package com.baf1.hexmaptest.cells

import com.uber.h3core.util.LatLng
import io.github.dellisd.spatialk.geojson.BoundingBox

object IsCross {
    fun BoundingBox.isCross(points: List<LatLng>): Boolean {
        // 1) Границы бокса
        val minLat = southwest.latitude
        val maxLat = northeast.latitude
        val minLng = southwest.longitude
        val maxLng = northeast.longitude

        // Функция проверки точки внутри бокса
        fun LatLng.isInsideBox() =
            this.lat in minLat..maxLat && this.lng in minLng..maxLng

        // Если хотя бы одна вершина шестиугольника внутри бокса
        if (points.any { it.isInsideBox() }) return true

        // 2) Проверка, что вершины бокса внутри шестиугольника
        val boxCorners = listOf(
            LatLng(minLat, minLng),
            LatLng(minLat, maxLng),
            LatLng(maxLat, maxLng),
            LatLng(maxLat, minLng)
        )
        if (boxCorners.any { pointInPolygon(it, points) }) return true

        // 3) Пересечение рёбер шестиугольника с рёбрами бокса
        // Рёбра бокса как пары точек
        val boxEdges = boxCorners.zipWithNext() + listOf(boxCorners.last() to boxCorners.first())
        // Рёбра шестиугольника
        val hexEdges = points.zipWithNext() + listOf(points.last() to points.first())

        for ((a, b) in hexEdges) {
            for ((c, d) in boxEdges) {
                if (segmentsIntersect(a, b, c, d)) return true
            }
        }

        // Если ни один из случаев не сработал — пересечений нет
        return false
    }

    // Тест «точка в полигоне» (ray-casting)
    private fun pointInPolygon(pt: LatLng, poly: List<LatLng>): Boolean {
        var inside = false
        val n = poly.size
        for (i in 0 until n) {
            val a = poly[i]
            val b = poly[(i + 1) % n]
            // проверяем, пересекает ли горизонталь из pt ребро a–b
            if ( ((a.lat > pt.lat) != (b.lat > pt.lat)) &&
                (pt.lng < (b.lng - a.lng) * (pt.lat - a.lat) / (b.lat - a.lat) + a.lng) ) {
                inside = !inside
            }
        }
        return inside
    }

    // Проверка пересечения отрезков a–b и c–d через ориентацию
    private fun segmentsIntersect(a: LatLng, b: LatLng, c: LatLng, d: LatLng): Boolean {
        fun orient(p: LatLng, q: LatLng, r: LatLng): Double =
            (q.lng - p.lng) * (r.lat - p.lat) - (q.lat - p.lat) * (r.lng - p.lng)

        fun onSegment(p: LatLng, q: LatLng, r: LatLng): Boolean =
            q.lng in minOf(p.lng, r.lng)..maxOf(p.lng, r.lng) &&
                    q.lat in minOf(p.lat, r.lat)..maxOf(p.lat, r.lat)

        val o1 = orient(a, b, c)
        val o2 = orient(a, b, d)
        val o3 = orient(c, d, a)
        val o4 = orient(c, d, b)

        // Общий случай
        if (o1 * o2 < 0 && o3 * o4 < 0) return true

        // Коллинеарные случаи
        if (o1 == 0.0 && onSegment(a, c, b)) return true
        if (o2 == 0.0 && onSegment(a, d, b)) return true
        if (o3 == 0.0 && onSegment(c, a, d)) return true
        if (o4 == 0.0 && onSegment(c, b, d)) return true

        return false
    }

}