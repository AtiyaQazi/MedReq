package com.example.MedReq.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

    var requestId by remember { mutableStateOf("") }
    var requestName by remember { mutableStateOf("") }
    var requestInjury by remember { mutableStateOf("") }
    var requestLocation by remember { mutableStateOf("") }
    var requestPriority by remember { mutableStateOf("High") }

    var isEditing by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        val arguments =
            navController.currentBackStackEntry?.arguments

        requestId =
            arguments?.getString("id") ?: ""

        requestName =
            arguments?.getString("name") ?: ""

        requestInjury =
            arguments?.getString("injury") ?: ""

        requestLocation =
            arguments?.getString("location") ?: ""

        requestPriority =
            arguments?.getString("priority") ?: "High"
    }

    Scaffold(

        modifier = Modifier.fillMaxSize(),

        topBar = {

            TopAppBar(

                title = {
                    Text("Request Details")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->

        Column(

            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    MaterialTheme.colorScheme.background
                )
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Text(
                text =
                    if (isEditing)
                        "Edit Medical Request"
                    else
                        "Medical Request",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold,

                color =
                    MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            if (isEditing) {

                OutlinedTextField(
                    value = requestName,
                    onValueChange = {
                        requestName = it
                    },
                    label = {
                        Text("Patient Name")
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = requestLocation,
                    onValueChange = {
                        requestLocation = it
                    },
                    label = {
                        Text("Location")
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = requestInjury,
                    onValueChange = {
                        requestInjury = it
                    },
                    label = {
                        Text("Injury / Medical Issue")
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = "Priority",
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    Button(
                        onClick = {
                            requestPriority = "High"
                        },
                        modifier =
                            Modifier.weight(1f)
                    ) {
                        Text("High")
                    }

                    Button(
                        onClick = {
                            requestPriority = "Critical"
                        },
                        modifier =
                            Modifier.weight(1f),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    MaterialTheme.colorScheme.error
                            )
                    ) {
                        Text("Critical")
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(
                    onClick = {

                        authViewModel.updateRequest(
                            requestId,
                            requestName,
                            requestInjury,
                            requestLocation,
                            requestPriority
                        )

                        isEditing = false
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {

                    Text(
                        text = "Save Changes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                TextButton(
                    onClick = {
                        isEditing = false
                    },
                    modifier =
                        Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }

            } else {

                DetailLabel("Patient Name")

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
                        FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                DetailLabel("Location")

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                RowWithIcon(
                    text =
                        if (requestLocation.isNotEmpty())
                            requestLocation
                        else
                            "Not available"
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                DetailLabel(
                    "Injury / Medical Issue"
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

                DetailLabel("Priority")

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = requestPriority,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold,

                    color =
                        if (requestPriority == "Critical")
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.tertiary
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                DetailLabel("Status")

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                RowWithStatusIcon()

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    Button(

                        onClick = {
                            isEditing = true
                        },

                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )

                        Text(
                            text = "Edit",
                            modifier =
                                Modifier.padding(start = 6.dp)
                        )
                    }

                    Button(

                        onClick = {
                            showDeleteDialog = true
                        },

                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    MaterialTheme.colorScheme.error
                            )
                    ) {

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )

                        Text(
                            text = "Delete",
                            modifier =
                                Modifier.padding(start = 6.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(

                    onClick = {
                        navController.popBackStack()
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                ) {

                    Text("Back to Requests")
                }
            }
        }
    }

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Request?")
            },

            text = {
                Text(
                    "Are you sure you want to delete this request?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        authViewModel.deleteRequest(
                            requestId
                        )

                        showDeleteDialog = false

                        navController.popBackStack()
                    }
                ) {

                    Text(
                        text = "Delete",
                        color =
                            MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun DetailLabel(text: String) {

    Text(
        text = text,
        style =
            MaterialTheme.typography.labelLarge,
        color =
            MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun RowWithIcon(text: String) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector =
                Icons.Default.LocationOn,
            contentDescription = "Location",
            tint =
                MaterialTheme.colorScheme.primary
        )

        Text(
            text = text,
            modifier =
                Modifier.padding(start = 8.dp),
            style =
                MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun RowWithWarningIcon(text: String) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector =
                Icons.Default.Warning,
            contentDescription = "Medical issue",
            tint =
                MaterialTheme.colorScheme.tertiary
        )

        Text(
            text = text,
            modifier =
                Modifier.padding(start = 8.dp),
            style =
                MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun RowWithStatusIcon() {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector =
                Icons.Default.CheckCircle,
            contentDescription = "Status",
            tint =
                MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Pending",
            modifier =
                Modifier.padding(start = 8.dp),
            style =
                MaterialTheme.typography.bodyLarge
        )
    }
}