package com.example.dentalapp.view.reservasi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.model.PaymentResponse
import com.example.dentalapp.model.Reservasi
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomCard
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.ReservasiViewModel
import com.example.dentalapp.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.UUID


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("RememberReturnType")
@Composable
fun MelakukanPembayaran(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    reservasiViewModel: ReservasiViewModel,
    namaDok: String,
    tanggal: String,
    hari: String,
    jam: String,
    keluhan: String
){
    val context = LocalContext.current
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    usersViewModel.getUserLogin(emailLogin!!)
    val userLogin by usersViewModel.userLogin.collectAsState(emptyList())
    val idRes = UUID.randomUUID().toString()

    val idUser = userLogin.map {
        it.idUser.toString()
    }.joinToString()
    val namaPemesan = userLogin.map {
        it.nama.toString()
    }.joinToString()
    val email = userLogin.map {
        it.email.toString()
    }.joinToString()
    val noWa = userLogin.map {
        it.noWa.toString()
    }.joinToString()
    val biaya = 50000.00
    val idTransaksi = remember {
        mutableStateOf("")
    }
    val jenisPembayaran = remember {
        mutableStateOf("")
    }
    val statusPembayaran = remember {
        mutableStateOf("")
    }
    val expire = remember {
        mutableStateOf("")
    }
    val waktuTransaksi = remember {
        mutableStateOf("")
    }
    val token = remember {
        mutableStateOf("")
    }
    val idPesanan = remember {
        mutableStateOf("")
    }
    val transactionResult = remember { mutableStateOf<TransactionResult?>(null) }

    UiKitApi.Builder()
        .withMerchantClientKey("SB-Mid-client-mX47J54iGwdG1tVi")
        .withContext(context)
        .withMerchantUrl("http://192.168.100.4:8080/api/")
        .enableLog(true)
        .withColorTheme(
            com.midtrans.sdk.uikit.api.model.CustomColorTheme(
                "#FF03A9F4",
                "#FF03A9F4",
                "#FF000000"
            )
        )
        .build()

    //callback
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                transactionResult.value = it.getParcelableExtra(UiKitConstants.KEY_TRANSACTION_RESULT)

            }
            if (transactionResult.value != null){
                when(transactionResult.value!!.status){
                    "success"-> {
                        Toast.makeText(context, "Transaction Finished. ID: " + transactionResult.value!!.transactionId, Toast.LENGTH_LONG).show()
                    }
                    "pending"-> {
                        Toast.makeText(context, "Transaction Pending. ID: " + transactionResult.value!!.transactionId, Toast.LENGTH_LONG).show()
                    }
                    "failed"-> {
                        Toast.makeText(context, "Transaction Failed. ID: " + transactionResult.value!!.transactionId, Toast.LENGTH_LONG).show()
                    }
                    "canceled"-> {
                        Toast.makeText(context, "Transaction Canceled. ID: " + transactionResult.value!!.transactionId, Toast.LENGTH_LONG).show()
                    }
                }
            }
            getPayment(
                context, idRes, idUser, idPesanan.value, idTransaksi, namaPemesan, email, noWa, namaDok, hari, tanggal, jam, keluhan, biaya, jenisPembayaran, statusPembayaran, waktuTransaksi, expire, reservasiViewModel
            )
        }
    }



    if (transactionResult.value == null) {
        Column {
            MyAppBar(
                title = "Detail Reservasi",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.MemilihTanggalScreen.route, false)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(20.dp)
            ) {
                Text(text = "Detail Reservasi :")
                CustomSpacer()
                CustomCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Nama : ${namaPemesan}")
                    Text(text = "Email : ${email}")
                    Text(text = "Nomor wa : ${noWa}")
                    Text(text = "Nama dokter : ${namaDok}")
                    Text(text = "Tanggal : ${hari}/${tanggal}")
                    Text(text = "Jam : ${jam}")
                    Text(text = "Keluhan/penyakit : ${keluhan}")
                    Text(text = "Biaya reservasi : ${biaya}")
                }

                Spacer(modifier = Modifier.weight(1f))
                MyButton(onClick = {
                    idPesanan.value = "Pesanan${System.currentTimeMillis().toShort()}"

                    startPayment(
                        context, idPesanan.value, namaPemesan, biaya, email, noWa, token, launcher
                    )

                }, text = "BAYAR")

            }
        }
    } else {
        Column {
            MyAppBar(
                title = "Berhasil",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.MemilihTanggalScreen.route, false)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(20.dp)
            ) {

                Text(text = "BERHASIL MEMBUAT RESERVASI")

                Spacer(modifier = Modifier.weight(1f))
                MyButton(
                    onClick = {
                        navController.popBackStack(Screen.HomeScreen.route, false)
                    },
                    text = "OK")
            }
        }

    }
}

