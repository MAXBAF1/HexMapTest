package com.baf1.hexmaptest

import io.github.dellisd.spatialk.geojson.Position

fun com.uber.h3core.util.LatLng.toPosition(): Position = Position(lng, lat)
