package com.example.dentalapp.view.dashboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.baseColor
import com.example.dentalapp.view.customcomponent.*
import com.example.dentalapp.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(
    navController: NavHostController,
    usersViewModel: UsersViewModel
){
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    usersViewModel.getUserLogin(emailLogin!!)
    val userLogin by usersViewModel.userLogin.collectAsState(emptyList())

    val namaList = userLogin.map {
        it.nama.toString()
    }
    val namaUser = namaList.joinToString()

    val activity = (LocalContext.current as? Activity)
    BackHandler(
        enabled = true,
        onBack = {
            activity?.finish()
        }
    )

    Column() {
        TopAppBar(modifier = Modifier
            .background(MaterialTheme.colors.primary),
            title = {
                Text(text = "Home")
            }
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(20.dp)
        ){

            //Selamat datang user
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(text = "Selamat datang..\n${namaUser}")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_24),
                    contentDescription = null,
                    tint = baseColor,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp)
                )
            }

            CustomSpacer()
            CustomDivider()
            CustomSpacer()
            CustomSpacer()
            CustomSpacer()
            CustomSpacer()
            CustomSpacer()

            //Buat reservasi
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.MemilihTanggalScreen.route)
                    },
                iconResId = R.drawable.ic_add,
                title = "BUAT RESERVASI"
            )

            CustomSpacer()
            CustomSpacer()
            //History reservasi
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.ListReservasiScreen.route)
                    },
                iconResId = R.drawable.ic_history,
                title = "HISTORY RESERVASI"
            )

            CustomSpacer()
            CustomSpacer()
            //List dokter
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.ListDokterScreen.route)
                    },
                iconResId = R.drawable.ic_history,
                title = "LIST DOKTER"
            )

            CustomSpacer()
            CustomSpacer()
            //List layanan
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.ListLayananScreen.route)
                    },
                iconResId = R.drawable.ic_history,
                title = "LIST LAYANAN"
            )

            CustomSpacer()
            CustomSpacer()
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.ProfileScreen.route)
                    },
                iconResId = R.drawable.ic_person_24,
                title = "PROFILE"
            )

        }
    }
}