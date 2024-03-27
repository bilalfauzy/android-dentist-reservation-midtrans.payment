package com.example.dentalapp.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dentalapp.view.dashboard.Home
import com.example.dentalapp.view.dashboard.Profile
import com.example.dentalapp.view.dashboard.doktergigi.DetailDokter
import com.example.dentalapp.view.dashboard.doktergigi.DetailJadwal
import com.example.dentalapp.view.dashboard.doktergigi.DetailLayanan
import com.example.dentalapp.view.dashboard.doktergigi.ListDokter
import com.example.dentalapp.view.dashboard.doktergigi.ListLayanan
import com.example.dentalapp.view.loginregister.Login
import com.example.dentalapp.view.loginregister.Register
import com.example.dentalapp.view.reservasi.BerhasilMembayar
import com.example.dentalapp.view.reservasi.DetailReservasi
import com.example.dentalapp.view.reservasi.ListReservasi
import com.example.dentalapp.view.reservasi.MelakukanPembayaran
import com.example.dentalapp.view.reservasi.MemilihTanggal
import com.example.dentalapp.viewmodel.DokterGigiViewModel
import com.example.dentalapp.viewmodel.JadwalViewModel
import com.example.dentalapp.viewmodel.LayananViewModel
import com.example.dentalapp.viewmodel.RefreshViewModel
import com.example.dentalapp.viewmodel.ReservasiViewModel
import com.example.dentalapp.viewmodel.UsersViewModel
import com.example.dentalapp.viewmodel.loginregister.LoginViewModel
import com.example.dentalapp.viewmodel.loginregister.RegisterViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(){
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(
            route = Screen.LoginScreen.route
        ){
            Login(
                navController = navController,
                loginViewModel = LoginViewModel(),
                usersViewModel = UsersViewModel()
            )
        }

        composable(
            route = Screen.HomeScreen.route
        ){
            Home(
                navController = navController,
                usersViewModel = UsersViewModel()
            )
        }
        
        composable(route = Screen.RegisterScreen.route){
            Register(navController = navController ,registerViewModel = RegisterViewModel())
        }

        composable(route = Screen.ProfileScreen.route){
            Profile(
                navController = navController,
                usersViewModel = UsersViewModel()
            )
        }

        composable(route = Screen.ListReservasiScreen.route){
            ListReservasi(
                navController = navController,
                reservasiViewModel = ReservasiViewModel(),
                refreshViewModel = RefreshViewModel()
            )
        }

        composable(
            route = Screen.MemilihTanggalScreen.route
        ){
            MemilihTanggal(
                navController = navController,
                dokterGigiViewModel = DokterGigiViewModel(),
                layananViewModel = LayananViewModel()
            )
        }

        composable(route = Screen.MelakukanPembayaranScreen.route + "/{nama}/{tanggal}/{hari}/{jam}/{keluhan}",
            arguments = listOf(
                navArgument("nama"){
                    type = NavType.StringType
                },
                navArgument("tanggal"){
                    type = NavType.StringType
                },
                navArgument("hari"){
                    type = NavType.StringType
                },
                navArgument("jam"){
                    type = NavType.StringType
                },
                navArgument("keluhan"){
                    type = NavType.StringType
                }
            )
        ){
            val nama = it.arguments?.getString("nama")!!
            val tanggal = it.arguments?.getString("tanggal")!!
            val hari = it.arguments?.getString("hari")!!
            val jam = it.arguments?.getString("jam")!!
            val keluhan = it.arguments?.getString("keluhan")!!
            MelakukanPembayaran(
                navController = navController,
                usersViewModel = UsersViewModel(),
                layananViewModel = LayananViewModel(),
                namaDok = nama,
                tanggal = tanggal,
                hari = hari,
                jam = jam,
                keluhan = keluhan
            )
        }

        composable(
            route = Screen.BerhasilMembayarScreen.route +
                    "/{orderId}/{nama}/{tanggal}/{hari}/{jam}/{keluhan}",
            arguments = listOf(
                navArgument("orderId"){
                    type = NavType.StringType
                },
                navArgument("nama"){
                    type = NavType.StringType
                },
                navArgument("tanggal"){
                    type = NavType.StringType
                },
                navArgument("hari"){
                    type = NavType.StringType
                },
                navArgument("jam"){
                    type = NavType.StringType
                },
                navArgument("keluhan"){
                    type = NavType.StringType
                },
            )
        ){
            val orderId = it.arguments?.getString("orderId")!!
            val nama = it.arguments?.getString("nama")!!
            val tanggal = it.arguments?.getString("tanggal")!!
            val hari = it.arguments?.getString("hari")!!
            val jam = it.arguments?.getString("jam")!!
            val keluhan = it.arguments?.getString("keluhan")!!
            BerhasilMembayar(
                navController = navController,
                reservasiViewModel = ReservasiViewModel(),
                usersViewModel = UsersViewModel(),
                orderId = orderId,
                namaDok = nama,
                tanggal = tanggal,
                hari = hari,
                jam = jam,
                keluhan = keluhan
            )
        }


        composable(
            route = Screen.ListDokterScreen.route
        ){
            ListDokter(
                navController = navController,
                dokterGigiViewModel = DokterGigiViewModel()
            )
        }

        composable(
            route = Screen.DetailDokterScreen.route +
                    "/{idDokter}/{nama}/{gender}/{spesialis}/{umur}",
            arguments = listOf(
                navArgument("idDokter"){
                    type = NavType.StringType
                },
                navArgument("nama"){
                    type = NavType.StringType
                },
                navArgument("gender"){
                    type = NavType.StringType
                },
                navArgument("spesialis"){
                    type = NavType.StringType
                },
                navArgument("umur"){
                    type = NavType.StringType
                },
            )
        ){
            val idDokter = it.arguments?.getString("idDokter")!!
            val nama = it.arguments?.getString("nama")!!
            val gender = it.arguments?.getString("gender")!!
            val spesialis = it.arguments?.getString("spesialis")!!
            val umur = it.arguments?.getString("umur")!!
            DetailDokter(
                navController = navController,
                jadwalViewModel = JadwalViewModel(),
                dokterGigiViewModel = DokterGigiViewModel(),
                idDokter,
                nama,
                gender,
                spesialis,
                umur
            )
        }

        composable(
            route = Screen.DetailReservasiScreen.route +
                    "/{namaUser}/{namaDokter}/{biaya}/{pembayaran}/{status}/{tanggal}/{hari}/{jam}",
            arguments = listOf(
                navArgument("namaUser"){
                    type = NavType.StringType
                },
                navArgument("namaDokter"){
                    type = NavType.StringType
                },
                navArgument("biaya"){
                    type = NavType.StringType
                },
                navArgument("pembayaran"){
                    type = NavType.StringType
                },
                navArgument("status"){
                    type = NavType.StringType
                },
                navArgument("tanggal"){
                    type = NavType.StringType
                },
                navArgument("hari"){
                    type = NavType.StringType
                },
                navArgument("jam"){
                    type = NavType.StringType
                },
            )
        ){
            val namaUser = it.arguments?.getString("namaUser")!!
            val namaDokter = it.arguments?.getString("namaDokter")!!
            val biaya = it.arguments?.getString("biaya")!!
            val pembayaran = it.arguments?.getString("pembayaran")!!
            val status = it.arguments?.getString("status")!!
            val tanggal = it.arguments?.getString("tanggal")!!
            val hari = it.arguments?.getString("hari")!!
            val jam = it.arguments?.getString("jam")!!
            DetailReservasi(
                navController = navController,
                reservasiViewModel = ReservasiViewModel(),
                namaUser = namaUser,
                namaDokter = namaDokter,
                biaya = biaya,
                pembayaran = pembayaran,
                status = status,
                tanggal,
                hari,
                jam
            )
        }

        composable(
            route = Screen.DetailJadwalScreen.route +
                    "/{idJadwal}/{idDokter}/{hari}/{jam}/{tanggal}/{status}",
            arguments = listOf(
                navArgument("idJadwal"){
                    type = NavType.StringType
                },
                navArgument("idDokter"){
                    type = NavType.StringType
                },
                navArgument("hari"){
                    type = NavType.StringType
                },
                navArgument("jam"){
                    type = NavType.StringType
                },
                navArgument("tanggal"){
                    type = NavType.StringType
                },
                navArgument("status"){
                    type = NavType.StringType
                },
            )
        ){
            val idJadwal = it.arguments?.getString("idJadwal")!!
            val idDokter = it.arguments?.getString("idDokter")!!
            val hari = it.arguments?.getString("hari")!!
            val jam = it.arguments?.getString("jam")!!
            val tanggal = it.arguments?.getString("tanggal")!!
            val status = it.arguments?.getString("status")!!
            DetailJadwal(
                navController = navController,
                jadwalViewModel = JadwalViewModel(),
                idJadwal,
                idDokter,
                hari,
                jam,
                tanggal,
                status
            )
        }

        composable(route = Screen.ListLayananScreen.route){
            ListLayanan(
                navController = navController,
                layananViewModel = LayananViewModel()
            )
        }

        composable(
            route = Screen.DetailLayananScreen.route +
                    "/{idLayanan}/{nama}/{biaya}/{deskripsi}/{gambar}",
            arguments = listOf(
                navArgument("idLayanan"){
                    type = NavType.StringType
                },
                navArgument("nama"){
                    type = NavType.StringType
                },
                navArgument("biaya"){
                    type = NavType.StringType
                },
                navArgument("deskripsi"){
                    type = NavType.StringType
                },
                navArgument("gambar"){
                    type = NavType.StringType
                },
            )
        ){
            val idLayanan = it.arguments?.getString("idLayanan")!!
            val nama = it.arguments?.getString("nama")!!
            val biaya = it.arguments?.getString("biaya")!!
            val deskripsi = it.arguments?.getString("deskripsi")!!
            val gambar = it.arguments?.getString("gambar")!!
            DetailLayanan(
                navController = navController, LayananViewModel(), idLayanan , nama, biaya, deskripsi, gambar
            )
        }

    }
}