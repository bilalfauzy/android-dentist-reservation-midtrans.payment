package com.example.dentalapp.view.dashboard

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.dentalapp.routes.Screen
import com.example.dentalapp.theme.backColor
import com.example.dentalapp.view.customcomponent.CustomCard
import com.example.dentalapp.view.customcomponent.CustomSpacer
import com.example.dentalapp.view.customcomponent.IconButton
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
    val umurUser = userLogin.map {
        it.umur.toString()
    }.joinToString()
    val noWa = userLogin.map {
        it.noWa.toString()
    }.joinToString()

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
            launcher.launch("image/*")
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
                navigationIcon = Icons.Outlined.KeyboardArrowLeft,
                onNavigationClick = {
                    navController.popBackStack(Screen.HomeScreen.route, false)
                }
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(backColor)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                val painter = rememberAsyncImagePainter(fotoProfil)
                if (fotoProfil.isEmpty()){
                    Image(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }else {
                    Image(
                        painter = painter,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                IconButton(
                    iconResId = Icons.Outlined.Add,
                    description = "add foto",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .offset(y = -20.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )

//                MyButton(
//                    onClick = {
//                        launcher.launch("image/*")
//                    },
//                    text = "Ganti Foto"
//                )

                CustomCard {
                    Column(
                        modifier = Modifier.padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Nama \t\t\t\t\t\t:\t${namaUser}")
                        CustomSpacer()
                        Text(text = "Email \t\t\t\t\t\t:\t${emailUser}")
                        CustomSpacer()
                        Text(text = "Umur \t\t\t\t\t\t:\t${umurUser}")
                        CustomSpacer()
                        Text(text = "Nomor WA\t\t:\t${noWa}")
                    }

                }

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