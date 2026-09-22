package com.example.MedReq.pages

import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.MedReq.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var filter by remember {
        mutableStateOf("All")
    }

    var requests by remember {
        mutableStateOf<List<Request>>(emptyList())
    }

    LaunchedEffect(Unit) {

        requests = authViewModel
            .getRequests()
            .mapNotNull { requestString ->

                val parts =
                    requestString.split(":")

                if (parts.size == 5) {

                    Request(
                        id = parts[0],
                        name = parts[1],
                        location = parts[3],
                        injury = parts[2],
                        time = "Just now",
                        status = "Pending",
                        priority = parts[4]
                    )

                } else {

                    null
                }
            }
    }

    val filteredRequests =
        when (filter) {

            "Critical" ->
                requests.filter {
                    it.priority == "Critical"
                }

            "High" ->
                requests.filter {
                    it.priority == "High"
                }

            else ->
                requests
        }

    Scaffold(

        modifier =
            Modifier.fillMaxSize(),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "MedReq",
                        style =
                            MaterialTheme.typography.headlineLarge
                    )
                }
            )
        },

        bottomBar = {

            BottomAppBar(

                modifier =
                    Modifier.padding(
                        bottom = 75.dp
                    ),

                containerColor =
                    MaterialTheme.colorScheme.surface

            ) {

                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 7.5.dp,
                                vertical = 3.5.dp
                            ),

                    horizontalArrangement =
                        Arrangement.SpaceEvenly,

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {

                    BottomNavItem(
                        "Requests",
                        Icons.Default.CheckCircle,
                        navController,
                        "home"
                    )

                    BottomNavItem(
                        "New",
                        Icons.Default.Add,
                        navController,
                        "new-request"
                    )

                    BottomNavItem(
                        "Profile",
                        Icons.Default.AccountCircle,
                        navController,
                        "profile"
                    )
                }
            }
        }

    ) { innerPadding ->

        Column(

            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 40.dp,
                    bottom = 16.dp
                )
        ) {

            Text(

                text =
                    "Welcome to MedReq",

                fontSize =
                    24.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    MaterialTheme.colorScheme.primary,

                modifier =
                    Modifier.padding(
                        bottom = 8.dp
                    )
            )

            Text(

                text =
                    "Manage and view medical requests efficiently.",

                fontSize =
                    14.sp,

                color =
                    MaterialTheme.colorScheme.onBackground,

                fontWeight =
                    FontWeight.Medium,

                modifier =
                    Modifier.padding(
                        bottom = 16.dp
                    )
            )

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {

                FilterButton(
                    text = "All",
                    currentFilter = filter
                ) {
                    filter = "All"
                }

                FilterButton(
                    text = "Critical",
                    currentFilter = filter
                ) {
                    filter = "Critical"
                }

                FilterButton(
                    text = "High",
                    currentFilter = filter
                ) {
                    filter = "High"
                }
            }

            Spacer(
                modifier =
                    Modifier.padding(8.dp)
            )

            LazyColumn(

                modifier =
                    Modifier.fillMaxSize()

            ) {

                items(
                    items = filteredRequests,
                    key = { request ->
                        request.id
                    }
                ) { request ->

                    RequestCard(
                        request = request,
                        navController = navController
                    )

                    Spacer(
                        modifier =
                            Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun FilterButton(
    text: String,
    currentFilter: String,
    onClick: () -> Unit
) {

    val buttonColor =
        when (text) {

            "All" ->
                MaterialTheme.colorScheme.primary

            "Critical" ->
                MaterialTheme.colorScheme.error

            "High" ->
                MaterialTheme.colorScheme.tertiary

            else ->
                MaterialTheme.colorScheme.primaryContainer
        }

    Button(

        onClick = onClick,

        colors =
            ButtonDefaults.buttonColors(

                containerColor =
                    if (currentFilter == text) {

                        buttonColor

                    } else {

                        buttonColor.copy(
                            alpha = 0.7f
                        )
                    },

                contentColor =
                    MaterialTheme.colorScheme.onPrimary
            ),

        modifier =
            Modifier.padding(4.dp)

    ) {

        Text(

            text = text,

            style =
                MaterialTheme.typography.bodyMedium,

            fontWeight =
                FontWeight.SemiBold
        )
    }
}


@Composable
fun RequestCard(
    request: Request,
    navController: NavController
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(
                color =
                    MaterialTheme.colorScheme.surface,
                shape =
                    MaterialTheme.shapes.medium
            )
            .padding(16.dp)
    ) {

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Text(

                text =
                    request.name,

                style =
                    MaterialTheme.typography.titleMedium,

                color =
                    MaterialTheme.colorScheme.onSurface,

                fontWeight =
                    FontWeight.Medium
            )

            Text(

                text =
                    request.priority,

                style =
                    MaterialTheme.typography.bodySmall,

                color =
                    when (request.priority) {

                        "Critical" ->
                            MaterialTheme.colorScheme.error

                        "High" ->
                            MaterialTheme.colorScheme.tertiary

                        else ->
                            MaterialTheme.colorScheme.onSurface
                    },

                modifier =
                    Modifier
                        .background(

                            color =
                                when (request.priority) {

                                    "Critical" ->
                                        MaterialTheme.colorScheme
                                            .error
                                            .copy(alpha = 0.12f)

                                    "High" ->
                                        MaterialTheme.colorScheme
                                            .tertiary
                                            .copy(alpha = 0.15f)

                                    else ->
                                        MaterialTheme.colorScheme
                                            .primaryContainer
                                },

                            shape =
                                MaterialTheme.shapes.small
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
            )
        }

        Row(

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Icon(

                imageVector =
                    Icons.Default.LocationOn,

                contentDescription =
                    "Location",

                tint =
                    MaterialTheme.colorScheme.primary
            )

            Text(

                text =
                    request.location,

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurface,

                modifier =
                    Modifier.padding(
                        start = 4.dp
                    )
            )
        }

        Row(

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Icon(

                imageVector =
                    Icons.Default.Warning,

                contentDescription =
                    "Injury",

                tint =
                    MaterialTheme.colorScheme.tertiary
            )

            Text(

                text =
                    request.injury,

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurface,

                modifier =
                    Modifier.padding(
                        start = 4.dp
                    )
            )
        }

        Row(

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Text(

                text =
                    request.time,

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurface
            )
        }

        Row(

            verticalAlignment =
                Alignment.CenterVertically

        ) {

            Icon(

                imageVector =
                    Icons.Default.CheckCircle,

                contentDescription =
                    "Status",

                tint =
                    MaterialTheme.colorScheme.primary
            )

            Text(

                text =
                    request.status,

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onSurface,

                modifier =
                    Modifier.padding(
                        start = 4.dp
                    )
            )
        }

        Button(

            onClick = {

                val id =
                    Uri.encode(request.id)

                val name =
                    Uri.encode(request.name)

                val injury =
                    Uri.encode(request.injury)

                val location =
                    Uri.encode(request.location)

                val priority =
                    Uri.encode(request.priority)

                navController.navigate(

                    "request-details" +
                            "?id=$id" +
                            "&name=$name" +
                            "&injury=$injury" +
                            "&location=$location" +
                            "&priority=$priority"

                ) {

                    launchSingleTop = true
                }
            },

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        MaterialTheme.colorScheme.primary
                ),

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)

        ) {

            Text(

                text =
                    "View Details",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


data class Request(

    val id: String,

    val name: String,

    val location: String,

    val injury: String,

    val time: String,

    val status: String,

    val priority: String
)