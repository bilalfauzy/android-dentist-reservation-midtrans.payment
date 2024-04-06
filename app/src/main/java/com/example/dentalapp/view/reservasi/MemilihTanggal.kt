package com.example.dentalapp.view.reservasi

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    var errorText = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    var isError = false


    Column() {
        MyAppBar(
            title = "Memilih tanggal",
            navigationIcon = Icons.Outlined.KeyboardArrowLeft,
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
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(top = 10.dp),
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

            Text(
                text = namaDok.value,
                fontWeight = FontWeight.Medium
            )
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
            val imgPainter = rememberAsyncImagePainter(dokter.fotoDokterUrl)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isSelected.value = !isSelected.value
                        onItemClick(dokter)
                    },
                elevation = 2.dp,
                backgroundColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = imgPainter,
                        contentDescription = "foto dokter",
                        modifier = Modifier
                            .size(80.dp, 80.dp)
                            .padding(end = 8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Nama : ${dokter.nama}",
                            fontWeight = FontWeight.Medium
                        )
                        Text(text = "Spesialis : ${dokter.spesialis}")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = "rating",
                            tint = warningColor)
                        Text(text = "${dokter.rating}")
                    }
                }
            }
            CustomSpacer()
        }
    }

}