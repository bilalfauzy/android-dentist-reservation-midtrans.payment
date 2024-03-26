package com.example.dentalapp.model

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class Users(
    var idUser: String? = null,
    var nama: String? = null,
    var umur: Int = 0,
    var gender: String? = null,
    var noWa: String? = null,
    var email: String? = null,
    var password: String? = null,
    var fotoProfilUrl: String? = null
)
