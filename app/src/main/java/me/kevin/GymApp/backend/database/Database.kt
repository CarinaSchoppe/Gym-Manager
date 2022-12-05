package me.kevin.GymApp.backend.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import me.kevin.GymApp.backend.objects.User

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

    fun userExists(username: String): Boolean {
        //print all users
        println("Users:")
        for (user in getAllUsers()) {
            println(user)
        }
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM USERS where USERNAME = 'carina'", null)
        val int = cursor.count > 0
        cursor.close()
        return int
    }

    fun emailExists(email: String): Boolean {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Users WHERE Email = '$email';", null)
        val int = cursor.count > 0
        cursor.close()
        return int
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

        Log.d("SQL", "User registered")
    }

    fun getPassword(username: String): String {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT Password FROM Users WHERE Username = '$username';", null)
        val string = cursor.getString(0)
        cursor.close()
        return string
    }

    fun getUserID(username: String): Int {
        val database = this.readableDatabase

        //print all users
        //getAllUsers()

        val cursor = database.rawQuery("SELECT ID FROM Users WHERE Username='$username';", null)
        val int = cursor.getInt(0)
        cursor.close()
        return int
    }

    fun getEmail(username: String): String {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT Email FROM Users WHERE Username = '$username';", null)
        val string = cursor.getString(0)
        cursor.close()
        return string
    }

    fun getFirstname(username: String): String {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT Firstname FROM Users WHERE Username = '$username';", null)
        val string = cursor.getString(0)
        cursor.close()
        return string
    }

    fun getLastname(username: String): String {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT Lastname FROM Users WHERE Username = '$username';", null)
        val string = cursor.getString(0)
        cursor.close()
        return string
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

}


