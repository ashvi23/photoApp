package com.example.android26;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import model.Album;

public class search_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search by tags");
        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        input.setHint("tag:value");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input2.setHint("tag:value");
        input2.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout radioLayout = new LinearLayout(this);
        radioLayout.setOrientation(LinearLayout.HORIZONTAL);
        //radioLayout.addView(input);
        //radioLayout.addView(input2);
      //  String[] items = {"and","or"};
       // int checkedItem = 1;
        //builder.setSingleChoiceItems();
        layout.addView(input);
        //layout.addView(radioLayout);
        layout.addView(input2);
        builder.setView(layout); // Again this is a set method, not add
// Set up the buttons
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                error check
                get inputs and radio button and find all images with related tags
                 */
                return;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
    }
}
