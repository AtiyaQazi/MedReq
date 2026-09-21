package com.example.fieldmedicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fieldmedicapp.pages.BottomNavItem
import com.example.fieldmedicapp.pages.HomePage
import com.example.fieldmedicapp.pages.LoginPage
import com.example.fieldmedicapp.pages.NewRequestPage
import com.example.fieldmedicapp.pages.ProfilePage
import com.example.fieldmedicapp.pages.RequestDetailsPage
import com.example.fieldmedicapp.pages.SignupPage
import kotlinx.coroutines.launch

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf("home") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val menuItems = listOf("home", "new-request", "profile", "request-details")
    val showBottomBar = navController.currentBackStackEntry?.destination?.route in listOf("home", "new-request", "profile", "request-details")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.replace("-", " ").replaceFirstChar { it.uppercase() }) },
                        selected = selectedItem == item,
                        onClick = {
                            navController.navigate(item) { launchSingleTop = true }
                            selectedItem = item
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
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
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = modifier.padding(padding)
            ) {
                composable("login") { LoginPage(modifier, navController, authViewModel) }
                composable("signup") { SignupPage(modifier, navController, authViewModel) }
                composable("home") { HomePage(modifier, navController, authViewModel) }
                composable("new-request") { NewRequestPage(modifier, navController, authViewModel) }
                composable("profile") { ProfilePage(modifier, navController, authViewModel) }
                composable(
                    "request-details?id={id}&name={name}&injury={injury}&location={location}",
                    arguments = listOf(
                        navArgument("id") { defaultValue = "" },
                        navArgument("name") { defaultValue = "" },
                        navArgument("injury") { defaultValue = "" },
                        navArgument("location") { defaultValue = "" }
                    )
                ) { RequestDetailsPage(modifier, navController, authViewModel) }
            }
        }
    }
}