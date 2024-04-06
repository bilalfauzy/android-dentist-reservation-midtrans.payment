package com.example.dentalapp.view.loginregister

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomExposedDropdown
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.CustomTextField
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.loginregister.RegisterViewModel

@Composable
fun Register(
    navController: NavHostController,
    registerViewModel: RegisterViewModel
){
    val scrollState = rememberScrollState()
    var isError = false
    val context = LocalContext.current

    val listGender = listOf(
        "Laki - laki",
        "Perempuan",
    )
    var errorText = remember {
        mutableStateOf("")
    }

    Column(){

        MyAppBar(
            title = "Register",
            navigationIcon = Icons.Filled.ArrowBackIosNew,
            onNavigationClick = {
                navController.popBackStack(Screen.LoginScreen.route, false)
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Register",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
                CustomSpacer()
                CustomSpacer()
                //nama
                CustomTextField(
                    value = registerViewModel.nama.value,
                    onValueChange = {
                        registerViewModel.onNameChange(it)
                    },
                    label = "Masukkan nama",
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_person_24),
                            contentDescription = "Nama",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )
                //umur
                CustomTextField(
                    value = registerViewModel.umur.value,
                    onValueChange = {
                        registerViewModel.onAgeChange(it)
                    },
                    label = "Masukkan umur",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.baseline_face_24),
                            contentDescription = "Umur",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )
                //gender
                CustomExposedDropdown(options = listGender, label = "Pilih gender", errorText = errorText.value, onOptionSelected = {
                    registerViewModel.onGenderChange(it)
                }, selectedOption = registerViewModel.gender.value)

                //nomor wa
                CustomTextField(
                    value = registerViewModel.noWa.value,
                    onValueChange = {
                        registerViewModel.onNomorChange(it)
                    },
                    label = "Nomor telepon/Whatsapp",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_phone_24),
                            contentDescription = "Nomor",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //email
                CustomTextField(
                    value = registerViewModel.email.value,
                    onValueChange = {
                        registerViewModel.onEmailChange(it)
                    },
                    label = "Masukkan email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_email),
                            contentDescription = "Email",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //password
                CustomTextField(
                    value = registerViewModel.password.value,
                    onValueChange = {
                        registerViewModel.onPasswordChange(it)
                    },
                    label = "Masukkan password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_lock),
                            contentDescription = "Password",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //konfirmasi password
                CustomTextField(
                    value = registerViewModel.confirmPassword.value,
                    onValueChange = {
                        registerViewModel.onConfirmPasswordChange(it)
                    },
                    label = "Konfirmasi password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_lock),
                            contentDescription = "Password",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                CustomSpacer()
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    onClick = {
                        registerViewModel.onRegisterClick(navController, context)
                    }) {
                    if (registerViewModel.isLoading.value){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(4.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }else{
                        Text(text = "REGISTER")
                    }
                }
                CustomSpacer()
                Text(text = "Sudah mempunyai akun?")
                CustomSpacer()
                MyButton(
                    onClick = {
                        navController.popBackStack(Screen.LoginScreen.route, false)
                    },
                    text = "LOGIN"
                )
            }

        }


    }
}



