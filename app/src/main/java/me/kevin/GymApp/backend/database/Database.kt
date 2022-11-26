package me.kevin.GymApp.backend.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Database(val context: Context) : SQLiteOpenHelper(context, "GymApp.db", null, 1) {

    override fun onCreate(database: SQLiteDatabase?) {
        var tables = listOf<String>(
            """
                CREATE TABLE IF NOT EXITS Users ('ID'	INTEGER NOT NULL UNIQUE,
                'Firstname'	TEXT NOT NULL,
                'Lastname'	INTEGER NOT NULL,
                'Username'	TEXT NOT NULL UNIQUE,
                'Email'	TEXT NOT NULL UNIQUE,
                'Password'	TEXT NOT NULL,
                FOREIGN KEY("ID") REFERENCES "UserFavorites"("UserID"),
                PRIMARY KEY('ID' AUTOINCREMENT)
            );""".trimIndent(),

            """
        CREATE TABLE IF NOT EXITS"Fitnessstudio" (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	"Location"	TEXT NOT NULL,
	"Beschreibung"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("StudioID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE IF NOT EXITS"Trainingsplan" (
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
    CREATE TABLE IF NOT EXITS "Muskelgruppe" (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE IF NOT EXITS  "UserFavorites" (
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


}