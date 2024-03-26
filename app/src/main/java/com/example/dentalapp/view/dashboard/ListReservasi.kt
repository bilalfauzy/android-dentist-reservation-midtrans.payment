package com.example.dentalapp.view.dashboard

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dentalapp.R
import com.example.dentalapp.model.Reservasi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.acceptColor
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.theme.errorColor
import com.example.dentalapp.view.customcomponent.*
import com.example.dentalapp.viewmodel.ReservasiViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ListReservasi(
    navController: NavHostController,
    reservasiViewModel: ReservasiViewModel
){
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email

    reservasiViewModel.getReservasi(emailLogin!!)
    reservasiViewModel.getAllReservasi()
    val reservasiList by reservasiViewModel.reservasiList.collectAsState(emptyList())
    val allReservasi by reservasiViewModel.allRes.collectAsState(emptyList())

    val context = LocalContext.current

    if (emailLogin.isNotEmpty() && emailLogin.equals("admin@gmail.com")){
        Column(
            modifier = Modifier.background(backColor)
        ) {
            MyAppBar(
                title = "History reservasi",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.AdminHomeScreen.route, false)
                }
            )

            AdminListReservasi(
                allReservasi,
                reservasiViewModel,
                navController,
                context,
                onItemClick = {
                    navController.navigate(Screen.DetailReservasiScreen.route+
                            "/${it.namaUser}/${it.namaDokter}/${it.biaya}/${it.jenisPembayaran}/${it.statusPembayaran}/${it.tanggalRes}/${it.hariRes}/${it.jamRes}"
                    )
                }
            )
        }
    }else {
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

}

@Composable
fun MyListReservasi(
    items: List<Reservasi>,
    reservasiViewModel: ReservasiViewModel,
    navController: NavHostController,
    context: Context,
    onItemClick: (Reservasi) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp)
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
                            reservasiViewModel.deleteReservasi(reservasi.idUser!!, reservasi.idRes!!, context)
                            navController.popBackStack(Screen.HomeScreen.route, true)
                            navController.navigate(Screen.ListReservasiScreen.route)
                        },
                        iconResId = R.drawable.baseline_delete_24,
                        description = "batal",
                        color = errorColor
                    )
                }

            }
            CustomDivider()
        }
    }
}

