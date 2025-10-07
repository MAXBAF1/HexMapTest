package com.baf1.hexmaptest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrecompose.compose.CameraState
import dev.sargunv.maplibrecompose.compose.MaplibreMap
import dev.sargunv.maplibrecompose.compose.layer.LineLayer
import dev.sargunv.maplibrecompose.compose.rememberCameraState
import dev.sargunv.maplibrecompose.compose.rememberStyleState
import dev.sargunv.maplibrecompose.compose.source.rememberGeoJsonSource
import dev.sargunv.maplibrecompose.core.BaseStyle
import dev.sargunv.maplibrecompose.core.MapOptions
import dev.sargunv.maplibrecompose.core.OrnamentOptions
import dev.sargunv.maplibrecompose.core.source.ComputedSourceOptions
import dev.sargunv.maplibrecompose.expressions.dsl.const
import io.github.dellisd.spatialk.geojson.Position
import com.baf1.hexmaptest.cells.CellFieldProvider

@Composable
fun MyMap(
    cellFieldProvider: CellFieldProvider,
    modifier: Modifier = Modifier,
    cameraState: CameraState = rememberCameraState(),
) {
    val styleState = rememberStyleState()
    MaplibreMap(
        cameraState = cameraState,
        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
        styleState = styleState,
        options = MapOptions(ornamentOptions = OrnamentOptions.AllDisabled),
        modifier = modifier,
    ) {
        CellsOverlay(cellFieldProvider = cellFieldProvider)
    }

    LaunchedEffect(Unit) {
        cameraState.animateTo(
            finalPosition = cameraState.position.copy(
                target = Position(latitude = 56.8519, longitude = 60.6122),
                zoom = 12.1,
            ),
        )
    }
}


@Composable
private fun CellsOverlay(cellFieldProvider: CellFieldProvider) {
    val source = rememberGeoJsonSource(
        options = ComputedSourceOptions(),
        getFeatures = cellFieldProvider::getFeaturesForBounds,
    )

    LineLayer(
        id = "ID_LINE_LAYER", source = source, width = const(0.4.dp)
    )
}
