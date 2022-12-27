package me.kevin.gymapp.graphics.fitness

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.kevin.GymApp.R
import me.kevin.gymapp.backend.util.Utility
import me.kevin.gymapp.graphics.musclegroup.CreateMuscleGroupActivity
import me.kevin.gymapp.graphics.studio.CreateFitnessStudioActivity
import me.kevin.gymapp.graphics.studio.FitnessStudioActivity
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
            //create a row containing 3 buttons each beside each other
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
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                for (activity in Utility.trainingsmapSet) {
                    Button(onClick = {
                        Utility.selectedFitnessstudio = activity
                        startActivity(Intent(this@FitnessActivities, FitnessStudioActivity::class.java))
                    }) {
                        Column(
                            modifier = Modifier.padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                // on below line we are specifying the drawable
                                // image for our image.
                                painter = painterResource(id = R.drawable.ic_launcher),

                                // on below line we are specifying
                                // content description for our image
                                contentDescription = "Image",
                                // on below line we are setting height
                                // and width for our image.
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                            )
                            // adding spacer on below line.
                            Spacer(Modifier.height(10.dp))

                            // adding text on below line.
                            Text(
                                // specifying text as android
                                text = activity.name,

                                // on below line adding style
                                style = TextStyle(fontWeight = FontWeight.Bold),

                                // adding text align on below line.
                                textAlign = TextAlign.Center,

                                // adding font size on below line.
                                fontSize = 20.sp
                            )
                        }
                    }
                }

            }


        }

    }
}
