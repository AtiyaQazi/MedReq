package com.example.MedReq

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val requestsKey =
        stringPreferencesKey("requests")

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _authState.value =
                if (firebaseAuth.currentUser == null) {
                    AuthState.Unauthenticated
                } else {
                    AuthState.Authenticated
                }
        }
    }

    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value =
                AuthState.Error("Email or password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val user = auth.currentUser

                    if (user != null) {
                        saveUserData(
                            user.email ?: "",
                            "",
                            ""
                        )
                    }

                    _authState.value =
                        AuthState.Success("Successfully logged in")

                } else {

                    _authState.value =
                        AuthState.Error(
                            task.exception?.message
                                ?: "Invalid credentials"
                        )
                }
            }
    }

    fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {

        if (
            email.isEmpty() ||
            password.isEmpty() ||
            firstName.isEmpty() ||
            lastName.isEmpty()
        ) {
            _authState.value =
                AuthState.Error("All fields are required")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val user = auth.currentUser

                if (user != null) {
                    saveUserData(
                        user.email ?: "",
                        firstName,
                        lastName
                    )
                }

                _authState.value =
                    AuthState.Success(
                        "Account created successfully"
                    )

            } else {

                _authState.value =
                    AuthState.Error(
                        task.exception?.message
                            ?: "Signup failed"
                    )
            }
        }
    }

    fun signout() {

        auth.signOut()

        viewModelScope.launch {
            dataStore.edit {
                it.clear()
            }

            _authState.value =
                AuthState.Unauthenticated
        }
    }

    fun submitRequest(
        name: String,
        injuryType: String,
        location: String,
        priority: String
    ) {

        if (
            name.isEmpty() ||
            injuryType.isEmpty() ||
            location.isEmpty()
        ) {
            _authState.value =
                AuthState.Error("All fields are required")
            return
        }

        viewModelScope.launch {

            try {

                val requestId =
                    System.currentTimeMillis().toString()

                dataStore.edit { preferences ->

                    val requests =
                        preferences[requestsKey]
                            ?.split("|")
                            ?.filter { it.isNotBlank() }
                            ?.toMutableList()
                            ?: mutableListOf()

                    requests.add(
                        "$requestId:$name:$injuryType:$location:$priority"
                    )

                    preferences[requestsKey] =
                        requests.joinToString("|")
                }

                _authState.value =
                    AuthState.Success(
                        "Request submitted successfully"
                    )

            } catch (e: Exception) {

                _authState.value =
                    AuthState.Error(
                        "Failed to submit request"
                    )
            }
        }
    }

    suspend fun getRequests(): List<String> {

        val data =
            dataStore.data.first()

        return data[requestsKey]
            ?.split("|")
            ?.filter { it.isNotBlank() }
            ?: emptyList()
    }

    fun updateRequest(
        requestId: String,
        name: String,
        injuryType: String,
        location: String,
        priority: String
    ) {

        viewModelScope.launch {

            dataStore.edit { preferences ->

                val requests =
                    preferences[requestsKey]
                        ?.split("|")
                        ?.filter { it.isNotBlank() }
                        ?: emptyList()

                val updatedRequests =
                    requests.map { request ->

                        val parts =
                            request.split(":")

                        if (
                            parts.isNotEmpty() &&
                            parts[0] == requestId
                        ) {
                            "$requestId:$name:$injuryType:$location:$priority"
                        } else {
                            request
                        }
                    }

                preferences[requestsKey] =
                    updatedRequests.joinToString("|")
            }

            _authState.value =
                AuthState.Success(
                    "Request updated successfully"
                )
        }
    }

    fun deleteRequest(requestId: String) {

        viewModelScope.launch {

            dataStore.edit { preferences ->

                val requests =
                    preferences[requestsKey]
                        ?.split("|")
                        ?.filter { it.isNotBlank() }
                        ?: emptyList()

                val remainingRequests =
                    requests.filter { request ->

                        val parts =
                            request.split(":")

                        parts.isEmpty() ||
                                parts[0] != requestId
                    }

                preferences[requestsKey] =
                    remainingRequests.joinToString("|")
            }

            _authState.value =
                AuthState.Success(
                    "Request deleted successfully"
                )
        }
    }

    private fun saveUserData(
        email: String,
        firstName: String,
        lastName: String
    ) {

        viewModelScope.launch {

            dataStore.edit { preferences ->

                preferences[
                    stringPreferencesKey("user_email")
                ] = email

                preferences[
                    stringPreferencesKey("user_first_name")
                ] = firstName

                preferences[
                    stringPreferencesKey("user_last_name")
                ] = lastName
            }
        }
    }

    suspend fun getUserEmail(): String? {

        return dataStore.data.first()[
            stringPreferencesKey("user_email")
        ]
    }

    suspend fun getUserFullName(): Pair<String?, String?> {

        val data =
            dataStore.data.first()

        return Pair(
            data[stringPreferencesKey("user_first_name")],
            data[stringPreferencesKey("user_last_name")]
        )
    }
}

sealed class AuthState {

    object Authenticated : AuthState()

    object Unauthenticated : AuthState()

    object Loading : AuthState()

    data class Error(
        val message: String
    ) : AuthState()

    data class Success(
        val message: String
    ) : AuthState()
}