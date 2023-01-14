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
                PRIMARY KEY('ID' AUTOINCREMENT)
            );""".trimIndent(),

            """CREATE TABLE IF NOT EXISTS UserFavorites (
                "UserID" INTEGER NOT NULL,
                "TrainingsMapID" INTEGER NOT NULL,
                FOREIGN KEY("UserID") REFERENCES "Users"("ID"),
                FOREIGN KEY("TrainingsMapID") REFERENCES "Trainingsplan"("ID"),
                PRIMARY KEY("UserID","TrainingsMapID"));
                """.trimIndent(),
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


                PRIMARY KEY("ID" AUTOINCREMENT)
    );""".trimIndent(),
            """
    CREATE TABLE  IF NOT EXISTS Muskelgruppe (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent()
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

    fun updateUserFavorites(userFavorites: UserFavorites) {
        val database = this.writableDatabase
        database.execSQL("DELETE FROM UserFavorites WHERE UserID = ${userFavorites.userID}")
        for (map in userFavorites.trainingsmapIDList) {
            database.execSQL("INSERT INTO UserFavorites (UserID, TrainingsMapID) VALUES (${userFavorites.userID}, ${map})")
        }

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

        var user = User(Utility.userSet.size + 1, username, password, email, firstname, lastname)
        //get the highest id in the dataset
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

    fun loadAllUserFavorites() {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM UserFavorites", null)
        while (cursor.moveToNext()) {
            //find user with id
            val user = Utility.userSet.find { it.id == cursor.getInt(0) }
            if (user != null) {
                val userFaUtility: UserFavorites = Utility.userFavoritesSet.find { it.userID == user.id } ?: run {
                    val userFavorites = UserFavorites(user.id, mutableSetOf())
                    userFavorites
                }
                userFaUtility.trainingsmapIDList.add(cursor.getInt(1))
            }
        }
        cursor.close()
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

    fun createMuscleGroup(id: Int, name: String) {
        val database = this.writableDatabase
        database.execSQL("INSERT INTO Muskelgruppe (ID, Name) VALUES ($id, '$name')")
        Log.d("MuscleGroup Registered", name)
    }

    fun createTrainingsPlan(id: Int, name: String, description: String, studioId: Int, muscleGroupId: Int) {
        val database = this.writableDatabase
        database.execSQL("INSERT INTO Trainingsplan (ID, Name, Beschreibung, StudioID, MuskelgruppeID) VALUES ($id, '$name', '$description', $studioId, $muscleGroupId)")
        Log.d("Trainingsplan Registered", name)
    }

}


