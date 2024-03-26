package com.example.dentalapp.view.dashboard

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.dentalapp.R
import com.example.dentalapp.model.Layanan
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.CustomTextField
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.LayananViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DetailLayanan(
    navController: NavHostController,
    layananViewModel: LayananViewModel,
    id: String,
    nama: String,
    biaya: String,
    deskripsi: String,
    gambar: String
) {
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val context = LocalContext.current
    val showDeleteDialog = remember { mutableStateOf(false) }

    val idLayanan = remember {
        mutableStateOf(id)
    }
    val namaLayanan = remember {
        mutableStateOf(nama)
    }
    val biayaLayanan = remember {
        mutableStateOf(biaya)
    }
    val deskripsiLayanan = remember {
        mutableStateOf(deskripsi)
    }
    val gambarLayanan = remember {
        mutableStateOf(gambar)
    }
    var isError = false

    val scrollState = rememberScrollState()
    var errorText = remember {
        mutableStateOf("")
    }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    if (emailLogin!!.isNotEmpty() && emailLogin.equals("admin@gmail.com")) {

        Column() {
            MyAppBar(
                title = "Detail dokter",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.ListLayananScreen.route, false)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(40.dp)

            ) {
                //id layanan
                CustomTextField(
                    value = idLayanan.value,
                    onValueChange = {
                        idLayanan.value = it
                        isError = it.isEmpty()
                    },
                    label = "ID layanan",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "ID",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //nama layanan
                CustomTextField(
                    value = namaLayanan.value,
                    onValueChange = {
                        namaLayanan.value = it
                        isError = it.isEmpty()
                    },
                    label = "Nama layanan",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "ID",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //biaya layanan
                CustomTextField(
                    value = biayaLayanan.value,
                    onValueChange = {
                        biayaLayanan.value = it
                        isError = it.isEmpty()
                    },
                    label = "Biaya layanan",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "ID",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //deskripsi layanan
                CustomTextField(
                    value = deskripsiLayanan.value,
                    onValueChange = {
                        deskripsiLayanan.value = it
                        isError = it.isEmpty()
                    },
                    label = "Deskripsi layanan",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "ID",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                CustomSpacer()
                CustomSpacer()
                val density = LocalDensity.current.density
                val width = (100 * density).toInt()
                val height = (100 * density).toInt()
                val painter = rememberImagePainter(data = gambarLayanan, builder = {
                    crossfade(true)
                })
                //gambar
                Image(
                    painter = painter,
                    contentDescription = "Image Layanan",
                    modifier = Modifier.size(width.dp, height.dp),
                    contentScale = ContentScale.Crop
                )

                CustomSpacer()
                MyButton(
                    onClick = {
                        launcher.launch("image/")
                    },
                    text = "GANTI GAMBAR"
                )

                CustomSpacer()
                CustomSpacer()
                MyButton(
                    onClick = {
                        val layanan = Layanan(
                            idLayanan.value,
                            namaLayanan.value,
                            biayaLayanan.value,
                            deskripsiLayanan.value,
                            gambarLayanan.value
                        )
                        if (idLayanan.value.isEmpty() ||
                            namaLayanan.value.isEmpty() ||
                            biayaLayanan.value.isEmpty() ||
                            deskripsiLayanan.value.isEmpty()
                        ){
                            errorText.value = "Pastikan semua form terisi!"
                        }else{
                            layananViewModel.updateLayanan(layanan, context)
                            selectedImageUri?.let { uri ->
                                layananViewModel.uploadImageToCloudFirebase(uri, context, id)
                                selectedImageUri = null
                            }
                        }
                    },
                    text = "UPDATE LAYANAN"
                )
            }
        }
    }
}