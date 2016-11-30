package com.example.djiinn.hellowordadvanced;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout2);

        ArrayList<View> tempView = linearLayout.getTouchables();

        for (View v:tempView)
        {
            ImageButton tempButton = (ImageButton) findViewById(v.getId());
            tempButton.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update
                    alertDialog.setTitle("Bakale bi buraya");
                    alertDialog.setMessage("Scrolling buttons.. you can imagine like scrolling gifs or something like that you know ;) ");

                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Continue..", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "bide yandakina basiverele!", Toast.LENGTH_SHORT);
                        }
                    });

                    alertDialog.show();  //<-- See This!
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
