package com.example.dentalapp.view.reservasi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomCard
import com.example.dentalapp.view.customcomponent.CustomSpacer
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

    Column() {
        MyAppBar(
            title = "Detail reservasi",
            navigationIcon = Icons.Outlined.KeyboardArrowLeft,
            onNavigationClick = {
                navController.popBackStack(Screen.ListReservasiScreen.route, false)
            }
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomCard{
                Column (
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ){
                    Text(text = "Pemesan \t\t\t\t: ${namaUser}")
                    CustomSpacer()
                    Text(text = "Dokter \t\t\t\t\t\t: ${namaDokter}")
                    CustomSpacer()
                    Text(text = "Tanggal \t\t\t\t\t: ${hari}, ${tanggal}")
                    CustomSpacer()
                    Text(text = "Jam \t\t\t\t\t\t\t\t: ${jam}")
                    CustomSpacer()
                    Text(text = "Biaya \t\t\t\t\t\t\t: ${biaya}")
                    CustomSpacer()
                    Text(text = "Pembayaran  : ${pembayaran}")
                    CustomSpacer()
                    Text(text = "Status \t\t\t\t\t\t: ${status}")

                }
            }
        }


    }


}