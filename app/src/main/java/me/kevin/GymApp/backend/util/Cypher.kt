package me.kevin.GymApp.backend.util

import android.util.Log
import org.mindrot.jbcrypt.BCrypt

object Cypher {
    fun encryptPassword(password: String, username: String): String {
        var password = password
        password = password.replace("'", "")
        password = password.replace("\"", "")
        password = password.replace(";", "")
        password = password.replace("=", "")
        password = password.replace(" ", "")

        //add salt to password
        password = password + username

        Log.d("password", "Password Encryped: $password")
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun checkPassword(password: String, username: String, hash: String): Boolean {
        return BCrypt.checkpw(password + username, hash)
    }


}