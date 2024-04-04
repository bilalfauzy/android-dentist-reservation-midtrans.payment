package com.example.dentalapp.view.dashboard

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.MyAppBar
import com.example.dentalapp.view.customcomponent.MyButton
import com.example.dentalapp.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(
    navController: NavHostController,
    usersViewModel: UsersViewModel
){
    val firebaseAuth = FirebaseAuth.getInstance()
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE)
    usersViewModel.getUserLogin(emailLogin!!)
    val userLogin by usersViewModel.userLogin.collectAsState(emptyList())

    val namaList = userLogin.map {
        it.nama.toString()
    }
    val emailList = userLogin.map {
        it.email.toString()
    }
    val idUser = userLogin.map {
        it.idUser.toString()
    }.joinToString()
    val namaUser = namaList.joinToString()
    val emailUser = emailList.joinToString()
    val fotoProfil = userLogin.map {
        it.fotoProfilUrl.toString()
    }.joinToString()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcher.launch("image/")
        } else {
            Toast.makeText(context, "izin ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    selectedImageUri?.let { uri ->
        usersViewModel.uploadImageToCloudFirebase(uri, context, namaUser, idUser)
        selectedImageUri = null
    }

        Column() {
            MyAppBar(
                title = "Profile",
                navigationIcon = Icons.Filled.ArrowBack,
                onNavigationClick = {
                    navController.popBackStack(Screen.HomeScreen.route, false)
                }
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(40.dp)
            ){

                val density = LocalDensity.current.density
                val width = (100 * density).toInt()
                val height = (100 * density).toInt()
                val painter = rememberImagePainter(data = fotoProfil, builder = {
                    crossfade(true)
                })
                if (fotoProfil.isEmpty()){
                    Image(
                        painter = painterResource(id = R.drawable.ic_person_24),
                        contentDescription = "Selected Image",
                        modifier = Modifier.size(width.dp, height.dp),
                        contentScale = ContentScale.Crop
                    )
                }else {
                    Image(
                        painter = painter,
                        contentDescription = "Selected Image",
                        modifier = Modifier.size(width.dp, height.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                MyButton(
                    onClick = {
                        launcher.launch("image/")
                    },
                    text = "Ganti Foto"
                )

                Text(text = "Nama :\n${namaUser}")
                Text(text = "Email :\n${emailUser}")

                Spacer(modifier = Modifier.weight(1f))
                MyButton(onClick = {
                    with(sharedPref.edit()){
                        clear()
                        apply()
                    }
                    navController.navigate(
                        Screen.LoginScreen.route
                    )
                },text = "LOGOUT")
            }
        }

}