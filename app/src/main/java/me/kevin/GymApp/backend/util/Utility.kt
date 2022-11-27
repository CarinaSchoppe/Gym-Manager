package me.kevin.GymApp.backend.util

import android.content.Context
import me.kevin.GymApp.backend.database.Database
import me.kevin.GymApp.backend.objects.Trainingsmap
import me.kevin.GymApp.backend.objects.User

object Utility {

    fun userLogin(username: String, password: String): Boolean {
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


        //check if user exists
        if (!database.userExists(username)) {
            return false
        }
        //check if password is correct
        if (!Cypher.checkPassword(username, password, database.getPassword(username))) {
            return false

        }

        currentUser = User(database.getUserID(username), username, password, database.getEmail(username), database.getFirstname(username), database.getLastname(username))
        return true

    }


    var currentUser: User? = null

    lateinit var database: Database

    fun init(context: Context) {
        database = Database(context)
    }

    val userFavorites = mutableMapOf<User, Trainingsmap>()


    fun register(username: String, password: String, email: String, firstname: String, lastname: String): Boolean {
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


        if (username == "" || password == "" || email == "" || firstname == "" || lastname == "") {
            return false
        }

        //check if email is a valid email
        if (!email.contains("@") || !email.contains(".")) {
            return false
        }

        //check if username is already taken
        if (database.userExists(username)) {
            return false
        }

        //check if email is already taken
        if (database.emailExists(email)) {
            return false
        }

        //register user
        database.registerUser(username, Cypher.encryptPassword(password, username), email, firstname, lastname)

        return true
    }
}
