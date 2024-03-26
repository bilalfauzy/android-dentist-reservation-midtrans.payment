package com.example.dentalapp.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.ReservasiViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DetailReservasi(
    navController: NavHostController,
    reservasiViewModel: ReservasiViewModel,
    namaUser: String,
    namaDokter: String,
    biaya: String,
    pembayaran: String,
    status: String,
    tanggal: String,
    hari: String,
    jam: String
){
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val context = LocalContext.current

    if (emailLogin!!.isNotEmpty() && emailLogin.equals("admin@gmail.com")){
        Column() {
            MyAppBar(
                title = "Detail Reservasi",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.ListReservasiScreen.route, false)
                }
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(20.dp)
            ){
                Text(text = "Nama pemesan :\n${namaUser}")
                Text(text = "Dokter :\n${namaDokter}")
                Text(text = "Tanggal :\n${hari}, ${tanggal}")
                Text(text = "Jam :\n${jam}")
                Text(text = "Biaya :\n${biaya}")
                Text(text = "Metode pembayaran :\n${pembayaran}")
                Text(text = "Status pesanan :\n${status}")
            }
        }
    }else{
        Column() {
            MyAppBar(
                title = "Detail reservasi",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.ListReservasiScreen.route, false)
                }
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(20.dp)
            ){
                Text(text = "Nama pemesan :\n${namaUser}")
                Text(text = "Dokter :\n${namaDokter}")
                Text(text = "Tanggal :\n${hari}, ${tanggal}")
                Text(text = "Jam :\n${jam}")
                Text(text = "Biaya :\n${biaya}")
                Text(text = "Metode pembayaran :\n${pembayaran}")
                Text(text = "Status pesanan :\n${status}")

            }
        }
    }

}