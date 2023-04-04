package me.carina.gymapp.graphics.studio

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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import me.carina.gymapp.backend.util.Utility
import me.carina.gymapp.graphics.extra.BackButton
import me.carina.gymapp.graphics.fitness.FitnessActivities
import me.carina.gymapp.graphics.ui.theme.GymAppTheme

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Fitnessstudio ansehen")
        }

        var lat = Utility.selectedFitnessstudio.location.split(";")[0].toDouble()
        var lng = Utility.selectedFitnessstudio.location.split(";")[1].toDouble()
        val studioPos = LatLng(51.716, 8.766) //TODO: get from Object
        val state = MarkerState(position = studioPos)
        val cameraState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(studioPos, 12f) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraState) {
                Marker(state = state, title = "Fitnessstudio Position")
            }
        }




        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {

            Button(onClick = {
                startActivity(Intent(this@FitnessStudioActivity, CreateFitnessStudioActivity::class.java))

            }) {
                Text(text = "Fitnessstudio Erstellen")

            }
            Spacer(modifier = Modifier.height(50.dp))

        }




        BackButton.BackButton(activity = this@FitnessStudioActivity, clazz = FitnessActivities::class.java)
    }
}

