package me.kevin.gymapp.ui.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import me.kevin.gymapp.backend.util.Utility
import me.kevin.gymapp.ui.ui.ui.theme.GymAppTheme


class CreateFitnessstudioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CreateFitnessstudio()
                }
            }
        }
    }


    @Preview
    @Composable
    private fun CreateFitnessstudio() {

        val studioName = remember { mutableStateOf(TextFieldValue()) }
        val studioDescription = remember { mutableStateOf(TextFieldValue()) }

        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } else
            lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val longitude: Double = location?.longitude ?: 0.0
        val latitude: Double = location?.latitude ?: 0.0
        val studioPos = LatLng(latitude, longitude)
        val state: MarkerState = rememberMarkerState(position = studioPos)
        val openDialog = remember { mutableStateOf(false) }
        val cameraState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(studioPos, 12f)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GoogleMap(modifier = Modifier.fillMaxWidth(), cameraPositionState = cameraState, onMapClick = { state.position = it }) {
                Marker(state = state, title = "Fitnessstudio Position")
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(text = "Fitnessstudio erstellen", fontFamily = FontFamily.Monospace, fontSize = TextUnit.Unspecified)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = studioName.value,
                onValueChange = { studioName.value = it },
                placeholder = {
                    Text("Studio Name")
                    //place text centered
                },
            )
            TextField(
                value = studioDescription.value,
                onValueChange = { studioDescription.value = it },
                placeholder = { Text("Studio Beschreibung") }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp)
        ) {
            Button(onClick = { createStudio(state, studioName.value.text, studioDescription.value.text, openDialog) }) {
                Text(text = "Fitnessstudio erstellen")
            }
            Popup.GeneratePopup(titleText = "Missing Text", displayText = "", good = false, openDialog = openDialog)
        }
        Utility.BackButton(activity = this@CreateFitnessstudioActivity, clazz = FitnessActivities::class.java)

    }


    fun createStudio(state: MarkerState, studioName: String, studioDescription: String, openDialog: MutableState<Boolean>) {

        if (studioName.isEmpty() || studioDescription.isEmpty()) {
            openDialog.value = true
            return
        }

        Utility.registerFitnessStudio(studioName, studioDescription, state.position)
    }

}

