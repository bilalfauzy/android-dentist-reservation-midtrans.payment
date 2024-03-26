package com.example.dentalapp.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class DokterGigi(
    var id: String? = null,
    var nama: String? = null,
    var gender: String? = null,
    var spesialis: String? = null,
    var umur: String? = null
)
