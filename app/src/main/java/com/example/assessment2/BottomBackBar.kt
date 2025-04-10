// BottomBackBar.kt
package com.example.assessment2.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBackBar(navController: NavController) {
    BottomAppBar {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back",style = MaterialTheme.typography.bodyLarge)
        }
    }
}
