package com.example.quoteme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class add_quote extends AppCompatActivity {

    // this activity serves to allow the user to add a quote to the database

    // initialise database variable
    public MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);


        // set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // set up backwards navigation for toolbar
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        // get database instance
        myDatabase = MyDatabase.getInstance(add_quote.this);

        //find the textview
        TextView submit = findViewById(R.id.submitbttn);

        //set click listener for submit text
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get the input view
                EditText quote = findViewById(R.id.quoteinput);

                // if the quote box is empty, display a toast
                if (quote.getText().toString()==""||quote.getText().toString()==" "){
                    Toast.makeText(getBaseContext(),"Add a Quote First!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // launch the adding quote dialog to confirm
                    addQuoteDialog();
                }

            }
        });// end of click listener for submit


    }// end of oncreate

    // method to addquote to the database
    private void addQuote() {
        //find the edit textviews
        EditText quote = findViewById(R.id.quoteinput);
        EditText source = findViewById(R.id.sourceinput);

            //create new quote object
            Quotes Quote = new Quotes();

            //set the quote text
            Quote.setQuote(quote.getText().toString());

            // if the source isn't blank, set it, else set it as space so not null
            if (source.getText().toString() != "") {
                Quote.setSource(source.getText().toString());
            } else {
                Quote.setSource(" ");
            }

            // add the quote to the database
            myDatabase.dao().addQuote(Quote);
            //confirmation toast
            Toast.makeText(getBaseContext(), "Quote added successfully!", Toast.LENGTH_SHORT).show();
            //clear inputs
            quote.setText("");
            source.setText("");


    }// end of add quote


// dialog to confirm then handle submitting the quote
    public void addQuoteDialog(){

        //find the inputs
        EditText quote = findViewById(R.id.quoteinput);
        EditText source = findViewById(R.id.sourceinput);

        // build dialog with quote and source texts
        AlertDialog.Builder builder = new AlertDialog.Builder(add_quote.this);
        builder.setTitle("Add Quote?");
        builder.setMessage("Are you sure you want to add this Quote?\n\n" +
                quote.getText().toString()+"\n\n Source: "+source.getText().toString());

// Add the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                addQuote();
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


}// end of add quote activity