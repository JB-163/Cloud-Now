package com.example.cloudnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.cloudnow.ui.AppBar
import com.example.cloudnow.ui.SearchDrawer
import com.example.cloudnow.ui.theme.CloudNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloudNowTheme {

                // Variable to handle the search drawer.
                var showSearch by remember {
                    mutableStateOf(false)
                }
                // variable to handle the keyboard.
                val keyboardController = LocalSoftwareKeyboardController.current
                // variable to handle the focus.
                val focusManager = LocalFocusManager.current

                Box(
                    modifier = Modifier.fillMaxSize()
                        // code for dismissing the keyboard when the user taps outside the text field.
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            AppBar(
                                onSearchClick = {
                                    showSearch = true
                                }
                            )
                        }
                    ) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    SearchDrawer(
                        isVisible = showSearch,
                        onClose = {
                            showSearch = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}