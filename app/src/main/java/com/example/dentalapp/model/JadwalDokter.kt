package com.example.dentalapp.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class JadwalDokter(
    var id: String? = null,
    var idDokter: String? = null,
    var tanggal: String? = null,
    var hari: String? = null,
    var jam: String? = null,
    var status: String? = null
)