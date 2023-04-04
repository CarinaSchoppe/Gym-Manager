package me.carina.gymapp.graphics.extra

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object BackButton {

    @Composable
    fun BackButton(activity: Activity, clazz: Class<out ComponentActivity>) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {

            Button(onClick = {
                //start old activity
                activity.startActivity(Intent(activity, clazz))
            }) {
                Text(text = "Back")
            }

        }


    }
}