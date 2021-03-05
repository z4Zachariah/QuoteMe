package com.example.quoteme;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    //the Data Access Object class handles all of the interfacing with the SQLite database

    // insert a quote into the db
    @Insert
    public void addQuote(Quotes quote);

    //select all quote objects from the db
    @Query("SELECT * FROM Quotes")
    public List<Quotes> getQuotes();

    //select all quotes where the id matches the passed integer
    @Query("SELECT * FROM Quotes WHERE ID = :search ")
    public Quotes randomQuote(int search);

    // count how many quotes there are in the db (disused)
    @Query("SELECT COUNT(ID) FROM Quotes")
    public int getCount();

    // get an array of all the id's in the database
    @Query("SELECT ID FROM Quotes")
    public int[] getIDs();

    // delete a selected quote from the database
    @Delete
    void deleteQuote(Quotes quote);

}
