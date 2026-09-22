package com.example.MedReq.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun RequestDetailsPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var requestId by remember {
        mutableStateOf("")
    }

    var requestName by remember {
        mutableStateOf("")
    }

    var requestInjury by remember {
        mutableStateOf("")
    }

    var requestLocation by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        val arguments =
            navController
                .currentBackStackEntry
                ?.arguments

        requestId =
            arguments?.getString("id") ?: ""

        requestName =
            arguments?.getString("name") ?: ""

        requestInjury =
            arguments?.getString("injury") ?: ""

        requestLocation =
            arguments?.getString("location") ?: ""
    }

    Scaffold(

        modifier = Modifier.fillMaxSize(),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Request Details",
                        style =
                            MaterialTheme.typography.titleLarge
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "Back"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->

        Column(

            modifier = modifier
                .fillMaxSize()

                // IMPORTANT:
                // This prevents content from going
                // underneath the TopAppBar.
                .padding(innerPadding)

                .background(
                    MaterialTheme.colorScheme.background
                )

                .padding(20.dp),

            verticalArrangement =
                Arrangement.Top,

            horizontalAlignment =
                Alignment.Start
        ) {

            Text(

                text = "Medical Request",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold,

                color =
                    MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(

                text = "Patient Name",

                style =
                    MaterialTheme.typography.labelLarge,

                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text =
                    if (requestName.isNotEmpty())
                        requestName
                    else
                        "Not available",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(

                text = "Location",

                style =
                    MaterialTheme.typography.labelLarge,

                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            RowWithIcon(
                iconContentDescription = "Location",
                text =
                    if (requestLocation.isNotEmpty())
                        requestLocation
                    else
                        "Not available"
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(

                text = "Injury / Medical Issue",

                style =
                    MaterialTheme.typography.labelLarge,

                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            RowWithWarningIcon(
                text =
                    if (requestInjury.isNotEmpty())
                        requestInjury
                    else
                        "Not available"
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(

                text = "Priority",

                style =
                    MaterialTheme.typography.labelLarge,

                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(

                text =
                    if (requestId.isNotEmpty())
                        requestId
                    else
                        "Not available",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.Bold,

                color = when (requestId) {

                    "Critical" ->
                        MaterialTheme.colorScheme.error

                    "High" ->
                        MaterialTheme.colorScheme.tertiary

                    else ->
                        MaterialTheme.colorScheme.primary
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(

                text = "Status",

                style =
                    MaterialTheme.typography.labelLarge,

                color =
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            RowWithStatusIcon()

            Spacer(
                modifier = Modifier.height(32.dp)
            )


            Button(

                onClick = {
                    navController.popBackStack()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor =
                        MaterialTheme.colorScheme.primary,

                    contentColor =
                        MaterialTheme.colorScheme.onPrimary
                )
            ) {

                Text(
                    text = "Back to Requests",

                    fontSize = 16.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
private fun RowWithIcon(
    iconContentDescription: String,
    text: String
) {

    androidx.compose.foundation.layout.Row(

        verticalAlignment =
            Alignment.CenterVertically

    ) {

        Icon(

            imageVector =
                Icons.Default.LocationOn,

            contentDescription =
                iconContentDescription,

            tint =
                MaterialTheme.colorScheme.primary
        )

        Text(

            text = text,

            style =
                MaterialTheme.typography.bodyLarge,

            color =
                MaterialTheme.colorScheme.onBackground,

            modifier =
                Modifier.padding(start = 8.dp)
        )
    }
}


@Composable
private fun RowWithWarningIcon(
    text: String
) {

    androidx.compose.foundation.layout.Row(

        verticalAlignment =
            Alignment.CenterVertically

    ) {

        Icon(

            imageVector =
                Icons.Default.Warning,

            contentDescription =
                "Medical issue",

            tint =
                MaterialTheme.colorScheme.tertiary
        )

        Text(

            text = text,

            style =
                MaterialTheme.typography.bodyLarge,

            color =
                MaterialTheme.colorScheme.onBackground,

            modifier =
                Modifier.padding(start = 8.dp)
        )
    }
}


@Composable
private fun RowWithStatusIcon() {

    androidx.compose.foundation.layout.Row(

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

            text = "Pending",

            style =
                MaterialTheme.typography.bodyLarge,

            fontWeight =
                FontWeight.Medium,

            color =
                MaterialTheme.colorScheme.onBackground,

            modifier =
                Modifier.padding(start = 8.dp)
        )
    }
}