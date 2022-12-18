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
import me.kevin.gymapp.graphics.extra.Popup
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
        val description = remember {
            mutableStateOf(TextFieldValue())
        }
        val possibleStudioNames = Utility.studioSet.stream().map { it.name }.toList()

        val selectedStudio = remember {
            mutableStateOf("")
        }
        val selectedMuscleGroup = remember {
            mutableStateOf("")
        }
        val studioName = remember {
            mutableStateOf("Studioname: ")
        }
        val muscleGroupName = remember {
            mutableStateOf("MuscleGroup: ")
        }
        val expandedStudio = remember {
            mutableStateOf(false)
        }
        val expandedMuscle = remember {
            mutableStateOf(false)
        }


        val created = remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Create Fitness Map")
            Spacer(modifier = Modifier.height(5.dp))
            TextField(value = name.value, onValueChange = { name.value = it }, placeholder = { Text("Name") })
            TextField(value = description.value, onValueChange = { description.value = it }, placeholder = { Text("Description") })
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = studioName.value)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = muscleGroupName.value)
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = { expandedStudio.value = true }) {
                Text(text = "Select Studio")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = { expandedMuscle.value = true }) {
                Text(text = "Select Musclegroup")
            }

            Spacer(modifier = Modifier.height(5.dp))

            Button(onClick = {
                if (Utility.createActivityTask(name.value.text, muscleGroupName.value, studioName.value, description.value.text))
                    created.value = true
            }) {
                Text(text = "Create")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                DropdownMenu(
                    expanded = expandedStudio.value,
                    onDismissRequest = { expandedStudio.value = false },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Utility.studioSet.forEach { studio ->
                        DropdownMenuItem(onClick = {
                            selectedStudio.value = studio.name
                            expandedStudio.value = false
                            studioName.value = "Studioname: ${studio.name}"
                        },
                            text = { Text(studio.name) })
                    }
                }
                DropdownMenu(
                    expanded = expandedMuscle.value,
                    onDismissRequest = { expandedMuscle.value = false },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Utility.muscleGroupSet.forEach { muscleGroup ->
                        DropdownMenuItem(onClick = {
                            selectedMuscleGroup.value = muscleGroup.name
                            expandedMuscle.value = false
                            muscleGroupName.value = "MuscleGroup: ${muscleGroup.name}"
                        },
                            text = { Text(muscleGroup.name) })
                    }
                }
            }


        }
        if (created.value)
            Popup.GeneratePopup("Activity", "ActivityCreated", true, created)

        BackButton.BackButton(activity = this@CreateFitnessActivity, clazz = FitnessActivities::class.java)
    }
}
