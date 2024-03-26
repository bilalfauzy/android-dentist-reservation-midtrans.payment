package com.example.dentalapp.model

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class PaymentResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("transaction_time")
    val waktuTransaksi: String?,
    @SerializedName("gross_amount")
    val biaya: String?,
    @SerializedName("currency")
    val mataUang: String?,
    @SerializedName("order_id")
    val idOrder: String?,
    @SerializedName("payment_type")
    val jenisPembayaran: String?,
    @SerializedName("status_code")
    val statusCode: String?,
    @SerializedName("transaction_id")
    val idTransaksi: String?,
    @SerializedName("transaction_status")
    val status: String?,
    @SerializedName("fraud_status")
    val fraud: String?,
    @SerializedName("expiry_time")
    val expire: String?,
    @SerializedName("settlement_time")
    val settlement: String?,
    @SerializedName("status_message")
    val statusMsg: String?,
    @SerializedName("merchant_id")
    val merchantId: String?,
    @SerializedName("shopeepay_reference_number")
    val shopeepayRef: String?,
    @SerializedName("reference_id")
    val refId: String?,
    @SerializedName("finish_redirect_url")
    val redUrl: String?
)
