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
        setContent {
            HexMapTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyMap(
                        cellFieldProvider = cellFieldProvider,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
