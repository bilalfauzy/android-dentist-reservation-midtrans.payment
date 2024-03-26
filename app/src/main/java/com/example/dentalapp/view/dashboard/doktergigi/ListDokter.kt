package com.example.dentalapp.view.dashboard.doktergigi

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.dentalapp.model.DokterGigi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.errorColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.DeleteConfirmationDialog
import com.example.dentalapp.view.customcomponent.IconButton
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.DokterGigiViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ListDokter(
    navController: NavHostController,
    dokterGigiViewModel: DokterGigiViewModel
){
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val listDokter by dokterGigiViewModel.dokterList.collectAsState(emptyList())
    val context = LocalContext.current

    if (emailLogin.equals("admin@gmail.com")){
        Column(
            modifier = Modifier.background(backColor)
        ) {
            MyAppBar(
                title = "List dokter gigi",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.AdminHomeScreen.route, false)
                }
            )

            MyListDokter(
                listDokter,
                dokterGigiViewModel,
                context,
                navController,
                onItemClick = {
                    navController.navigate(Screen.DetailDokterScreen.route +
                            "/${it.id}/${it.nama}/${it.gender}/${it.spesialis}/${it.umur}")
                }
            )
        }
    }else{
        Column(
            modifier = Modifier.background(backColor)
        ) {
            MyAppBar(
                title = "List dokter gigi",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.HomeScreen.route, false)
                }
            )

            MyListDokter(
                listDokter,
                dokterGigiViewModel,
                context,
                navController,
                onItemClick = {
                    navController.navigate(Screen.DetailDokterScreen.route +
                            "/${it.id}/${it.nama}/${it.gender}/${it.spesialis}/${it.umur}")
                }
            )
        }
    }
}

@Composable
fun MyListDokter(
    items: List<DokterGigi>,
    dokterGigiViewModel: DokterGigiViewModel,
    context: Context,
    navController: NavHostController,
    onItemClick: (DokterGigi) -> Unit
){
    val showDeleteDialog = remember { mutableStateOf(false) }
    val idDokter = remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        items(items) { dokter ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(dokter) }),
                elevation = 2.dp
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
                    IconButton(
                        modifier = Modifier.clickable {
                            showDeleteDialog.value = true
                            idDokter.value = dokter.id!!
                        },
                        iconResId = com.example.dentalapp.R.drawable.baseline_delete_24,
                        description = "hapus",
                        color = errorColor
                    )
                    DeleteConfirmationDialog(
                        showDialog = showDeleteDialog.value,
                        onConfirm = {
                            dokterGigiViewModel.deleteDokter(idDokter.value, context)
                            navController.popBackStack(Screen.AdminHomeScreen.route, true)
                            navController.navigate(Screen.ListDokterScreen.route)
                        },
                        onDismiss = {
                            showDeleteDialog.value = false
                        }
                    )

                }
            }
            CustomDivider()
        }
    }

}