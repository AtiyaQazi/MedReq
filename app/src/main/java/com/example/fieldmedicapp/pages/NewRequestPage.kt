package com.example.fieldmedicapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.runtime.LaunchedEffect
import com.example.fieldmedicapp.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRequestPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var injury by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("High") }
    var expanded by remember { mutableStateOf(false) }
    val authState by authViewModel.authState.observeAsState()
    var requestState by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        requestState = null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Request", style = MaterialTheme.typography.headlineLarge) }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomNavItem("Requests", Icons.Default.CheckCircle, navController, "home")
                    BottomNavItem("New", Icons.Default.Add, navController, "new-request")
                    BottomNavItem("Profile", Icons.Default.AccountCircle, navController, "profile")
                }
            }
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
                    text = "Emergency Medical Request",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B0000), // Deep red for contrast
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Fill all fields for prompt assistance.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700), // Gold for contrast
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location", style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = injury,
                    onValueChange = { injury = it },
                    label = { Text("Injury", style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = priority,
                        onValueChange = {},
                        label = { Text("Priority", style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("High") },
                            onClick = {
                                priority = "High"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Critical") },
                            onClick = {
                                priority = "Critical"
                                expanded = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = {
                        if (name.isNotEmpty() && location.isNotEmpty() && injury.isNotEmpty()) {
                            authViewModel.submitRequest(name, injury, location, priority)
                            requestState = "Request submitted successfully"
                            navController.navigate("home") {
                                popUpTo("new-request") { inclusive = true }
                            }
                        } else {
                            requestState = "Please fill all required fields"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4500)), // Orange-red for contrast
                    enabled = name.isNotEmpty() && location.isNotEmpty() && injury.isNotEmpty()
                ) {
                    Text(text = "Submit Request", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.padding(8.dp))
                requestState?.let {
                    Text(
                        text = it,
                        color = if (it.contains("Please")) MaterialTheme.colorScheme.error else Color(0xFF4CAF50),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    )
}