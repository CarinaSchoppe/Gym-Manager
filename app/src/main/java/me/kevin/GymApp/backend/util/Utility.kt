package me.kevin.GymApp.backend.util

import android.content.Context
import me.kevin.GymApp.backend.database.Database

object Utility {

    fun userLogin(username: String, password: String){
        var username = username
        var password = password
        //clean username and password from sql injection
        //check if user exists
        //check if password is correct
        //if both are correct, login

        username = username.replace("'", "")
        username = username.replace("\"", "")
        username = username.replace(";", "")
        username = username.replace("=", "")
        username = username.replace(" ", "")

        password = password.replace("'", "")
        password = password.replace("\"", "")
        password = password.replace(";", "")
        password = password.replace("=", "")
        password = password.replace(" ", "")


    }

    lateinit var database: Database

    fun init(context: Context) {

        database = Database(context)

    }


    fun register(username: String, password: String, email: String, firstname: String, lastname: String) {
        var username = username
        var password = password
        var email = email
        var firstname = firstname
        var lastname = lastname

        username = username.replace("'", "")
        username = username.replace("\"", "")
        username = username.replace(";", "")
        username = username.replace("=", "")
        username = username.replace(" ", "")

        password = password.replace("'", "")
        password = password.replace("\"", "")
        password = password.replace(";", "")
        password = password.replace("=", "")
        password = password.replace(" ", "")

        email = email.replace("'", "")
        email = email.replace("\"", "")
        email = email.replace(";", "")
        email = email.replace("=", "")
        email = email.replace(" ", "")

        firstname = firstname.replace("'", "")
        firstname = firstname.replace("\"", "")
        firstname = firstname.replace(";", "")
        firstname = firstname.replace("=", "")
        firstname = firstname.replace(" ", "")

        lastname = lastname.replace("'", "")
        lastname = lastname.replace("\"", "")
        lastname = lastname.replace(";", "")
        lastname = lastname.replace("=", "")
        lastname = lastname.replace(" ", "")

        //TODO: database.register(username, password, email, firstname, lastname)
    }
}