@Composable
fun AdminListReservasi(
    items: List<Reservasi>,
    reservasiViewModel: ReservasiViewModel,
    navController: NavHostController,
    context: Context,
    onItemClick: (Reservasi) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp)
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
                ){
                    Column() {
                        Text(text = "Nama  : ${reservasi.namaUser}")
                        Text(text = "Dokter : ${reservasi.namaDokter}")
                        Text(text = "Biaya : ${reservasi.biaya}")
                        Text(text = "Pembayaran : ${reservasi.jenisPembayaran}")
                        Text(text = "Status : ${reservasi.statusPembayaran}")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if(reservasi.statusPembayaran.equals("sudah dibayar")) {
                        IconButton(
                            modifier = Modifier.clickable {
                                val reservasi = Reservasi(
                                    idRes = reservasi.idRes,
                                    idUser = reservasi.idUser,
                                    namaUser = reservasi.namaUser,
                                    emailUser = reservasi.emailUser,
                                    noWa = reservasi.noWa,
                                    namaDokter = reservasi.namaDokter,
                                    hariRes = reservasi.hariRes,
                                    tanggalRes = reservasi.tanggalRes,
                                    jamRes = reservasi.jamRes,
                                    keluhan = reservasi.keluhan,
                                    biaya = reservasi.biaya,
                                    jenisPembayaran = reservasi.jenisPembayaran,
                                    statusPembayaran = "diterima",
                                    waktuTransaksi = reservasi.waktuTransaksi,
                                    expire = reservasi.expire
                                )

                                reservasiViewModel.updateReservasi(
                                    reservasi.idUser.toString(),
                                    reservasi,
                                    context
                                )
                                navController.popBackStack(Screen.HomeScreen.route, true)
                                navController.navigate(Screen.ListReservasiScreen.route)
                            },
                            iconResId = R.drawable.ic_check_24,
                            description = "terima",
                            color = acceptColor
                        )

                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(
                            modifier = Modifier.clickable {
                                val reservasi = Reservasi(
                                    idRes = reservasi.idRes,
                                    idUser = reservasi.idUser,
                                    namaUser = reservasi.namaUser,
                                    emailUser = reservasi.emailUser,
                                    noWa = reservasi.noWa,
                                    namaDokter = reservasi.namaDokter,
                                    hariRes = reservasi.hariRes,
                                    tanggalRes = reservasi.tanggalRes,
                                    jamRes = reservasi.jamRes,
                                    keluhan = reservasi.keluhan,
                                    biaya = reservasi.biaya,
                                    jenisPembayaran = reservasi.jenisPembayaran,
                                    statusPembayaran = "ditolak",
                                    waktuTransaksi = reservasi.waktuTransaksi,
                                    expire = reservasi.expire
                                )

                                reservasiViewModel.updateReservasi(
                                    reservasi.idUser.toString(),
                                    reservasi,
                                    context
                                )
                                navController.popBackStack(Screen.HomeScreen.route, true)
                                navController.navigate(Screen.ListReservasiScreen.route)
                            },
                            iconResId = R.drawable.ic_cancel_24,
                            description = "tolak",
                            color = errorColor
                        )

                    }else if(reservasi.statusPembayaran.equals("diterima")){
                        IconButton(
                            modifier = Modifier.clickable {
                                val reservasi = Reservasi(
                                    idRes = reservasi.idRes,
                                    idUser = reservasi.idUser,
                                    namaUser = reservasi.namaUser,
                                    emailUser = reservasi.emailUser,
                                    noWa = reservasi.noWa,
                                    namaDokter = reservasi.namaDokter,
                                    hariRes = reservasi.hariRes,
                                    tanggalRes = reservasi.tanggalRes,
                                    jamRes = reservasi.jamRes,
                                    keluhan = reservasi.keluhan,
                                    biaya = reservasi.biaya,
                                    jenisPembayaran = reservasi.jenisPembayaran,
                                    statusPembayaran = "selesai",
                                    waktuTransaksi = reservasi.waktuTransaksi,
                                    expire = reservasi.expire
                                )

                                reservasiViewModel.updateReservasi(
                                    reservasi.idUser.toString(),
                                    reservasi,
                                    context
                                )
                                navController.popBackStack(Screen.HomeScreen.route, true)
                                navController.navigate(Screen.ListReservasiScreen.route)
                            },
                            iconResId = R.drawable.ic_done_all_24,
                            description = "selesai",
                            color = acceptColor
                        )

                    }else if(reservasi.statusPembayaran.equals("belum dibayar")){
                        IconButton(
                            modifier = Modifier.clickable {
                                val reservasi = Reservasi(
                                    idRes = reservasi.idRes,
                                    idUser = reservasi.idUser,
                                    namaUser = reservasi.namaUser,
                                    emailUser = reservasi.emailUser,
                                    noWa = reservasi.noWa,
                                    namaDokter = reservasi.namaDokter,
                                    hariRes = reservasi.hariRes,
                                    tanggalRes = reservasi.tanggalRes,
                                    jamRes = reservasi.jamRes,
                                    keluhan = reservasi.keluhan,
                                    biaya = reservasi.biaya,
                                    jenisPembayaran = reservasi.jenisPembayaran,
                                    statusPembayaran = "ditolak",
                                    waktuTransaksi = reservasi.waktuTransaksi,
                                    expire = reservasi.expire
                                )

                                reservasiViewModel.updateReservasi(
                                    reservasi.idUser.toString(),
                                    reservasi,
                                    context
                                )
                                navController.popBackStack(Screen.HomeScreen.route, true)
                                navController.navigate(Screen.ListReservasiScreen.route)
                            },
                            iconResId = R.drawable.ic_cancel_24,
                            description = "tolak",
                            color = errorColor
                        )
                    }else{
                        CustomSpacer()
                        Text(text = "PESANAN SELESAI")
                    }

                }

            }
            CustomDivider()
        }
    }
}