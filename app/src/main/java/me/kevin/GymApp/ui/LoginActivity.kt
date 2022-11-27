package me.kevin.GymApp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.kevin.GymApp.backend.util.Utility
import me.kevin.GymApp.ui.theme.GymAppTheme

class LoginActivity : ComponentActivity() {

    init {
        Utility.init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background) {
                    // A surface container using the 'background' color from the theme
                    Login()
                }
            }
        }

    }


    @Composable
    fun Login(modifier: Modifier = Modifier) {
        Column {
            val username = remember { mutableStateOf(TextFieldValue()) }
            Username(username)
            val password = remember { mutableStateOf(TextFieldValue()) }
            Password(password)
            LoginButton(username, password)
            RegisterButton()
        }
    }

    @Composable
    fun Username(username: MutableState<TextFieldValue>) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            TextField(
                value = username.value,
                onValueChange = { username.value = it },
                placeholder = {
                    Text("username")
                    //place text centered
                },
            )
        }

    }

    @Composable
    fun Password(password: MutableState<TextFieldValue>) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        ) {
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text("password") }
            )
        }
    }

    @Composable
    fun RegisterButton() {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }) {
                Text("Register")
            }
        }
    }

    @Composable
    fun LoginButton(username: MutableState<TextFieldValue>, password: MutableState<TextFieldValue>) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, top = 15.dp)
        ) {
            val openDialog = remember { mutableStateOf(false) }
            Button(onClick = {
                if (Utility.userLogin(username = username.value.text, password = password.value.text)) {
                    openDialog.value = false
                }

            }) { Text(text = "Login") }

            if (openDialog.value)
                Popup.generatePopup(titleText = "Wrong Login", displayText = "Username or Password is wrong", good = false, openDialog = openDialog)
        }
    }

}

