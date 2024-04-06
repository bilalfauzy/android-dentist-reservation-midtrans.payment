package com.example.dentalapp.view.dashboard.doktergigi

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomCard
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.DokterGigiViewModel
import com.example.dentalapp.viewmodel.JadwalViewModel

@Composable
fun DetailDokter(
    navController: NavHostController,
    jadwalViewModel: JadwalViewModel,
    dokterGigiViewModel: DokterGigiViewModel,
    idDokter: String,
    namaDokter: String,
    genderDokter: String,
    spesialisDok: String,
    umurDokter: String
){
    dokterGigiViewModel.dokterById(idDokter)
    val listDokter by dokterGigiViewModel.dokterList.collectAsState(emptyList())

    val url = listDokter.map {
        it.fotoDokterUrl.toString()
    }.joinToString()
    val painter = rememberAsyncImagePainter(url)
    Log.e("urldokter", url)

    Column() {
        MyAppBar(
            title = "Detail dokter",
            navigationIcon = Icons.Filled.KeyboardArrowLeft,
            onNavigationClick = {
                navController.popBackStack(Screen.ListDokterScreen.route, false)
            }
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(
                painter = painter,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            CustomSpacer()
            CustomCard {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Nama \t\t\t\t:\t${namaDokter}")
                    CustomSpacer()
                    Text(text = "Umur \t\t\t\t:\t${umurDokter} tahun")
                    CustomSpacer()
                    Text(text = "Gender \t\t\t:\t${genderDokter}")
                    CustomSpacer()
                    Text(text = "Spesialis\t:\t${spesialisDok}")

                }

            }
        }
    }
}