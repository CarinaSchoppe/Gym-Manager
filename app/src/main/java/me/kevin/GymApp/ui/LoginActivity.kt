package me.kevin.GymApp.ui

import android.os.Bundle
import android.renderscript.RenderScript.Priority
import android.util.Log
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
        Utility.init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                println("jsioerwjpüosejf")
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Login()
                }
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
    }
}

@Composable
fun Username(username: MutableState<TextFieldValue>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
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
            .padding(top = 10.dp)
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
fun LoginButton(username: MutableState<TextFieldValue>, password: MutableState<TextFieldValue>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Button(onClick = {
            Utility.userLogin(username = username.value.text, password = password.value.text) }) {}
            

    }
}


