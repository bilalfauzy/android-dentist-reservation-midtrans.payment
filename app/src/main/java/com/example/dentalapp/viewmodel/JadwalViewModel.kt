package com.example.dentalapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalapp.model.JadwalDokter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class JadwalViewModel : ViewModel() {
    private var db = Firebase.firestore

    private val _jadwalList = MutableStateFlow<List<JadwalDokter>>(emptyList())
    val jadwalList: StateFlow<List<JadwalDokter>> = _jadwalList

    fun getJadwalDokter(idDokter: String){
        viewModelScope.launch(Dispatchers.IO){
            val snapshot = db.collectionGroup("jadwal")
                .whereEqualTo("idDokter", idDokter)
                .get()
                .await()

            val listJadwal = snapshot.toObjects(JadwalDokter::class.java)
            _jadwalList.value = listJadwal
        }
    }
    
    fun updateJadwal(idDokter: String, jadwalDokter: JadwalDokter, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("dokter")
                .document(idDokter)
                .collection("jadwal")
                .document(jadwalDokter.id!!)
                .set(jadwalDokter)
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil mengupdate jadwal!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }


    fun deleteJadwal(idDokter: String, idJadwal: String, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("dokter")
                .document(idDokter)
                .collection("jadwal")
                .document(idJadwal)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil menghapus jadwal!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }
}