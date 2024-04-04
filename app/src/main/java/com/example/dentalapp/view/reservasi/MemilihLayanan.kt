package com.example.dentalapp.view.reservasi


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.model.Layanan
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.CustomTextField
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
            navigationIcon = Icons.Filled.ArrowBack,
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
            //isi keluhan
            CustomTextField(
                value = keluhan.value,
                onValueChange = {
                    keluhan.value = it
                    isError = it.isEmpty()
                },
                label = "Keluhan",
                leadingIcon = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_email
                        ),
                        contentDescription = "Email",
                        tint = MaterialTheme.colors.primary
                    )
                },
                isError = isError
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