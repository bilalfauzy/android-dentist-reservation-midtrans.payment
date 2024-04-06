package com.example.dentalapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalapp.model.Layanan
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

                val layananList = snapshot.toObjects(Layanan::class.java)
                _layananList.value = layananList
            } catch (e: Exception) {
                Log.e("LayananViewModel", "Error getting layanan", e)
            }
        }
    }

    fun getLayananByDokter(namaDokter: String){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("dokter")
                .whereEqualTo("nama", namaDokter)
                .get()
                .addOnSuccessListener{nama ->
                    for (doc in nama){
                        val idDok = doc.id
                        db.collectionGroup("layanan")
                            .whereEqualTo("idDokter", idDok)
                            .get()
                            .addOnSuccessListener{
                                val servicesList = mutableListOf<Layanan>()
                                for (layanan in it){
                                    val layananData = layanan.toObject(Layanan::class.java)
                                    servicesList.add(layananData)
                                }
                                _layananList.value = servicesList
                            }
                    }
                }
                .await()

        }
    }

}