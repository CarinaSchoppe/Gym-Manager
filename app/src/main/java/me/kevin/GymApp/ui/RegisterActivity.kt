package me.kevin.GymApp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.kevin.GymApp.backend.util.Utility
import me.kevin.GymApp.ui.theme.GymAppTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Register()
                }
            }
        }
    }

//    Column(horizontalAlignment = Alignment.CenterHorizontally ,verticalArrangement = Arrangement.Bottom,modifier = Modifier.fillMaxSize()) {

    @Composable
    fun Register() {
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val firstname = remember { mutableStateOf(TextFieldValue()) }
        val lastname = remember { mutableStateOf(TextFieldValue()) }
        Column {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                TextField(value = username.value, onValueChange = { username.value = it }, placeholder = { Text("Username") })
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                TextField(visualTransformation = PasswordVisualTransformation(), value = password.value, onValueChange = { password.value = it }, placeholder = { Text("Password") })
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {

                TextField(value = email.value, onValueChange = { email.value = it }, placeholder = { Text("Email") })
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                TextField(value = firstname.value, onValueChange = { firstname.value = it }, placeholder = { Text("Vorname") })
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                TextField(value = lastname.value, onValueChange = { lastname.value = it }, placeholder = { Text("Nachname") })
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                val openDialog = remember { mutableStateOf(false) }
                val text = remember { mutableStateOf("") }
                val good = remember {
                    mutableStateOf(false)
                }
                Button(onClick = {

                    if (Utility.register(username.value.text, password.value.text, email.value.text, firstname.value.text, lastname.value.text)) {
                        openDialog.value = true
                        text.value = "Registrierung erfolgreich"
                        good.value = true
                    } else {
                        openDialog.value = true
                        text.value = "Registrierung fehlgeschlagen"
                        good.value = false
                    }
                    //get current composable


                }) {
                    Text(text = "Register")
                }
                if (openDialog.value) {
                    Popup.generatePopup(titleText = "Registrierung", displayText = text.value, good = good.value, openDialog)

                }
            }
            Utility.backButton(activity = this@RegisterActivity, clazz = LoginActivity::class.java)
        }
    }


}
