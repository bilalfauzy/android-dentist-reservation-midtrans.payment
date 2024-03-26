package com.example.dentalapp.view.dashboard.doktergigi

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.model.JadwalDokter
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.*
import com.example.dentalapp.viewmodel.JadwalViewModel
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailJadwal(
    navController: NavHostController,
    jadwalViewModel: JadwalViewModel,
    idJadwal: String,
    idDokter: String,
    hari: String,
    jam: String,
    tanggal: String,
    status: String
){
    val idDok = remember {
        mutableStateOf(idDokter)
    }
    val idJad = remember {
        mutableStateOf(idJadwal)
    }
    val harii = remember {
        mutableStateOf<String?>(hari)
    }
    val listHari = listOf(
        "Senin",
        "Selasa",
        "Rabu",
        "Kamis",
        "Jumat",
        "Sabtu"
    )

    val jamm = remember {
        mutableStateOf<String?>(jam)
    }
    val listJam = listOf(
        "08.00",
        "10.00",
        "13.00",
        "15.00"
    )

    val statuss = remember {
        mutableStateOf<String?>(status)
    }

    val listStatus = listOf(
        "Tersedia",
        "Kosong"
    )

    val selectedDate = remember {
        mutableStateOf(tanggal)
    }

    val context = LocalContext.current
    var isError = false
    val scrollState = rememberScrollState()
    var errorText = remember {
        mutableStateOf("")
    }

    Column {
        TopAppBar(modifier = Modifier
            .background(MaterialTheme.colors.primary),
            title = {
                Text(text = "Detail jadwal")
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(40.dp)
                .verticalScroll(scrollState)
        ) {

            //id dokter read only
            CustomTextField(
                value = idDok.value,
                onValueChange = {
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


            //id jadwal
            CustomTextField(
                value = idJad.value,
                onValueChange = {
                    idJad.value = it
                    isError = it.isEmpty()
                },
                label = "ID jadwal",
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
            //tanggal
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = selectedDate.value,
                    onValueChange = {
                        selectedDate.value = LocalDate.parse(it).toString()
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                val calendar = Calendar.getInstance()
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)

                                val datepicker = DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        selectedDate.value =
                                            LocalDate.of(year, month + 1, dayOfMonth).toString()
                                    },
                                    year, month, day
                                )
                                datepicker.datePicker.minDate = calendar.timeInMillis
                                datepicker.show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = "Pilih tanggal"
                            )
                        }
                    }
                )
            }

            //hari
            CustomExposedDropdown(options = listHari, label = "Pilih hari", errorText = errorText.value, onOptionSelected = {
                harii.value = it
            }, selectedOption = harii.value)
            //jam
            CustomExposedDropdown(options = listJam, label = "Pilih jam", errorText = errorText.value, onOptionSelected = {
                jamm.value = it
            }, selectedOption = jamm.value)

            //status
            CustomExposedDropdown(options = listStatus, label = "Pilih status", errorText = errorText.value, onOptionSelected = {
                statuss.value = it
            }, selectedOption = statuss.value)

            Spacer(modifier = Modifier.weight(1f))
            MyButton(
                onClick = {
                    val jadwalDokter = JadwalDokter(
                        id = idJad.value,
                        idDokter = idDok.value,
                        tanggal = selectedDate.value,
                        hari = harii.value,
                        jam = jamm.value,
                        status = statuss.value
                    )
                    jadwalViewModel.updateJadwal(idDok.value, jadwalDokter, context)
                },
                text = "SIMPAN"
            )
        }
    }

}