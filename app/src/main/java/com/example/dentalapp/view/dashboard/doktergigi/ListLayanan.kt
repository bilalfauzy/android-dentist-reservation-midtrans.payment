package com.example.dentalapp.view.dashboard.doktergigi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.model.Layanan
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.LayananViewModel

@Composable
fun ListLayanan(
    navController: NavHostController,
    layananViewModel: LayananViewModel
) {
    val listLayanan by layananViewModel.layananList.collectAsState(emptyList())
    val context = LocalContext.current

    Column(
        modifier = Modifier.background(backColor)
    ) {
        MyAppBar(
            title = "Layanan klinik",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
            }
        )

        PasienListLayanan(
            items = listLayanan,
            navController = navController,
            onItemClick = {
                navController.navigate(Screen.DetailLayananScreen.route +
                        "/${it.nama}/${it.biaya}/${it.deskripsi}/${it.gambar}")
            }
        )

    }

}

@Composable
fun PasienListLayanan(
    items: List<Layanan>,
    navController: NavHostController,
    onItemClick: (Layanan) -> Unit
){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        items(items) { layanan ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(layanan) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(){
                        Text(text = "ID layanan  : ${layanan.idLayanan}")
                        Text(text = "Nama : ${layanan.nama}")
                        Text(text = "Biaya : Rp${layanan.biaya}")

                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            CustomDivider()
        }
    }
}