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
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This code gets all touchable childs from his parent
        // and sets a OnClickListener
        // which gives some messages
        // All OnClick method is assigned to the same function
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

        // Second Part for experiment

        // Getting the current display width
        int width = Utils.GetDisplayWidth(this);

        // Setting the listview width to half of its parent width size
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.getLayoutParams().width = width/2;

        // Maybe this information needs to be get from database ?
        String[] values = new String[] { "Peynir", "Yag", "Ekmek",
                "Salata", "Hiyar", "Su", "Hiyar", "Hiyar",
                "Zeytinyagi", "Kola" };

        // This is a custom adapter
        // We can just adapt it however we want
        // It is implemented in ListViewAdapter
        // This class extends from Adapter
        ListViewAdapter adapter = new ListViewAdapter(this, values);
        listView.setAdapter(adapter);
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
