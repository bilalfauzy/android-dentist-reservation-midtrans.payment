package com.example.dentalapp.viewmodel.loginregister

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.example.dentalapp.routes.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun onLoginClick(navController: NavHostController , email: String, password: String, context: Context){
        if (!isEmailValid(email)){
            _loginState.value = LoginState.InvalidEmail
            return
        }
        if (!isPasswordValid(password)){
            _loginState.value = LoginState.InvalidPassword
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _loginState.value = LoginState.Success(firebaseAuth.currentUser)
                    if (email == "admin@gmail.com" && password == "admin123"){
                        navController.navigate(
                            Screen.AdminHomeScreen.route,
                            navOptions {
                                popUpTo(Screen.LoginScreen.route){
                                    inclusive = true
                                }
                            }
                        )
                    }else{
                        navController.navigate(
                            Screen.HomeScreen.route,
                            navOptions {
                                popUpTo(Screen.LoginScreen.route){
                                    inclusive = true
                                }
                            }
                        )
                    }
                }else{
                    _loginState.value = LoginState.Error(it.exception?.message)
                    Toast.makeText(context, "Email atau password salah!!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isEmailValid(email: String) : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String) : Boolean{
        return password.length >= 6
    }
}


sealed class LoginState{
    object InvalidEmail : LoginState()
    object InvalidPassword: LoginState()
    data class Error(val message: String?) : LoginState()
    data class Success(val user: FirebaseUser?) : LoginState()
}