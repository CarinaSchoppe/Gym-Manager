package me.kevin.GymApp.backend.database

import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File

class Database {

    lateinit var database: SQLiteDatabase
    lateinit var cursor: SQLiteCursor
    fun createDatabase() {
        //create databasefile
        database = SQLiteDatabase.openOrCreateDatabase(File("database.db"), null)
        //create tables

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
        CREATE TABLE "Fitnessstudio" (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	"Location"	TEXT NOT NULL,
	"Beschreibung"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("StudioID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE "Trainingsplan" (
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
    CREATE TABLE "Muskelgruppe" (
	"ID"	INTEGER NOT NULL UNIQUE,
	"Name"	TEXT NOT NULL,
	FOREIGN KEY("ID") REFERENCES "Trainingsplan"("ID"),
	PRIMARY KEY("ID" AUTOINCREMENT)
);""".trimIndent(),
            """
                CREATE TABLE "UserFavorites" (
                	"UserID"	INTEGER NOT NULL,
                	"TrainingsplanID"	INTEGER NOT NULL,
                	FOREIGN KEY("UserID") REFERENCES "Users"("ID"),
                	FOREIGN KEY("TrainingsplanID") REFERENCES "Users"("ID"),
                	PRIMARY KEY("UserID","TrainingsplanID")
                );
            """.trimIndent()
        )

        for (table in tables) {
            database.execSQL(table)
            Log.d("SQL", "created table")
        }
    }


}