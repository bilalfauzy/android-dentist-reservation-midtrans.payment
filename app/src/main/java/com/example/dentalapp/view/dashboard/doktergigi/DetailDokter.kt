package com.example.dentalapp.view.dashboard.doktergigi

import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
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
import com.example.dentalapp.model.DokterGigi
import com.example.dentalapp.model.JadwalDokter
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.errorColor
import com.example.dentalapp.view.customcomponent.CustomCard
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.CustomExposedDropdown
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.CustomTextField
import com.example.dentalapp.view.customcomponent.IconButton
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
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
    val idDok = remember {
        mutableStateOf(idDokter)
    }
    val nama = remember {
        mutableStateOf(namaDokter)
    }

    val gender = remember {
        mutableStateOf<String?>(genderDokter)
    }
    val listGender = listOf(
        "Laki - laki",
        "Perempuan",
    )
    val spesialis = remember {
        mutableStateOf(spesialisDok)
    }
    val umur = remember {
        mutableStateOf(umurDokter)
    }

    var isError = false
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var errorText = remember {
        mutableStateOf("")
    }

    jadwalViewModel.getJadwalDokter(idDokter)
    val jadwalList by jadwalViewModel.jadwalList.collectAsState(emptyList())

    Column() {
        MyAppBar(
            title = "Detail dokter",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.ListDokterScreen.route, false)
            }
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(40.dp)

        ){

            //id dokter
            CustomTextField(
                value = idDok.value,
                onValueChange = {
                    idDok.value = it
                    isError = it.isEmpty()
                },
                label = "ID dokter",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "ID",
                        tint = MaterialTheme.colors.primary
                    )
                },
                isError = isError
            )

            //data dokter
            CustomTextField(
                value = nama.value,
                onValueChange = {
                    nama.value = it
                    isError = it.isEmpty()
                },
                label = "Nama dokter",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "ID",
                        tint = MaterialTheme.colors.primary
                    )
                },
                isError = isError
            )

            //gender dokter
            CustomExposedDropdown(options = listGender, label = "Gender", errorText = errorText.value, onOptionSelected = {
                gender.value = it
            }, selectedOption = gender.value)

            //spesialis dokter
            CustomTextField(
                value = spesialis.value,
                onValueChange = {
                    spesialis.value = it
                    isError = it.isEmpty()
                },
                label = "Spesialis",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "ID",
                        tint = MaterialTheme.colors.primary
                    )
                },
                isError = isError
            )

            //umur dokter
            CustomTextField(
                value = umur.value,
                onValueChange = {
                    umur.value = it
                    isError = it.isEmpty()
                },
                label = "Umur",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "ID",
                        tint = MaterialTheme.colors.primary
                    )
                },
                isError = isError
            )

            CustomSpacer()
            CustomSpacer()
            Text(text = "List jadwal")
            CustomSpacer()
            CustomCard() {
                MyListJadwal(
                    items = jadwalList,
                    jadwalViewModel,
                    context,
                    navController,
                    onItemClick = {
                        navController.navigate(
                            Screen.DetailJadwalScreen.route +
                                    "/${it.id}/${it.idDokter}/${it.hari}/${it.jam}/${it.tanggal}/${it.status}"
                        )
                    }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            MyButton(
                onClick = {
                    val dokterGigi = DokterGigi(
                        id = idDok.value,
                        nama = nama.value,
                        gender = gender.value,
                        spesialis = spesialis.value,
                        umur = umur.value
                    )

                    if (idDok.value.isEmpty() ||
                        nama.value.isEmpty() ||
                        gender.value == null ||
                        spesialis.value.isEmpty() ||
                        umur.value.isEmpty()
                    ){
                        errorText.value = "Pastikan semua form terisi!"
                    }else{
                        dokterGigiViewModel.updateDokter(dokterGigi, context)
                    }
                },
                text = "UPDATE DATA"
            )

        }
    }
}

@Composable
fun MyListJadwal(
    items: List<JadwalDokter>,
    jadwalViewModel: JadwalViewModel,
    context: Context,
    navController: NavHostController,
    onItemClick: (JadwalDokter) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
    ) {
        items(items) { jadwal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(jadwal) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ){
                    Column(){
                        Text(text = "ID jadwal  : ${jadwal.id}")
                        Text(text = "Tanggal : ${jadwal.hari}, ${jadwal.tanggal}")
                        Text(text = "Jam : ${jadwal.jam}")
                        Text(text = "Status : ${jadwal.status}")
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.clickable {
                            jadwalViewModel.deleteJadwal(jadwal.idDokter!!, jadwal.id!!, context)
                        },
                        iconResId = R.drawable.baseline_delete_24,
                        description = "hapus",
                        color = errorColor
                    )
                }
            }
            CustomDivider()
        }
    }
}