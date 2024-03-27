package com.example.dentalapp.view.reservasi

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.model.DokterGigi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.DokterGigiViewModel

@Composable
fun MemilihDokter(
    navController: NavHostController,
    dokterGigiViewModel: DokterGigiViewModel,
    tanggal: String,
    jam: String
){
    val listDokter by dokterGigiViewModel.dokterList.collectAsState(emptyList())
    val context = LocalContext.current

    val selectedDate = remember {
        mutableStateOf("")
    }

    val selectedJam = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.background(backColor)
    ) {
        MyAppBar(
            title = "Pilih dokter",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.MemilihTanggalScreen.route, false)
            }
        )

        MyListDokter(
            listDokter,
            navController,
            onItemClick = {
                navController.navigate(Screen.DetailDokterScreen.route +
                        "/${it.id}/${it.nama}/${it.gender}/${it.spesialis}/${it.umur}")
            }
        )
    }
}

@Composable
fun MyListDokter(
    items: List<DokterGigi>,
    navController: NavHostController,
    onItemClick: (DokterGigi) -> Unit
){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        items(items) { dokter ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(dokter) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(){
                        Text(text = "ID dokter  : ${dokter.id}")
                        Text(text = "Nama : ${dokter.nama}")
                        Text(text = "Spesialis : ${dokter.spesialis}")
                    }
                    Spacer(modifier = Modifier.weight(1f))

                }
            }
            CustomDivider()
        }
    }

}