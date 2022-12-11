package me.kevin.GymApp.ui.ui.extra

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

object Popup {

    @Composable
    fun GeneratePopup(titleText: String, displayText: String, good: Boolean, openDialog: MutableState<Boolean>) {
        val dialog = remember { mutableStateOf(true) }
        if (dialog.value) {
            AlertDialog(title = { Text(text = titleText, color = good.let { if (it) return@let Color.Green else return@let Color.Red }) }, onDismissRequest = { dialog.value = false;openDialog.value = false }, text = { Text(text = displayText, color = good.let { if (it) return@let Color.Green else return@let Color.Red }) },

                confirmButton = {
                    TextButton(onClick = {
                        dialog.value = false
                        openDialog.value = false
                    }) {
                        Text(text = "Ok")
                    }
                }

            )
        }
    }
}
