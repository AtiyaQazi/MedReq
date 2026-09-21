package com.example.fieldmedicapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.fieldmedicapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var filter by remember { mutableStateOf("All") }
    var requests by remember { mutableStateOf<List<Request>>(emptyList()) }
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            requests = authViewModel.getRequests().mapNotNull { requestString ->
                val parts = requestString.split(":")
                if (parts.size == 5) {
                    Request(parts[1], parts[3], parts[2], "Just now", "Pending", parts[4])
                } else null
            }
        }
    }

    val filteredRequests = when (filter) {
        "Critical" -> requests.filter { it.priority == "Critical" }
        "High" -> requests.filter { it.priority == "High" }
        else -> requests
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Field Medic App", style = MaterialTheme.typography.headlineLarge) }
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
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome to Field Medic App",
                fontSize = 24.sp, // Slightly larger for emphasis
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50), // Bold green for contrast
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Manage and view medical requests efficiently.",
                fontSize = 14.sp,
                color = Color(0xFFFFD700), // Gold for contrast
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterButton("All", filter) { filter = "All" }
                FilterButton("Critical", filter) { filter = "Critical" }
                FilterButton("High", filter) { filter = "High" }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(filteredRequests) { request ->
                    RequestCard(request, navController)
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, currentFilter: String, onClick: () -> Unit) {
    val buttonColor = when (text) {
        "All" -> Color(0xFF4CAF50)
        "Critical" -> Color.Red
        "High" -> Color(0xFFFF9800)
        else -> MaterialTheme.colorScheme.primaryContainer
    }
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (currentFilter == text) buttonColor else buttonColor.copy(alpha = 0.7f),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun RequestCard(request: Request, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2F4F2F).copy(alpha = 0.9f), shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = request.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = request.priority,
                style = MaterialTheme.typography.bodySmall,
                color = when (request.priority) {
                    "Critical" -> Color.Red
                    "High" -> Color(0xFFFF9800)
                    else -> MaterialTheme.colorScheme.onBackground
                },
                modifier = Modifier
                    .background(
                        when (request.priority) {
                            "Critical" -> Color.Red.copy(alpha = 0.3f)
                            "High" -> Color(0xFFFF9800).copy(alpha = 0.3f)
                            else -> MaterialTheme.colorScheme.primaryContainer
                        },
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.onBackground)
            Text(text = request.location, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, contentDescription = "Injury", tint = MaterialTheme.colorScheme.onBackground)
            Text(text = request.injury, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = request.time, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Status", tint = Color(0xFF4CAF50))
            Text(text = request.status, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
        }
        Button(
            onClick = { navController.navigate("request-details?id=${request.priority}&name=${request.name}&injury=${request.injury}&location=${request.location}") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.8f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("View Details", style = MaterialTheme.typography.bodyMedium, color = Color.White)
        }
    }
}

data class Request(
    val name: String,
    val location: String,
    val injury: String,
    val time: String,
    val status: String,
    val priority: String
)