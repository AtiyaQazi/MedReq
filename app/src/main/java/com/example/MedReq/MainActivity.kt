package com.example.MedReq

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.MedReq.pages.HomePage
import com.example.MedReq.pages.LoginPage
import com.example.MedReq.pages.NewRequestPage
import com.example.MedReq.pages.ProfilePage
import com.example.MedReq.pages.RequestDetailsPage
import com.example.MedReq.pages.SignupPage
import com.example.MedReq.ui.theme.FieldMedicAppTheme

private val Context.dataStore: DataStore<Preferences> by
preferencesDataStore(name = "user_prefs")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel =
            AuthViewModel(dataStore)

        setContent {

            FieldMedicAppTheme {

                val navController =
                    rememberNavController()

                val authState by
                authViewModel.authState
                    .observeAsState()

                DisposableEffect(authState) {

                    when (authState) {

                        is AuthState.Authenticated -> {

                            val currentRoute =
                                navController
                                    .currentBackStackEntry
                                    ?.destination
                                    ?.route

                            if (currentRoute == "login") {

                                navController.navigate("home") {

                                    popUpTo("login") {
                                        inclusive = true
                                    }

                                    launchSingleTop = true
                                }
                            }
                        }

                        is AuthState.Unauthenticated -> {

                            val currentRoute =
                                navController
                                    .currentBackStackEntry
                                    ?.destination
                                    ?.route

                            if (
                                currentRoute != "login" &&
                                currentRoute != "signup"
                            ) {

                                navController.navigate("login") {

                                    popUpTo(0) {
                                        inclusive = true
                                    }

                                    launchSingleTop = true
                                }
                            }
                        }

                        else -> {
                            // Loading, Error, Success
                        }
                    }

                    onDispose { }
                }

                Surface(
                    modifier =
                        Modifier.fillMaxSize(),

                    color =
                        MaterialTheme.colorScheme.background
                ) {

                    NavHost(

                        navController =
                            navController,

                        startDestination =
                            "login",

                        modifier =
                            Modifier.fillMaxSize()
                    ) {

                        // LOGIN
                        composable("login") {

                            LoginPage(
                                modifier =
                                    Modifier.fillMaxSize(),

                                navController =
                                    navController,

                                authViewModel =
                                    authViewModel
                            )
                        }

                        // SIGN UP
                        composable("signup") {

                            SignupPage(
                                modifier =
                                    Modifier.fillMaxSize(),

                                navController =
                                    navController,

                                authViewModel =
                                    authViewModel
                            )
                        }

                        // HOME / REQUESTS
                        composable("home") {

                            HomePage(
                                modifier =
                                    Modifier.fillMaxSize(),

                                navController =
                                    navController,

                                authViewModel =
                                    authViewModel
                            )
                        }

                        // NEW REQUEST
                        composable("new-request") {

                            NewRequestPage(
                                modifier =
                                    Modifier.fillMaxSize(),

                                navController =
                                    navController,

                                authViewModel =
                                    authViewModel
                            )
                        }

                        // PROFILE
                        composable("profile") {

                            ProfilePage(
                                modifier =
                                    Modifier.fillMaxSize(),

                                navController =
                                    navController,

                                authViewModel =
                                    authViewModel
                            )
                        }

                        // REQUEST DETAILS
                        composable(

                            route =
                                "request-details" +
                                        "?id={id}" +
                                        "&name={name}" +
                                        "&injury={injury}" +
                                        "&location={location}" +
                                        "&priority={priority}",

                            arguments = listOf(

                                navArgument("id") {
                                    defaultValue = ""
                                },

                                navArgument("name") {
                                    defaultValue = ""
                                },

                                navArgument("injury") {
                                    defaultValue = ""
                                },

                                navArgument("location") {
                                    defaultValue = ""
                                },

                                navArgument("priority") {
                                    defaultValue = "High"
                                }
                            )

                        ) {

                            RequestDetailsPage(

                                modifier =
                                    Modifier.fillMaxSize(),

                                navController =
                                    navController,

                                authViewModel =
                                    authViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}