package me.kevin.gymapp.graphics.fitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.kevin.gymapp.graphics.musclegroup.CreateMuscleGroupActivity
import me.kevin.gymapp.graphics.studio.CreateFitnessStudioActivity
import me.kevin.gymapp.graphics.ui.theme.GymAppTheme

class FitnessActivities : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    createFitnessActivityMap()
                }
            }
        }
    }


    @Composable
    private fun createFitnessActivityMap() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Fitness Activities")
            Spacer(modifier = Modifier.height(5.dp))
            //create a row containing 3 buttons each beside eachother
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //create a button for each fitness activity
                Button(onClick = { startActivity(Intent(this@FitnessActivities, CreateFitnessStudioActivity::class.java)) }) { Text(text = "Add Studio") }
                Button(onClick = { startActivity(Intent(this@FitnessActivities, CreateMuscleGroupActivity::class.java)) }) { Text(text = "Add Musclegroup") }
                Button(onClick = { startActivity(Intent(this@FitnessActivities, CreateFitnessActivity::class.java)) }) { Text(text = "Add Fitnessplan") }
            }


        }

    }
}
