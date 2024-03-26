package com.example.dentalapp.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalapp.model.Layanan
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

class LayananViewModel : ViewModel() {
    private var db = Firebase.firestore

    private val _layananList = MutableStateFlow<List<Layanan>>(emptyList())
    val layananList: StateFlow<List<Layanan>> = _layananList

    init {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("layanan")
                    .get().await()

                val layananList = snapshot.toObjects<Layanan>()
                _layananList.value = layananList
            } catch (e: Exception) {
                Log.e("LayananViewModel", "Error getting layanan", e)
            }
        }
    }

    fun updateLayanan(layanan: Layanan, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("layanan").document(layanan.idLayanan!!)
                .set(layanan)
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil mengupdate data layanan!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }

    fun deleteLayanan(idLayanan: String, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("layanan").document(idLayanan)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil menghapus data layanan!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Gagal menghapus data layanan!", Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }

    fun uploadImageToCloudFirebase(uri: Uri, context: Context, idLayanan: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("admin/layanan/layanan${uri.lastPathSegment}")

        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val downloadUrlTask = it.storage.downloadUrl
            downloadUrlTask.addOnSuccessListener { downloadUrl ->
                // Gambar telah diunggah, simpan URL gambar ke Firestore
                saveImageProfileToFirestore(downloadUrl.toString(), idLayanan)
            }
        }.addOnFailureListener {
            Toast.makeText(context,"Gagal menambahkan data layanan!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImageProfileToFirestore(imageUrl: String, idLayanan: String) {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            viewModelScope.launch(Dispatchers.IO){
                db.collection("layanan").document(idLayanan)
                    .update("gambar", imageUrl)
                    .await()
            }
        }
    }
}