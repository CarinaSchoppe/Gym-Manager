package me.kevin.gymapp.backend.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import me.kevin.gymapp.backend.objects.FitnessStudio
import me.kevin.gymapp.backend.objects.Musclegroup
import me.kevin.gymapp.backend.objects.Trainingsmap
import me.kevin.gymapp.backend.objects.User
import me.kevin.gymapp.backend.objects.UserFavorites
import me.kevin.gymapp.backend.util.Utility

class Database(val context: Context) : SQLiteOpenHelper(context, "GymApp.db", null, 1) {

    override fun onCreate(database: SQLiteDatabase?) {
        val tables = listOf<String>(
            """
                CREATE TABLE  IF NOT EXISTS Users ('ID'	INTEGER NOT NULL UNIQUE,
                'Firstname'	TEXT NOT NULL,
                'Lastname'	INTEGER NOT NULL,
                'Username'	TEXT NOT NULL UNIQUE,
                'Email'	TEXT NOT NULL UNIQUE,
                'Password'	TEXT NOT NULL,
                FOREIGN KEY("ID") REFERENCES "UserFavorites"("UserID"),
                PRIMARY KEY('ID' AUTOINCREMENT)
            );""".trimIndent(),

            """
        CREATE TABLE IF NOT EXISTS Fitnessstudio (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	"Location"	TEXT NOT NULL,
	"Beschreibung"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("StudioID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE  IF NOT EXISTS Trainingsplan (
                "ID"	INTEGER NOT NULL UNIQUE,
                "Name"	TEXT NOT NULL,
                "Beschreibung"	TEXT NOT NULL,
                "StudioID"	INTEGER NOT NULL,
                "MuskelgruppeID"	INTEGER NOT NULL,
                FOREIGN KEY("MuskelgruppeID") REFERENCES "Muskelgruppe"("ID"),
                FOREIGN KEY("StudioID") REFERENCES "Trainingsplan"("ID"),
                FOREIGN KEY("ID") REFERENCES "UserFavorites"("TrainingsplanID"),

                PRIMARY KEY("ID" AUTOINCREMENT)
    );""".trimIndent(),
            """
    CREATE TABLE  IF NOT EXISTS Muskelgruppe (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE  IF NOT EXISTS UserFavorites (
                	"UserID"	INTEGER NOT NULL,
                	"TrainingsplanID"	INTEGER NOT NULL,
                	FOREIGN KEY("UserID") REFERENCES "Users"("ID"),
                	FOREIGN KEY("TrainingsplanID") REFERENCES "Users"("ID"),
                	PRIMARY KEY("UserID","TrainingsplanID")
                );
            """.trimIndent()
        )

        for (table in tables) {
            database?.execSQL(table)
            Log.d("SQL", "created table")
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.execSQL("DROP TABLE IF EXISTS Users")
        database?.execSQL("DROP TABLE IF EXISTS Fitnessstudio")
        database?.execSQL("DROP TABLE IF EXISTS Trainingsplan")
        database?.execSQL("DROP TABLE IF EXISTS Muskelgruppe")
        database?.execSQL("DROP TABLE IF EXISTS UserFavorites")
        onCreate(database)
    }


    fun registerFitnessStudio(id: Int, name: String, description: String, location: String) {
        val database = this.writableDatabase
        database.execSQL("INSERT INTO Fitnessstudio (ID, Name, Beschreibung, Location) VALUES ($id, '$name', '$description', '$location')")
        Log.d("Location Registered", location)
    }

    fun registerUser(username: String, password: String, email: String, firstname: String, lastname: String) {
        val database = this.writableDatabase
        /*        //print the values
                Log.d("SQL", "username: $username")
                Log.d("SQL", "password: $password")
                Log.d("SQL", "email: $email")
                Log.d("SQL", "firstname: $firstname")
                Log.d("SQL", "lastname: $lastname")*/

        database.execSQL("INSERT INTO Users (Firstname, Lastname, Username, Email, Password) VALUES ('$username', '$password', '$email', '$firstname', '$lastname');")

        var user = User(0, username, password, email, firstname, lastname)
        //get the highest id in the dataset
        user.id = Utility.userSet.size
        Utility.userSet.add(user)
        Log.d("SQL", "User registered")
    }


    fun getAllUsers(): List<User> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Users", null)
        val users = mutableListOf<User>()
        while (cursor.moveToNext()) {
            val user = User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5))
            users.add(user)
        }
        cursor.close()
        return users

    }

    fun getAllFitnessStudios(): List<FitnessStudio> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Fitnessstudio", null)

        val fitnessStudios = mutableListOf<FitnessStudio>()
        while (cursor.moveToNext()) {
            val fitnessStudio = FitnessStudio(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
            fitnessStudios.add(fitnessStudio)
        }
        cursor.close()
        return fitnessStudios
    }

    fun getAllMuscleGroups(): List<Musclegroup> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Muskelgruppe", null)

        val muscleGroups = mutableListOf<Musclegroup>()
        while (cursor.moveToNext()) {
            val muscleGroup = Musclegroup(cursor.getInt(0), cursor.getString(1))
            muscleGroups.add(muscleGroup)
        }
        cursor.close()
        return muscleGroups
    }

    fun getAllUserFavorites(): List<UserFavorites> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM UserFavorites", null)

        val userFavorites = mutableListOf<UserFavorites>()
        while (cursor.moveToNext()) {
            val userFavorite = UserFavorites(cursor.getInt(0), cursor.getInt(1))
            userFavorites.add(userFavorite)
        }
        cursor.close()
        return userFavorites

    }

    fun getAllTrainingsMaps(): List<Trainingsmap> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Trainingsplan", null)

        val trainingsMaps = mutableListOf<Trainingsmap>()
        while (cursor.moveToNext()) {
            val trainingsMap = Trainingsmap(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4))
            trainingsMaps.add(trainingsMap)
        }
        cursor.close()
        return trainingsMaps
    }

}


