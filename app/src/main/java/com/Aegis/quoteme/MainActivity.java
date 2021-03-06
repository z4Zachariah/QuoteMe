package com.Aegis.quoteme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.Random;

// The main activity is the entry point of the app when it is launched from the home screen icon
public class MainActivity extends AppCompatActivity {

    //initialise database variable
    public MyDatabase myDatabase;


    // on create is the first thing run at the start of an activity's lifecycle.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        //get database instance
        myDatabase = MyDatabase.getInstance(MainActivity.this);

        final TextView textOutput = findViewById(R.id.textoutput);
        final TextView ins = findViewById(R.id.instruction);

        // create a short fade animation for the swipe instruction
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        animation.setStartOffset(5000);

        //start the fade animation
        ins.startAnimation(animation);
        ins.setVisibility(View.INVISIBLE);

        // create the swipe detection for the quote textview
        textOutput.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                randomQuote();
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                randomQuote();
            }
        });



        // call method to set quote once for initialisation
        randomQuote();


// Some DB testing creating a list of the quote objects
/*
        List<Quotes> quotes = myDatabase.dao().getQuotes();
        String output = "";
        for(Quotes quote : quotes) {
            output += quote.getQuote() + " " + quote.getSource() + "\n";
        }
        textOutput.setText(output);
*/


    }// end of oncreate




// inflate the menu options for the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    // handle which menu option is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add:

                // if the 'add' option is clicked (+) start the add quote activity
                Intent myIntent = new Intent(MainActivity.this, add_quote.class);
                MainActivity.this.startActivity(myIntent);

                return true;

            case R.id.delete:

                // if the 'delete' button is clicked, start the delete quote activity
                Intent myIntent2 = new Intent(MainActivity.this, delete_quote.class);
                MainActivity.this.startActivity(myIntent2);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }//end of menu item handler



// method to get a random quote from the database and animate the transition
    public  void randomQuote(){

        // find the text views in the activity
        final TextView textOutput = findViewById(R.id.textoutput);
        final TextView sourcetext = findViewById(R.id.source);

        // if there aren't any quotes, set quote to no quote message
        int count = myDatabase.dao().getCount();
        if (count==0){
            textOutput.setText(R.string.no_quotes);
            sourcetext.setText(" ");
        }


        //if at least one quote, get a random one
        else {

            // create new animation
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(1000);


            // get an array of the ids of each quote in the DB
            int[] ids = myDatabase.dao().getIDs();

            //get the length of the array
            int length = ids.length;
            // use random number generator to get a random index for the array
            int random = RandomNumber(length);

            Quotes rand;

            //if there's only one quote, get that, else run the rng
            if (length ==1){
                rand = myDatabase.dao().randomQuote(ids[0]);
            }
            else {
                // get the quote via random id
                rand = myDatabase.dao().randomQuote(ids[random]);
            }

            //set the text to the quote and source, using the animation transition
            textOutput.setText(rand.getQuote());
            sourcetext.setText(rand.getSource());
            textOutput.startAnimation(animation);
            sourcetext.startAnimation(animation);
        }


    }// end of random quote


    // random number generator
    public static int RandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }


}// end of main activity class