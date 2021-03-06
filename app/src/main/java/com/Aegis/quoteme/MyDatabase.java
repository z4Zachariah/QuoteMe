package com.Aegis.quoteme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Quotes.class}, version = 1)


public abstract class MyDatabase extends RoomDatabase {

    // the database class sets up linking the entities of the database and the Data Access Object

    // create a DAO
    public abstract Dao dao();

    //singleton pattern database
    //Because it is costly to build the database anew for each activity, create one shared instance
    private static MyDatabase myDatabase;

    // if the database hasnt been built, build it. Else return the database
    public static MyDatabase getInstance(Context context) {
        if (null == myDatabase) {
            myDatabase = buildDatabaseInstance(context);
        }
        return myDatabase;
    }

    // database building method creates a db with a pre-populated database file
    private static MyDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, MyDatabase.class, "quotesdb")
                .allowMainThreadQueries()
                .createFromAsset("quotes.db")
                .build();
    }//end of database builder


}// end of database class
