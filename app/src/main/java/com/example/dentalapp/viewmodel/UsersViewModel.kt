package com.example.dentalapp.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalapp.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel : ViewModel() {
    private var db = Firebase.firestore

    private val _userLogin = MutableStateFlow<List<Users>>(emptyList())
    val userLogin: StateFlow<List<Users>> = _userLogin

    private val _allUser = MutableStateFlow<List<Users>>(emptyList())
    val allUser: StateFlow<List<Users>> = _allUser

    fun getUserLogin(emailUser: String) {

        if (emailUser.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val snapshot = db.collection("users")
                        .whereEqualTo("email", emailUser)
                        .get()
                        .await()
                    val user = snapshot.toObjects<Users>()
                    _userLogin.value = user
                } catch (e: Exception) {  
                    Log.e("UserLogin", "Gagal mengambil user login", e)
                }
            }
        }

    }
    fun getUserEmailPass(emailUser: String, passUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = db.collection("users")
                    .whereEqualTo("email", emailUser)
                    .whereEqualTo("password", passUser)
                    .get().await()
                val users = snapshot.toObjects(Users::class.java)
                _allUser.value = users
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error getting users", e)
            }

        }
    }
    fun uploadImageToCloudFirebase(uri: Uri, context: Context, namaUser: String, idUser: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("fotoProfil/${namaUser}/${namaUser}${uri.lastPathSegment}")

        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val downloadUrlTask = it.storage.downloadUrl
            downloadUrlTask.addOnSuccessListener { downloadUrl ->
                // Gambar telah diunggah, simpan URL gambar ke Firestore
                val updatedUserList = _userLogin.value.map {
                    if (it.idUser == idUser){
                        it.copy(fotoProfilUrl = downloadUrl.toString())
                    }else{
                        it
                    }
                }
                _userLogin.value = updatedUserList
                saveImageProfileToFirestore(downloadUrl.toString(), idUser)
                Toast.makeText(context,"Berhasil mengganti profil", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context,"Gagal mengganti profil", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImageProfileToFirestore(imageUrl: String, idUser: String) {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            viewModelScope.launch(Dispatchers.IO){
                db.collection("users").document(idUser)
                    .update("fotoProfilUrl", imageUrl)
                    .await()
            }
        }
    }
}