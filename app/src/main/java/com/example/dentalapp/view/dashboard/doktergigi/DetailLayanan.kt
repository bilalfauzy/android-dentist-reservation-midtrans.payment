package com.example.dentalapp.view.dashboard.doktergigi

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.LayananViewModel
import java.net.URLDecoder

@Composable
fun DetailLayanan(
    navController: NavHostController,
    layananViewModel: LayananViewModel,
    id: String,
    nama: String,
    biaya: String,
    deskripsi: String,
    gambar: String
) {

    var isError = false

    val scrollState = rememberScrollState()
    var errorText = remember {
        mutableStateOf("")
    }

    val decodedUrl = URLDecoder.decode(gambar, "UTF-8").toString()
    val painter = rememberAsyncImagePainter(decodedUrl)
    Log.e("urllayanan", decodedUrl)

    Column() {
        MyAppBar(
            title = "Detail layanan",
            navigationIcon = Icons.Outlined.KeyboardArrowLeft,
            onNavigationClick = {
                navController.popBackStack(Screen.ListDokterScreen.route, false)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    imageVector = Icons.Outlined.MedicalServices,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(200.dp, 200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                CustomSpacer()
                Text(text = "${nama}")
                CustomSpacer()
                Text(text = "${biaya}")
                CustomSpacer()
                CustomDivider()
                Text(
                    text = "${deskripsi}",
                    maxLines = 10
                )
            }
        }

    }

}