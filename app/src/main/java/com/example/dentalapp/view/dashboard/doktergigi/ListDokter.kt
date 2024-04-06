package com.example.dentalapp.view.dashboard.doktergigi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.dentalapp.model.DokterGigi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.warningColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.DokterGigiViewModel

@Composable
fun ListDokter(
    navController: NavHostController,
    dokterGigiViewModel: DokterGigiViewModel
){
    val listDokter by dokterGigiViewModel.dokterList.collectAsState(emptyList())
    val context = LocalContext.current

    Column(
        modifier = Modifier.background(backColor)
    ) {
        MyAppBar(
            title = "List dokter gigi",
            navigationIcon = Icons.Outlined.KeyboardArrowLeft,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
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
    ) {
        items(items) { dokter ->
            val imgPainter = rememberAsyncImagePainter(dokter.fotoDokterUrl)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(dokter) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight(0.3f),
                ) {
                    Image(
                        painter = imgPainter,
                        contentDescription = "foto dokter",
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
                            text = "${dokter.nama}",
                            fontWeight = FontWeight.Medium
                        )
                        CustomDivider()
                        CustomSpacer()
                        Text(text = "${dokter.gender}")
                        Text(text = "${dokter.spesialis}")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = "rating",
                            tint = warningColor)
                        Text(text = "${dokter.rating}")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                }
            }
            CustomDivider()
        }
    }

}