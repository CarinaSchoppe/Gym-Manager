package me.kevin.gymapp.graphics.fitness

import android.content.Intent
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kevin.gymapp.backend.objects.Trainingsmap
import me.kevin.gymapp.backend.util.Utility
import me.kevin.gymapp.backend.util.Utility.selectedFitnessActivity
import me.kevin.gymapp.graphics.extra.BackButton
import me.kevin.gymapp.graphics.fitness.ui.theme.GymAppTheme
import me.kevin.gymapp.graphics.studio.FitnessStudioActivity

class FitnessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    FitnessActivityView(selectedFitnessActivity)
                }
            }
        }
    }


    @Composable
    private fun FitnessActivityView(selectedFitnessActivity: Trainingsmap) {
        val muscleGroup = Utility.muscleGroupSet.stream().filter { it.id == selectedFitnessActivity.muscleGroupID }.findFirst().get()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val checked = remember { mutableStateOf(false) }
            if (Utility.userFavoritesSet.find { it.userID == Utility.currentUser!!.id }!!.trainingsmapIDList.contains(selectedFitnessActivity.id))
                checked.value = true
            Text(text = "Fitness Map")
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Name: ${selectedFitnessActivity.name}")
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Description: ${selectedFitnessActivity.description}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Muskelgruppe: ${muscleGroup.name}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Favorite")
            Spacer(modifier = Modifier.height(5.dp))
            Checkbox(checked = checked.value, onCheckedChange = { bool ->
                checked.value = bool
                if (checked.value) {
                    Utility.addUserFavorites(Utility.currentUser!!, selectedFitnessActivity)
                } else {
                    Utility.removeUserFavorite(Utility.currentUser!!, selectedFitnessActivity)
                }
            })
            Spacer(modifier = Modifier.height(5.dp))

            Button(onClick = {
                val studio = Utility.studioSet.stream().filter { it.id == selectedFitnessActivity.studioID }.findFirst().get()
                Utility.selectedFitnessstudio = studio
                startActivity(Intent(this@FitnessActivity, FitnessStudioActivity::class.java))
            }) {
                Text("Open Fitnessstudio")
            }
        }
        BackButton.BackButton(activity = this@FitnessActivity, clazz = FitnessActivities::class.java)
    }
}

