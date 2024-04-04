package com.example.dentalapp.view.reservasi

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.model.DokterGigi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.CustomDropdownJam
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.DokterGigiViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemilihTanggal(
    navController: NavHostController,
    dokterGigiViewModel: DokterGigiViewModel,
){
    val namaDok = remember {
        mutableStateOf("")
    }

    val namaLayanan = remember {
        mutableStateOf<String?>(null)
    }

    val biayaLayanan = remember {
        mutableStateOf<String?>(null)
    }

    val selectedDate = remember {
        mutableStateOf(LocalDate.now())
    }

    val jam = remember {
        mutableStateOf<String?>(null)
    }

    val listJam = listOf(
        "08.00",
        "08.30",
        "09.00",
        "09.30",
        "10.00",
        "10.30",
        "11.00",
        "11.30",
        "13.00",
        "13.30",
        "14.00",
        "14.30",
    )

    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val disabledTimes =
        if (selectedDate.value == LocalDate.now() ){
            listJam.filter { it < currentTime }
        }else{
            emptySet()
        }

    val keluhan = remember {
        mutableStateOf("")
    }

    var errorText = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    var isError = false


    Column() {
        MyAppBar(
            title = "Memilih tanggal",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
            }
        )

        Column(
            modifier = Modifier
                .background(backColor)
                .fillMaxSize()
                .padding(40.dp)
        ) {

            //MEMILIH TANGGAL
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.8f),
                    value = selectedDate.value.toString(),
                    onValueChange = {
                        selectedDate.value = LocalDate.parse(it)
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
                                        selectedDate.value = LocalDate.of(year, month +1, dayOfMonth)
                                    },
                                    year, month, day
                                )
                                datepicker.datePicker.minDate = calendar.timeInMillis
                                datepicker.show()
                            }
                        ) {
                            Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Pilih tanggal")
                        }
                    }
                )
            }
            
            CustomSpacer()
            //MEMILIH JAM
            CustomDropdownJam(
                options = listJam,
                label = "Pilih waktu",
                errorText = errorText.value,
                onOptionSelected = {
                    jam.value = it
                },
                selectedOption = jam.value,
                disabledItems = disabledTimes.toSet()
            )
            
            if (selectedDate.value.toString().isNotEmpty() && jam.value != null){
                
                val dokterList by dokterGigiViewModel.dokterByDate(selectedDate.value.toString(), jam.value!!).collectAsState(
                    initial = emptyList()
                )
                if (dokterList.isEmpty()){
                    CustomSpacer()
                    Text(text = "Tidak ada dokter tersedia..")
                }else{
                    CustomSpacer()
                    Text(text = "Pilih dokter")
                    LazyListDokter(
                        items = dokterList,
                        onItemClick = {
                            namaDok.value = it.nama.toString()
                        }
                    )
                }
            }

            Text(text = namaDok.value)
            Spacer(modifier = Modifier.weight(1f))
            MyButton(
                onClick = {
                    if (
                        namaDok.value.isEmpty() || jam.value == null ||
                                selectedDate.value.toString().isEmpty()
                    ){
                        errorText.value = "Pastikan semua form terisi!"
                    }else{
                        navController.navigate(Screen.MemilihLayananScreen.route+
                            "/${namaDok.value}/${selectedDate.value}/${selectedDate.value.dayOfWeek}/${jam.value}")
                    }
                },
                text = "LANJUT"
            )

            if (errorText.value.isNotEmpty()){
                Toast.makeText(context, errorText.value, Toast.LENGTH_SHORT).show()
                errorText.value = ""
            }

        }
    }
}

@Composable
fun LazyListDokter(
    items: List<DokterGigi>,
    onItemClick: (DokterGigi) -> Unit
){

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        items(items) { dokter ->
            val isSelected = remember {
                mutableStateOf(false)
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isSelected.value = !isSelected.value
                        onItemClick(dokter)
                    },
                elevation = 2.dp,
                backgroundColor = if (isSelected.value) Color(backColor.value) else Color(backColor.value)
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