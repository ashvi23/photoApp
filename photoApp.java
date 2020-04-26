package com.example.android26;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;


import java.util.ArrayList;

import model.Album;

import static com.example.android26.R.layout.home_screen;

public class photoApp extends AppCompatActivity {
    private ListView listView;
   // private String[] albumNames;
    ArrayList<String> albumNames;
    ArrayList<Album> albums;
    private String m_Text="";
    ArrayAdapter<String> adapter;
    int pos;
    String albumClicked;
    Album albumLClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(home_screen);
        listView = findViewById(R.id.home_screen);
        //albumNames = getResources().get
        //albumNames = getResources().getStringArray(R.array.albums_array);
        albumNames = new ArrayList<String>();
        //albumNames.add("Test");
        adapter = new ArrayAdapter<>(this, R.layout.route, albumNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                albumClicked = (String) listView.getItemAtPosition(pos); // get item last clicked --> change this to hte album Object
            }
        });
    }
    public void handleSearch(View view){
        Intent intent = new Intent(this, search_activity.class);
        startActivity(intent);
    }
    public void addAlbum(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        input.setHint("album name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if(m_Text.equalsIgnoreCase("exists")){
                    new AlertDialog.Builder(builder.getContext())
                            .setMessage("An album with the title \"" + m_Text + "\" already exists. Please try a different title.")
                            .setPositiveButton("OK", null)
                            .show();

                    return;
                }
                else{
                    albumNames.add(m_Text);
                    adapter.notifyDataSetChanged();
                    pos = -1;
                    albumClicked = "";
                }
                /*
                else add to list view
                 */
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pos = -1;
                albumClicked = "";
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void editAlbum(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        if(pos< 0 ){
            Toast toast = Toast.makeText(getApplicationContext(),"No album selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        final String selectedAlbum = adapter.getItem(pos);
        input.setHint("Enter new name for album '"+ albumClicked + "'");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if(m_Text.equalsIgnoreCase("exists")){
                    new AlertDialog.Builder(builder.getContext())
                            .setMessage("An album with the title \"" + m_Text + "\" already exists. Please try a different title.")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                else{
                    albumNames.set(pos, m_Text);
                    adapter.notifyDataSetChanged();

                }
                pos = -1;
                albumClicked = "";
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pos = -1;
                albumClicked = "";
                dialog.cancel();
            }
        });

        builder.show();

    }
    public void deleteAlbum(View view){
        //get position, delete album from adapter
        if(pos<0){
            Toast toast = Toast.makeText(getApplicationContext(),"No album selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        albumNames.remove(albumClicked);
        adapter.notifyDataSetChanged();
        String deleted = "Deleted " + albumClicked;
        Toast toast = Toast.makeText(getApplicationContext(),deleted,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        pos = -1;
        albumClicked = "";

    }
    public void openAlbum(View view){
        if(pos < 0){
            Toast toast = Toast.makeText(getApplicationContext(),"No album selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            pos = -1;
            albumClicked = "";
            return;
        }
        // make it open the album at position
        Intent intent = new Intent(this, album_activity.class);
        startActivity(intent);
        pos = -1;
        albumClicked = "";
    }

/*

 */
}
