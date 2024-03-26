package com.example.dentalapp.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Reservasi(
    var idRes: String? = null,
    var idUser: String? = null,
    var namaUser: String? = null,
    var emailUser: String? = null,
    var noWa: String? = null,
    var namaDokter : String? = null,
    var hariRes: String? = null,
    var tanggalRes: String? = null,
    var jamRes: String? = null,
    var keluhan : String? = null,
    var biaya: Double? = null,
    var jenisPembayaran: String? = null,
    var statusPembayaran: String? = null,
    var waktuTransaksi : String? = null,
    var expire: String? = null
)