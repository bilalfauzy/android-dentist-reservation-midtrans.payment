package com.example.dentalapp.view.reservasi

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.model.Reservasi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.errorColor
import com.example.dentalapp.view.customcomponent.ConfirmationDialog
import com.example.dentalapp.view.customcomponent.CustomDivider
import com.example.dentalapp.view.customcomponent.IconButton
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.viewmodel.RefreshViewModel
import com.example.dentalapp.viewmodel.ReservasiViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun ListReservasi(
    navController: NavHostController,
    reservasiViewModel: ReservasiViewModel,
    refreshViewModel: RefreshViewModel
){
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email

    reservasiViewModel.getReservasi(emailLogin!!)
    reservasiViewModel.getAllReservasi()
    val reservasiList by reservasiViewModel.reservasiList.collectAsState(emptyList())
    val allReservasi by reservasiViewModel.allRes.collectAsState(emptyList())

    val context = LocalContext.current
    LaunchedEffect(refreshViewModel.needRefresh.value) {
        if (refreshViewModel.needRefresh.value) {
            delay(1000)
            refreshViewModel.resetRefreshStatus()
        }
    }

    Column(
        modifier = Modifier.background(backColor)
    ) {
        MyAppBar(
            title = "History reservasi",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
            }
        )
        MyListReservasi(
            reservasiList,
            reservasiViewModel,
            refreshViewModel,
            navController,
            context,
            onItemClick = {
                navController.navigate(Screen.DetailReservasiScreen.route+
                        "/${it.namaUser}/${it.namaDokter}/${it.biaya}/${it.jenisPembayaran}/${it.statusPembayaran}/${it.tanggalRes}/${it.hariRes}/${it.jamRes}"
                )
            }
        )
}

}

@Composable
fun MyListReservasi(
    items: List<Reservasi>,
    reservasiViewModel: ReservasiViewModel,
    refreshViewModel: RefreshViewModel,
    navController: NavHostController,
    context: Context,
    onItemClick: (Reservasi) -> Unit
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val idRes = remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        items(items) { reservasi ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(reservasi) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(){
                        Text(text = "Nama  : ${reservasi.namaUser}")
                        Text(text = "Dokter : ${reservasi.namaDokter}")
                        Text(text = "Biaya : ${reservasi.biaya}")
                        Text(text = "Pembayaran : ${reservasi.jenisPembayaran}")
                        Text(text = "Status : ${reservasi.statusPembayaran}")
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.clickable {
                            showDeleteDialog.value = true
                            idRes.value = reservasi.idRes!!

                        },
                        iconResId = R.drawable.baseline_delete_24,
                        description = "batal",
                        color = errorColor
                    )
                    ConfirmationDialog(
                        showDialog = showDeleteDialog.value,
                        alertText = "Apakah yakin ingin membatalkan reservasi?",
                        onConfirm = {
                            reservasiViewModel.deleteReservasi(reservasi.idUser!!, reservasi.idRes!!, context)
                            refreshViewModel.refreshPage()
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
