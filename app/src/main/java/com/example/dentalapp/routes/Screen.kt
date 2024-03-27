package com.example.dentalapp.routes

sealed class Screen(val route: String){
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object ProfileScreen : Screen("profile_screen")
    object ListReservasiScreen : Screen("listreservasi_screen")
    object MemilihTanggalScreen : Screen("memilihtanggal_screen")
    object MelakukanPembayaranScreen : Screen("melakukanpembayaran_screen")
    object BerhasilMembayarScreen : Screen("berhasilmembayar_screen")
    object MemilihDokterScreen : Screen("memilihdokter_screen")
    object MemilihLayananScreen : Screen("memilihlayanan_screen")

    object AdminHomeScreen : Screen("adminhome_screen")

    object ListDokterScreen: Screen("listdokter_screen")

    object DetailDokterScreen: Screen("detaildokter_screen")
    object DetailReservasiScreen: Screen("detailreservasi_screen")

    object DetailJadwalScreen: Screen("detailjadwal_screen")
    object CreateLayananScreen: Screen("createlayanan_screen")
    object ListLayananScreen: Screen("listlayanan_screen")
    object DetailLayananScreen: Screen("detaillayanan_screen")
}
