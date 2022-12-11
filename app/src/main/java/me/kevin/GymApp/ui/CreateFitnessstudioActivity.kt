package me.kevin.GymApp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import me.kevin.GymApp.ui.ui.ui.theme.GymAppTheme

class CreateFitnessstudioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                }
            }
        }
    }


    @Composable
    private fun CreateFitnessstudio() {
        val studioName = remember { mutableStateOf(TextFieldValue()) }
        val studioDescription = remember { mutableStateOf(TextFieldValue()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Fitnessstudio erstellen")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            TextField(
                value = studioName.value,
                onValueChange = { studioName.value = it },
                placeholder = {
                    Text("Studio Beschreibung")
                    //place text centered
                },
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        ) {
            TextField(
                value = studioDescription.value,
                onValueChange = { studioDescription.value = it },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text("Studio Beschreibung") }
            )
        }


        Utility.BackButton(activity = this@CreateFitnessstudioActivity, clazz = FitnessActivities::class.java)
    }
}

