package com.example.fieldmedicapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fieldmedicapp.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailsPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var injury by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val args = navController.currentBackStackEntry?.arguments
        id = args?.getString("id") ?: ""
        name = args?.getString("name") ?: ""
        injury = args?.getString("injury") ?: ""
        location = args?.getString("location") ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request Details", style = MaterialTheme.typography.headlineLarge) }
            )
        },
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Request ID: $id",
                    fontSize = 18.sp, // Slightly larger text
                    fontWeight = FontWeight.Bold,
                    color = when (id) {
                        "Critical" -> Color.Red
                        "High" -> Color(0xFFFF9800)
                        else -> Color.Black
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Name: $name",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Injury: $injury",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Location: $location",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Back to Home", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    )
}