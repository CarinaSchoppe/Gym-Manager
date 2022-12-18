package me.kevin.gymapp.backend.util

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import me.kevin.gymapp.backend.database.Database
import me.kevin.gymapp.backend.objects.FitnessStudio
import me.kevin.gymapp.backend.objects.Musclegroup
import me.kevin.gymapp.backend.objects.Trainingsmap
import me.kevin.gymapp.backend.objects.User
import me.kevin.gymapp.backend.objects.UserFavorites

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
        if (userSet.find { it.username == username } == null) {

            return false

        }
        //check if password is correct
        if (!Cypher.checkPassword(username, password, userSet.find { it.username == username }!!.password)) {
            return false

        }

        currentUser = userSet.find { it.username == username }!!

        return true
    }


    var currentUser: User? = null

    lateinit var database: Database

    fun init(context: Context) {
        database = Database(context)
    }


    fun registerFitnessStudio(studioName: String, studioDescription: String, location: LatLng) {
        //get the highest id number of the studios
        val number = studioSet.maxOfOrNull { it.id } ?: 0

        val studio = FitnessStudio(number + 1, studioName, "${location.latitude};${location.longitude}", studioDescription)

        studioSet.add(studio)

        database.registerFitnessStudio(studio.id, studioName, studioDescription, studio.location)

    }

    fun registerUser(username: String, password: String, email: String, firstname: String, lastname: String): Boolean {
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


        //register user
        database.registerUser(username, Cypher.encryptPassword(password, username), email, firstname, lastname)




        return true
    }


    val studioSet = mutableSetOf<FitnessStudio>()

    val trainingsmapSet = mutableSetOf<Trainingsmap>()

    val muscleGroupSet = mutableSetOf<Musclegroup>()

    val userSet = mutableSetOf<User>()

    val userFavorites = mutableSetOf<UserFavorites>()

    fun createMuscleGroup(name: String) {
        if (muscleGroupSet.find { it.name == name } == null) {
            val number = muscleGroupSet.maxOfOrNull { it.id } ?: 0
            val muscleGroup = Musclegroup(number + 1, name)
            muscleGroupSet.add(muscleGroup)
            database.createMuscleGroup(muscleGroup.id, muscleGroup.name)
        }
    }

    fun fillValuesFromDataBase() {
        userSet.addAll(database.getAllUsers())
        studioSet.addAll(database.getAllFitnessStudios())
        trainingsmapSet.addAll(database.getAllTrainingsMaps())
        muscleGroupSet.addAll(database.getAllMuscleGroups())
        userFavorites.addAll(database.getAllUserFavorites())
    }

    lateinit var selectedFitnessstudio: FitnessStudio


}
