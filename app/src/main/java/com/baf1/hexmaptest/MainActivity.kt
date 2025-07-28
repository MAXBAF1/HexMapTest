package com.baf1.hexmaptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.baf1.hexmaptest.cells.CellFieldProvider
import com.baf1.hexmaptest.ui.theme.HexMapTestTheme
import com.uber.h3core.H3Core

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val h3 = H3Core.newSystemInstance()
        val cellFieldProvider = CellFieldProvider(h3)
        cellFieldProvider.addCells(
            listOf(
                CellDTO(id = "8610c221fffffff"),
                CellDTO(id = "8610c22f7ffffff"),
                CellDTO(id = "8610c22afffffff"),
                CellDTO(id = "8610c22d7ffffff"),
                CellDTO(id = "8610c228fffffff"),
                CellDTO(id = "8610c2287ffffff"),
                CellDTO(id = "8610c22b7ffffff"),
                CellDTO(id = "8610dc92fffffff"),
                CellDTO(id = "8610dc927ffffff"),
                CellDTO(id = "8610c229fffffff"),
                CellDTO(id = "8610c2297ffffff"),
                CellDTO(id = "8610dc907ffffff"),
                CellDTO(id = "8610dc937ffffff"),
                CellDTO(id = "8610c266fffffff"),
                CellDTO(id = "8610dc917ffffff"),
                CellDTO(id = "8610c264fffffff"),
            )
        )
        setContent {
            HexMapTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AroundMap(
                        cellFieldProvider = cellFieldProvider,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
