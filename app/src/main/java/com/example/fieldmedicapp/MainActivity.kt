package com.example.fieldmedicapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fieldmedicapp.ui.theme.FieldMedicAppTheme
import com.example.fieldmedicapp.pages.HomePage
import com.example.fieldmedicapp.pages.LoginPage
import com.example.fieldmedicapp.pages.NewRequestPage
import com.example.fieldmedicapp.pages.ProfilePage
import com.example.fieldmedicapp.pages.RequestDetailsPage
import com.example.fieldmedicapp.pages.SignupPage

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel = AuthViewModel(dataStore)

        setContent {
            val navController = rememberNavController()
            FieldMedicAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginPage(modifier = Modifier, navController = navController, authViewModel = authViewModel) }
                        composable("signup") { SignupPage(modifier = Modifier, navController = navController, authViewModel = authViewModel) }
                        composable("home") { HomePage(modifier = Modifier, navController = navController, authViewModel = authViewModel) }
                        composable("new-request") { NewRequestPage(modifier = Modifier, navController = navController, authViewModel = authViewModel) }
                        composable("profile") { ProfilePage(modifier = Modifier, navController = navController, authViewModel = authViewModel) }
                        composable(
                            "request-details?id={id}&name={name}&injury={injury}&location={location}",
                            arguments = listOf(
                                navArgument("id") { defaultValue = "" },
                                navArgument("name") { defaultValue = "" },
                                navArgument("injury") { defaultValue = "" },
                                navArgument("location") { defaultValue = "" }
                            )
                        ) { backStackEntry ->
                            RequestDetailsPage(
                                modifier = Modifier,
                                navController = navController,
                                authViewModel = authViewModel
                            )
                        }
                    }
                }
            }

            // Observe auth state to handle navigation on app start
            authViewModel.authState.observe(this) { state ->
                if (state is AuthState.Authenticated) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }
}