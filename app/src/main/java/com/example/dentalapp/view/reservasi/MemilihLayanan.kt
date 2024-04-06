package com.example.dentalapp.view.reservasi


import android.widget.Toast
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.dentalapp.model.Layanan
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.LayananViewModel

@Composable
fun MemilihLayanan(
    navController: NavHostController,
    layananViewModel: LayananViewModel,
    namaDokter: String,
    tanggal: String,
    hari: String,
    jam: String
) {
    layananViewModel.getLayananByDokter(namaDokter)
    val layananList by layananViewModel.layananList.collectAsState(emptyList())
    val namaLayanan = remember {
        mutableStateOf("")
    }
    val keluhan = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    var errorText = remember {
        mutableStateOf("")
    }
    var isError = false

    Column() {
        MyAppBar(
            title = "Memilih layanan",
            navigationIcon = Icons.Outlined.KeyboardArrowLeft,
            onNavigationClick = {
                navController.popBackStack(Screen.MemilihTanggalScreen.route, false)
            }
        )

        Column(
            modifier = Modifier
                .background(backColor)
                .fillMaxSize()
                .padding(40.dp)
        ) {

            ListLayanan(
                items = layananList,
                onItemClick = {
                    namaLayanan.value = it.nama.toString()
                }
            )
            CustomSpacer()
            Text(
                text = namaLayanan.value,
                fontWeight = FontWeight.Medium
            )
            CustomSpacer()
            OutlinedTextField(
                value = keluhan.value,
                onValueChange = {
                    keluhan.value = it
                    isError = it.isEmpty()
                },
                label = {
                    Text(text = "Keluhan/penyakit")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = "Email",
                        tint = MaterialTheme.colors.primary
                    )
                },
                isError = isError,
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))
            MyButton(
                onClick = {
                    if (
                        namaLayanan.value.isEmpty() || keluhan.value.isEmpty()
                    ) {
                        errorText.value = "Pastikan semua form terisi!"
                    } else {
                        navController.navigate(
                            Screen.MelakukanPembayaranScreen.route +
                                    "/${namaDokter}/${tanggal}/${hari}/${jam}/${keluhan.value}"
                        )
                    }
                },
                text = "LANJUT"
            )

            if (errorText.value.isNotEmpty()) {
                Toast.makeText(context, errorText.value, Toast.LENGTH_SHORT).show()
                errorText.value = ""
            }

        }
    }

}

@Composable
fun ListLayanan(
    items: List<Layanan>,
    onItemClick: (Layanan) -> Unit
){

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        items(items) { layanan ->
            val imgPainter = rememberAsyncImagePainter(layanan.gambar)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(layanan) }),
                elevation = 2.dp,
                backgroundColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = imgPainter,
                        contentDescription = "foto layanan",
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .padding(end = 8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
                            text = "${layanan.nama}",
                            fontWeight = FontWeight.Medium
                        )
                        CustomSpacer()
                        Text(text = "Rp${layanan.biaya}")

                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            CustomSpacer()
        }
    }
}