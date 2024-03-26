package com.example.dentalapp.view.reservasi

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dentalapp.midtrans.MidtransConfig
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder


@SuppressLint("RememberReturnType")
@Composable
fun MelakukanPembayaran(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    namaDok: String,
    tanggal: String,
    hari: String,
    jam: String,
    keluhan: String
){
    val context = LocalContext.current
    val activity = (context as? Activity)
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    usersViewModel.getUserLogin(emailLogin!!)
    val userLogin by usersViewModel.userLogin.collectAsState(emptyList())

    val namaList = userLogin.map {
        it.nama.toString()
    }

    val emailList = userLogin.map {
        it.email.toString()
    }

    val noWaList = userLogin.map {
        it.noWa.toString()
    }

    val nama = namaList.joinToString()
    val email = emailList.joinToString()
    val noWa = noWaList.joinToString()

    SdkUIFlowBuilder.init()
        .setClientKey(MidtransConfig.CLIENT_KEY) // client_key is mandatory
        .setContext(context) // context is mandatory
        .setTransactionFinishedCallback(
        TransactionFinishedCallback {

        }
        ) // set transaction finish callback (sdk callback)
        .setMerchantBaseUrl(MidtransConfig.SERVER) //set merchant url (required)
        .enableLog(true) // enable sdk log (optional)
        .setColorTheme(CustomColorTheme("#FF03A9F4", "#FF03A9F4", "#FF000000")) // set theme. it will replace theme on snap theme on MAP ( optional)
        .setLanguage("id") //`en` for English and `id` for Bahasa
        .buildSDK()



    Column {
        val biaya = 50000.00


        MyAppBar(
            title = "Pembayaran",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.MemilihTanggalScreen.route, false)
            }
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(20.dp)
        ){

            Text(text = "Detail Reservasi :")
            CustomSpacer()

            Text(text = "Nama : ${nama}")
            Text(text = "Email : ${email}")
            Text(text = "Nomor wa : ${noWa}")
            Text(text = "Nama dokter : ${namaDok}")
            Text(text = "Tanggal : ${hari}/${tanggal}")
            Text(text = "Jam : ${jam}")
            Text(text = "Keluhan/penyakit : ${keluhan}")
            Text(text = "Biaya total : ${biaya}")
            CustomSpacer()
            Text(text = "Metode pembayaran : ")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    painterResource(id = com.midtrans.sdk.uikit.R.drawable.ic_bank_bca_40),
                    contentDescription = "bca",
                    Modifier.size(60.dp)
                )
                Icon(
                    painterResource(id = com.midtrans.sdk.uikit.R.drawable.ic_bank_mandiri_40),
                    contentDescription = "mandiri",
                    Modifier.size(60.dp)
                )

                Icon(
                    painterResource(id = com.midtrans.sdk.uikit.R.drawable.ic_bank_bni_40),
                    contentDescription = "bni",
                    Modifier.size(60.dp)
                )
                Icon(
                    painterResource(id = com.midtrans.sdk.uikit.R.drawable.ic_bank_bri_40),
                    contentDescription = "bri",
                    Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            MyButton(onClick = {
                val orderId = "Pesanan${System.currentTimeMillis().toShort()}"

                val transactionRequest = TransactionRequest(orderId, biaya)
                val details = ItemDetails("ID-${namaDok}", biaya, 1, namaDok)
                val itemDetails = ArrayList<ItemDetails>()
                itemDetails.add(details)
                customerDetail(
                    transactionRequest,
                    nama,
                    email,
                    noWa
                )
                transactionRequest.itemDetails = itemDetails
                MidtransSDK.getInstance().transactionRequest = transactionRequest
                MidtransSDK.getInstance().startPaymentUiFlow(
                    context
                )
                navController.navigate(Screen.BerhasilMembayarScreen.route +
                        "/${orderId}/${namaDok}/${tanggal}/${hari}/${jam}/${keluhan}")
            }, text = "BAYAR")

        }
    }
}

fun customerDetail(
    transactionRequest: TransactionRequest,
    namaUser: String,
    email: String,
    noWa: String
){
    val customerDetails = CustomerDetails()
    customerDetails.customerIdentifier = namaUser
    customerDetails.phone = noWa
    customerDetails.firstName = namaUser
    customerDetails.email = email

    val shippingAddress = ShippingAddress()
    shippingAddress.address = null
    shippingAddress.city = null
    shippingAddress.postalCode = null

    customerDetails.shippingAddress = shippingAddress

    val billingAddress = BillingAddress()
    billingAddress.address = null
    billingAddress.city = null
    billingAddress.postalCode = null

    customerDetails.billingAddress = billingAddress

    transactionRequest.customerDetails = customerDetails

}
