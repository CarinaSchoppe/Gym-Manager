package me.kevin.gymapp.graphics.musclegroup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.kevin.gymapp.backend.util.Utility
import me.kevin.gymapp.graphics.extra.BackButton
import me.kevin.gymapp.graphics.extra.Popup
import me.kevin.gymapp.graphics.fitness.FitnessActivities
import me.kevin.gymapp.graphics.ui.theme.GymAppTheme

class CreateMuscleGroupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    createMuscleGroup()

                }
            }
        }
    }

    @Composable
    private fun createMuscleGroup() {
        val text = remember { mutableStateOf(TextFieldValue()) }
        val open = remember {
            mutableStateOf(false)
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Create Muscle Group")
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(value = text.value, onValueChange = { text.value = it }, placeholder = { Text("Muscle Group Name") })
            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                if (text.value.text.isBlank() || text.value.text.isEmpty()) {
                    return@Button
                }

                val name = text.value.text.trim()
                Utility.createMuscleGroup(name)
                open.value = true
            }) {
                Text(text = "Create")
            }
        }



        if (open.value)
            Popup.GeneratePopup(titleText = "Muscle Group", "Muscle Group ${text.value.text} created", good = true, openDialog = open)


        BackButton.BackButton(activity = this@CreateMuscleGroupActivity, clazz = FitnessActivities::class.java)
    }

}
