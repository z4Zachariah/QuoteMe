package com.Aegis.quoteme;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Quotes")
public class Quotes {

    // the Quotes class acts as an entity - a table in the database

    // assign primary key, autoincrement by one per quote added
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    //quote text
    @ColumnInfo(name = "Quote")
    private String quote;

    //source text
    @ColumnInfo(name = "Source")
    private String source;



// getters and setters for table fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}// end of quotes class
