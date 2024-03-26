package com.example.dentalapp.view.dashboard

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.model.Layanan
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.errorColor
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.DeleteConfirmationDialog
import com.example.dentalapp.view.customcomponent.IconButton
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.LayananViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ListLayanan(
    navController: NavHostController,
    layananViewModel: LayananViewModel
) {
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val listLayanan by layananViewModel.layananList.collectAsState(emptyList())
    val context = LocalContext.current

    if (emailLogin.equals("admin@gmail.com")){
        Column(
            modifier = Modifier.background(backColor)
        ) {
            MyAppBar(
                title = "List layanan",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.AdminHomeScreen.route, false)
                }
            )

            AdminListLayanan(
                listLayanan,
                layananViewModel,
                context,
                navController,
                onItemClick = {
//                    navController.navigate(Screen.DetailLayananScreen.route +
//                            "/${it.idLayanan}/${it.nama}/${it.biaya}/${it.deskripsi}/${it.gambar}")
                    Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }else{
        Column(
            modifier = Modifier.background(backColor)
        ) {
            MyAppBar(
                title = "Layanan klinik",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.HomeScreen.route, false)
                }
            )

            PasienListLayanan(
                items = listLayanan,
                layananViewModel = layananViewModel,
                context = context,
                navController = navController,
                onItemClick = {
                    navController.navigate(Screen.DetailLayananScreen.route +
                            "/${it.nama}/${it.biaya}/${it.deskripsi}/${it.gambar}")
                    Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
                }
            )

        }
    }
}

@Composable
fun AdminListLayanan(
    items: List<Layanan>,
    layananViewModel: LayananViewModel,
    context: Context,
    navController: NavHostController,
    onItemClick: (Layanan) -> Unit
){
    val showDeleteDialog = remember { mutableStateOf(false) }
    val idLayanan = remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                    IconButton(
                        modifier = Modifier.clickable {
                            showDeleteDialog.value = true
                            idLayanan.value = layanan.idLayanan!!
                        },
                        iconResId = R.drawable.baseline_delete_24,
                        description = "hapus",
                        color = errorColor
                    )
                    DeleteConfirmationDialog(
                        showDialog = showDeleteDialog.value,
                        onConfirm = {
                            layananViewModel.deleteLayanan(idLayanan.value, context)
                            navController.popBackStack(Screen.AdminHomeScreen.route, true)
                            navController.navigate(Screen.ListLayananScreen.route)
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

@Composable
fun PasienListLayanan(
    items: List<Layanan>,
    layananViewModel: LayananViewModel,
    context: Context,
    navController: NavHostController,
    onItemClick: (Layanan) -> Unit
){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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