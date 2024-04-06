package com.example.dentalapp.view.dashboard.doktergigi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
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
            navigationIcon = Icons.Outlined.KeyboardArrowLeft,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
            }
        )

        PasienListLayanan(
            items = listLayanan,
            navController = navController,
            onItemClick = {
                navController.navigate(Screen.DetailLayananScreen.route +
                        "${it.idLayanan}/${it.nama}/${it.biaya}/${it.deskripsi}/p")
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
    ) {
        items(items) { layanan ->
            val imgPainter = rememberAsyncImagePainter(layanan.gambar)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(layanan) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                        .fillMaxHeight(0.3f)
                ) {
                    Image(
                        imageVector = Icons.Outlined.MedicalServices,
                        contentDescription = "foto layanan",
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .padding(end = 8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ){
                        Text(
                            text = "${layanan.nama}",
                            fontWeight = FontWeight.Medium
                        )
                        Text(text = "Rp${layanan.biaya}")

                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            CustomDivider()
        }
    }
}