package com.example.dentalapp.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Layanan(
    var idLayanan: String? = null,
    var nama: String? = null,
    var biaya: String? = null,
    var deskripsi: String? = null,
    var gambar: String? = null
)
