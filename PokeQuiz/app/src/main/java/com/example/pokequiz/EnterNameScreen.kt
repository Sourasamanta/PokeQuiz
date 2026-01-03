package com.example.pokequiz

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import android.widget.VideoView
import androidx.compose.ui.viewinterop.AndroidView
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController

@Composable
fun EnterNameScreen(navController: NavHostController) {
    var showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = { BottomBar(showDialog) }
    ){
        innerPadding ->
        VideoPlayer(innerPadding)

        AlertDialogEnterName(showDialog, navController)
    }
}


@Composable
fun VideoPlayer(innerPadding: androidx.compose.foundation.layout.PaddingValues) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Keep reference to VideoView
    val videoView = remember {
        VideoView(context).apply {
            setVideoURI(
                Uri.parse("android.resource://${context.packageName}/raw/greninja")
            )
            setOnCompletionListener { start() } // loop
        }
    }

    /* -------------------------------
       APP FOREGROUND / BACKGROUND
       ------------------------------- */
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    videoView.start() // app comes foreground
                }

                Lifecycle.Event.ON_PAUSE -> {
                    videoView.pause() // app goes background
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            videoView.stopPlayback()
        }
    }

    /* -------------------------------
       SCREEN ON / OFF
       ------------------------------- */
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_SCREEN_OFF -> videoView.pause()
                    Intent.ACTION_SCREEN_ON -> videoView.start()
                }
            }
        }

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }

        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    /* -------------------------------
       VIDEO VIEW UI
       ------------------------------- */
    AndroidView(
        factory = { videoView },
        modifier = Modifier.fillMaxSize()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogEnterName(showDialog: MutableState<Boolean>, navController: NavHostController) {
    if (showDialog.value) {
        var name by remember { mutableStateOf("trainer") }

        AlertDialog(
            containerColor = colorResource(id = R.color.light_blue),
            onDismissRequest = { showDialog.value = false },
            title = { Text("Enter Name") },
            text = {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your name") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (name.isNotBlank()) {
                        showDialog.value = false
                        // Pass name as navigation argument
                        navController.navigate("quiz/${name}")
                    }
                },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red))
                    ) {
                    Text(
                        text = "Confirm",
                        color = colorResource(id = R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.pokemon_solid))
                    )
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue))
                    ) {
                    Text(
                        text = "Cancel",
                        color = colorResource(id = R.color.yellow),
                        fontFamily = FontFamily(Font(R.font.pokemon_solid))
                    )
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}
