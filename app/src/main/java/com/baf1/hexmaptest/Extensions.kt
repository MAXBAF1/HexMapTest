package com.baf1.hexmaptest

import com.uber.h3core.util.LatLng
import io.github.dellisd.spatialk.geojson.BoundingBox
import io.github.dellisd.spatialk.geojson.Position

fun LatLng.toPosition(): Position = Position(lng, lat)

fun BoundingBox.toUberLatLngList(): List<LatLng> {
    return listOf(
        // Юго-западный угол
        LatLng(southwest.latitude, southwest.longitude),
        // Северо-западный угол
        LatLng(northeast.latitude, southwest.longitude),
        // Северо-восточный угол
        LatLng(northeast.latitude, northeast.longitude),
        // Юго-восточный угол
        LatLng(southwest.latitude, northeast.longitude),
        // Замыкаем полигон, возвращаясь к юго-западному углу
        LatLng(southwest.latitude, southwest.longitude)
    )
}