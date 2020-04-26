package com.example.android26;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
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
    private String m_Text="";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(home_screen);
        listView = findViewById(R.id.home_screen);
        //albumNames = getResources().get
        //albumNames = getResources().getStringArray(R.array.albums_array);
        albumNames = new ArrayList<String>();
       // albumNames.add("Test");
        adapter = new ArrayAdapter<>(this, R.layout.route, albumNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                handleAlbumClick(position);
            }
        });
    }
    public void handleSearch(View view){
        Intent intent = new Intent(this, search_activity.class);
        startActivity(intent);
    }
    public void handleAlbumClick(int position){
        Intent intent = new Intent(this, album_activity.class);
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
                }
                /*
                else add to list view
                 */
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void editAlbum(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        final int position = listView.getCheckedItemPosition();
        final String selectedAlbum = adapter.getItem(position);

        input.setHint("new name for album");
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
                    albumNames.set(position, m_Text);
                    //adapter.getItem(listView.getCheckedItemPosition()).;
                    //albumNames.add(m_Text);
                    adapter.notifyDataSetChanged();
                }
                /*
                else add to list view
                 */
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
    public void deleteAlbum(View view){
        //get position, delete album from adapter
    }

/*

 */
}
