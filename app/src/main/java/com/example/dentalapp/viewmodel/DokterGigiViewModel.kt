package com.example.dentalapp.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentalapp.model.DokterGigi
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DokterGigiViewModel : ViewModel() {
    private var db = Firebase.firestore

    private val _dokterList = MutableStateFlow<List<DokterGigi>>(emptyList())
    val dokterList: StateFlow<List<DokterGigi>> = _dokterList

    init {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("dokter")
                    .get().await()

                val dokterList = snapshot.toObjects<DokterGigi>()
                _dokterList.value = dokterList
            } catch (e: Exception) {
                Log.e("DokterViewModel", "Error getting dokter", e)
            }
        }
    }
    fun dokterById(idDokter: String){
        viewModelScope.launch(Dispatchers.IO){
            val snapshot = db.collection("dokter")
                .get().await()
            val dokterList = snapshot.toObjects(DokterGigi::class.java)
            _dokterList.value = dokterList
        }
    }

    fun updateDokter(dokterGigi: DokterGigi, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("dokter").document(dokterGigi.id!!)
                .set(dokterGigi)
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil mengupdate data dokter!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }

    fun dokterByDate(date: String, jam: String): Flow<List<DokterGigi>> {

        return callbackFlow {
            val availableDokter = mutableListOf<DokterGigi>()
            db.collectionGroup("jadwal")
                .whereEqualTo("tanggal", date)
                .whereEqualTo("jam", jam)
                .get()
                .addOnSuccessListener {
                    val availableDokterId = mutableSetOf<String>()
                    for (jadwalDoc in it.documents){
                        val dokterId = jadwalDoc.reference.parent.parent?.id
                        dokterId?.let {
                            availableDokterId.add(it)
                        }
                    }

                    if (availableDokterId.isNotEmpty()){
                        db.collection("dokter")
                            .whereIn(FieldPath.documentId(), availableDokterId.toList())
                            .get()
                            .addOnSuccessListener {
                                for (dokterDoc in it.documents){
                                    val dokter = dokterDoc.toObject(DokterGigi::class.java)
                                    dokter?.let {
                                        availableDokter.add(it)
                                    }
                                }
                                trySend(availableDokter).isSuccess
                            }
                            .addOnFailureListener {
                                Log.e(ContentValues.TAG, "error", it)
                            }
                    }else{
                        trySend(availableDokter).isSuccess
                    }
                }
                .addOnFailureListener{
                    Log.e(ContentValues.TAG, "eroor cok", it)
                }
            awaitClose()
        }
    }

}