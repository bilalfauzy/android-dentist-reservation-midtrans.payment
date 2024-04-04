package com.example.dentalapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.dentalapp.model.PaymentResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class TransaksiViewModel : ViewModel() {

    fun jsonParsingTransaksi(
        context: Context,
        orderId: String,
        idTransaksi: MutableState<String>,
        jenisPembayaran: MutableState<String>,
        status: MutableState<String>,
        expire: MutableState<String>,
        waktuTransaksi: MutableState<String>
    ) {

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.sandbox.midtrans.com/v2/${orderId}/status")
            .get()
            .addHeader("accept", "application/json")
            .addHeader(
                "authorization",
                "Basic U0ItTWlkLXNlcnZlci1COS0tb0w2M29FM0NSblFKd09kLWdEbGs6"
            )
            .build()

        client.newCall(request).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Toast.makeText(context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful){
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()

                    val parsed = gson.fromJson(body, PaymentResponse::class.java)

                    val idTrans = parsed.idTransaksi.toString()
                    val jenisPem = parsed.jenisPembayaran.toString()
                    val stat = parsed.status.toString()
                    val exp = parsed.expire.toString()
                    val waktu = parsed.waktuTransaksi.toString()


                    if (stat.equals("settlement")){
                        status.value = "sudah dibayar"
                    }else if(stat.equals("pending")){
                        status.value = "belum dibayar"
                    }

                    idTransaksi.value = idTrans
                    jenisPembayaran.value = jenisPem
                    expire.value = exp
                    waktuTransaksi.value = waktu
                }
            }
        })
    }

}