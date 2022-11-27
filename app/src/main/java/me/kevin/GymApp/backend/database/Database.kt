package me.kevin.GymApp.backend.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Database(val context: Context) : SQLiteOpenHelper(context, "GymApp.db", null, 1) {

    override fun onCreate(database: SQLiteDatabase?) {
        var tables = listOf<String>(
            """
                CREATE TABLE Users ('ID'	INTEGER NOT NULL UNIQUE,
                'Firstname'	TEXT NOT NULL,
                'Lastname'	INTEGER NOT NULL,
                'Username'	TEXT NOT NULL UNIQUE,
                'Email'	TEXT NOT NULL UNIQUE,
                'Password'	TEXT NOT NULL,
                FOREIGN KEY("ID") REFERENCES "UserFavorites"("UserID"),
                PRIMARY KEY('ID' AUTOINCREMENT)
            );""".trimIndent(),

            """
        CREATE TABLE Fitnessstudio (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	"Location"	TEXT NOT NULL,
	"Beschreibung"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("StudioID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE  Trainingsplan (
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
    CREATE TABLE  Muskelgruppe (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE  UserFavorites (
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
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Users WHERE Username = '$username'", null)
        return cursor.count > 0
    }

    fun emailExists(email: String): Boolean {
        val database = this.readableDatabase
        val cursor = database.rawQuery("SELECT * FROM Users WHERE Email = '$email'", null)
        return cursor.count > 0
    }

    fun registerUser(username: String, password: String, email: String, firstname: String, lastname: String) {
        val database = this.writableDatabase
        database.execSQL("INSERT INTO Users (Username, Password, Email, Firstname, Lastname) VALUES ('$username', '$password', '$email', '$firstname', '$lastname')")

        Log.d("SQL", "User registered")
    }


}