fun startPayment(
    context: Context,
    idPesanan: String,
    namaPemesan: String,
    biaya: Double,
    email: String,
    noWa: String,
    token: MutableState<String>,
    launcher: ActivityResultLauncher<Intent>
){
    val paymentData = JSONObject().apply {
        put("idPesanan", idPesanan)
        put("namaPemesan", namaPemesan)
        put("biaya", biaya)
        put("email", email)
        put("noWa", noWa)
    }

    val client = OkHttpClient()
    val requestBody =
        paymentData.toString().toRequestBody("application/json".toMediaTypeOrNull())
    val request = Request.Builder()
        .url("http://192.168.100.4:8080/api/create-payment")
        .post(requestBody)
        .addHeader("accept", "application/json")
        .addHeader("content-type", "application/json")
        .addHeader(
            "authorization",
            "Basic U0ItTWlkLXNlcnZlci1COS0tb0w2M29FM0NSblFKd09kLWdEbGs6"
        )
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("gg", e.message.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            val responseBody = response.body?.string()
            if (response.isSuccessful && responseBody != null) {
                val jsonResponse = JSONObject(responseBody)
                token.value = jsonResponse.getString("token")

                // Mulai proses pembayaran menggunakan Midtrans SDK
                UiKitApi.getDefaultInstance().startPaymentUiFlow(
                    context as Activity,
                    launcher,
                    token.value,
                )

                Log.e("ppp", token.value)
            } else {
                Log.e("gg", "gagal mendapat token")
            }
        }
    })

}

fun getPayment(
    context: Context,
    idRes: String,
    idUser: String,
    idPesanan: String,
    idTransaksi: MutableState<String>,
    namaPemesan: String,
    email: String,
    noWa: String,
    namaDok: String,
    hari: String,
    tanggal: String,
    jam: String,
    keluhan: String,
    biaya: Double,
    jenisPembayaran: MutableState<String>,
    statusPembayaran: MutableState<String>,
    waktuTransaksi: MutableState<String>,
    expire: MutableState<String>,
    reservasiViewModel: ReservasiViewModel
){
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.sandbox.midtrans.com/v2/${idPesanan}/status")
        .get()
        .addHeader("accept", "application/json")
        .addHeader("content-type", "application/json")
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

                idTransaksi.value = parsed.idTransaksi.toString()
                jenisPembayaran.value = parsed.jenisPembayaran.toString()
                statusPembayaran.value = parsed.status.toString()
                expire.value = parsed.expire.toString()
                waktuTransaksi.value = parsed.waktuTransaksi.toString()

                if (statusPembayaran.equals("settlement")){
                    statusPembayaran.value = "sudah dibayar"
                }else if(statusPembayaran.equals("pending")){
                    statusPembayaran.value = "belum dibayar"
                }
                val reservasi = Reservasi(
                    idRes = idRes,
                    idUser = idUser,
                    idPesanan = idPesanan,
                    namaUser = namaPemesan,
                    emailUser = email,
                    noWa = noWa,
                    namaDokter = namaDok,
                    hariRes = hari,
                    tanggalRes = tanggal,
                    jamRes = jam,
                    keluhan = keluhan,
                    biaya = biaya,
                    jenisPembayaran = jenisPembayaran.value,
                    statusPembayaran = statusPembayaran.value,
                    waktuTransaksi = waktuTransaksi.value,
                    expire = expire.value
                )
                reservasiViewModel.createReservasi(idUser, reservasi, context)
            }
        }
    })
}

