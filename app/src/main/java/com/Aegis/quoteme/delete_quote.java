package com.Aegis.quoteme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class delete_quote extends AppCompatActivity {
    // This activity handles deleting quotes from the database by displaying them all in a recyclerview
    //for inspection and on long press of a given quote creates a deletion confirmation dialog

    // initialise database variable
    public MyDatabase myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_quote);


        // set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // set up backwards navigation
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        // get database instance
        myDatabase = MyDatabase.getInstance(delete_quote.this);

        // get a list of all quote objects in the database
        List<Quotes> quotes = myDatabase.dao().getQuotes();

        //find and set up recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(quotes);

        // set up the layout manager for the recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set up handling long clicks on the quote items in the recyclerview
        recyclerViewAdapter.setOnItemListenerListener(new RecyclerViewAdapter.OnItemListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                       // do nothing -- onclick must be included here
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

                // get the position of the item, then get the object associated with that position
                RecyclerView recyclerView = findViewById(R.id.recyclerview);

                int pos = recyclerView.getChildLayoutPosition(view);
                Quotes item = quotes.get(pos);

                //pass the selected quote object to the deletion confirmation dialog
                deleteQuoteDialog(item);
            }
        });// end of long click handler

        // set the adapter for the recyclerview
        recyclerView.setAdapter(recyclerViewAdapter);

    }// end of oncreate

// delete quote confirmation dialog
    public void deleteQuoteDialog(Quotes quote){

        // get object variables
        int id = quote.getId();
        String Quote = quote.getQuote();
        String Source = quote.getSource();

//build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(delete_quote.this);
        builder.setTitle("Delete Quote?");
        builder.setMessage("Are you sure you want to delete this Quote?: \n\n "+Quote +
                "\n\nBy: " +Source);

// Add the buttons
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // delete the selected quote object
                myDatabase.dao().deleteQuote(quote);

                // confirmation toast
                Toast.makeText(delete_quote.this,
                        "Quote Deleted",
                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}// end of delete quote activity