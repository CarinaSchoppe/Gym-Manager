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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.kevin.GymApp.backend.util.Utility
import me.kevin.GymApp.ui.ui.theme.GymAppTheme

class FitnessStudioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    FitnessStudio()
                }
            }
        }
    }


    @Composable
    private fun FitnessStudio() {

        val studioName = remember { mutableStateOf(TextFieldValue()) }
        val studioDescription = remember { mutableStateOf(TextFieldValue()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, top = 15.dp)
        ) {
            Text(text = "Fitnessstudio Erstellen")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, top = 15.dp)
        ) {
            Button(onClick = { }) {
                Text(text = "Fitnessstudio Erstellen")

            }
        }




        Utility.BackButton(activity = this@FitnessStudioActivity, clazz = FitnessActivities::class.java)
    }
}

