package me.kevin.gymapp.graphics.fitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.kevin.gymapp.backend.util.Utility
import me.kevin.gymapp.graphics.extra.BackButton
import me.kevin.gymapp.graphics.ui.theme.GymAppTheme
import kotlin.streams.toList

class CreateFitnessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    createFitnessActivity()
                }
            }
        }
    }

    @Composable
    private fun createFitnessActivity() {
        val name = remember {
            mutableStateOf(TextFieldValue())
        }
        val possibleStudioNames = Utility.studioSet.stream().map { it.name }.toList()

        val selectedStudio = remember {
            mutableStateOf("")
        }
        val studioName = remember {
            mutableStateOf("Studioname: ")
        }
        val expanded = remember {
            mutableStateOf(false)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Create Fitness Activity")
            Spacer(modifier = Modifier.height(5.dp))
            TextField(value = name.value, onValueChange = { name.value = it }, placeholder = { Text("Name") })
            Spacer(modifier = Modifier.height(5.dp))


            val studioNameText = Text(text = studioName.value)

            Button(onClick = { expanded.value = true }) {
                Text(text = "Select Studio")
            }


            //place DropdownMenu in the mittle

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    listOf("Test1", "Test2", "test3").forEach { name ->
                        DropdownMenuItem(onClick = {
                            selectedStudio.value = name
                            expanded.value = false
                            studioName.value = "Studioname: $name"
                        },
                            text = { Text(name) })
                    }
                }
            }

        }

        BackButton.BackButton(activity = this@CreateFitnessActivity, clazz = FitnessActivities::class.java)
    }
}
