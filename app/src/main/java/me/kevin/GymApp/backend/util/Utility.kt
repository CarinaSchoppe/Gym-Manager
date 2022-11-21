package me.kevin.GymApp.backend.util

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

    fun init() {
        database = Database()
    }
}