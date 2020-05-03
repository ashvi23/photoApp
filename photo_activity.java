package com.example.android26;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import java.lang.Object;
import android.content.res.Resources;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.example.android26.R.layout.photo_display;
import model.Album;
import model.Photo;
import model.Serializer;
import model.Tag;

public class photo_activity extends AppCompatActivity {
    ArrayList<Album> allAlbums;
    Album currAlbum;
    int selectedAlbum;
    ArrayList<Photo> images;
    ListView lView;
    photos_adaptor lAdapter;
    Button addPhoto;
    Uri imageURI;
    int selectedPhoto=-1;
    //ImageView tryphoto;
    Serializer serial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("hello!!! in photo");
        setContentView(photo_display);

        Intent intent = getIntent();
        serial = new Serializer(this.getApplicationInfo().dataDir);
        //currAlbum = (Album) intent.getSerializableExtra("albumClicked"); //gets the album object clicked
        try {
            allAlbums = serial.readAlbums();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        selectedPhoto = intent.getIntExtra("selectedPhoto", 0); // gets the position of the album object clicked
        selectedAlbum = intent.getIntExtra("selectedAlbum", 0); // gets the position of the album object clicked
        currAlbum = allAlbums.get(selectedAlbum);
        System.out.println("photo: "+ selectedPhoto + " album: "+ selectedAlbum);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(allAlbums.get(selectedAlbum).getPhotos().get(selectedPhoto).getBitmap());
        setTags();
    }

    public void addTag(View view){
        EditText mEdit;
        mEdit   = (EditText)findViewById(R.id.tagInput);
        String tagInput = mEdit.getText().toString();
        System.out.println("tag input is " + tagInput);
        //|| tagInput.isBlank()
        //tagInput.strip()
        if(tagInput.isEmpty()){
            return;
        }
        // Is the button now checked?
        RadioButton personBtn = (RadioButton) findViewById(R.id.personRadio);
        RadioButton locationBtn = (RadioButton) findViewById(R.id.locationRadio);
        //boolean checked = personBtn.isChecked();
        System.out.println("");
        // Check which radio button was clicked
        switch(personBtn.getId()) {
            case R.id.personRadio:
                if (personBtn.isChecked()) {
                    // Pirates are the best
                    Tag newTag = new Tag("person", tagInput);
                    List<Tag> t = currAlbum.getPhotos().get(selectedPhoto).getTagPairs();
                    for(int i = 0; i< t.size(); i++){
                        if(t.get(i).isTagEqual("person", tagInput) == 1){
                            Toast toast = Toast.makeText(getApplicationContext(),"Tag already exists",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }
                    }
                    currAlbum.getPhotos().get(selectedPhoto).addTags("person", tagInput);
                    setTags();
                    try{
                        serial.writeAlbum(currAlbum);
                        serial.writeAlbums(allAlbums);
                    } catch(IOException e){
                        e.getStackTrace();
                    }
                    break;
                }
            case R.id.locationRadio:
                if (locationBtn.isChecked()) {
                    //check if there is already a location tag
                    if(currAlbum.getPhotos().get(selectedPhoto).getTagtype("location").size() == 0){
                        currAlbum.getPhotos().get(selectedPhoto).addTags("location", tagInput);
                        setTags();
                        try{
                            serial.writeAlbum(currAlbum);
                            serial.writeAlbums(allAlbums);
                        } catch(IOException e){
                            e.getStackTrace();
                        }
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),"Location tag already exists",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                    break;
                }
            }

       /*
       check if tag value is not blank and not empty and does not already exist
        */

    }
    public void deleteTag(View view) {
        EditText mEdit;
        mEdit = (EditText) findViewById(R.id.tagInput);
        String value = mEdit.getText().toString();
        System.out.println("tag input is " + value);
        //|| tagInput.isBlank()
        //tagInput.strip()
        if (value.isEmpty()) {
            return;
        }
        // Is the button now checked?
        RadioButton personBtn = (RadioButton) findViewById(R.id.personRadio);
        RadioButton locationBtn = (RadioButton) findViewById(R.id.locationRadio);
        String key = "";
        switch (personBtn.getId()) {
            case R.id.personRadio:
                if (personBtn.isChecked()) {
                    key = "person";
                    break;
                }
            case R.id.locationRadio:
                if (locationBtn.isChecked()) {
                    //check if there is already a location tag
                    key = "location";
                    break;
                }
        }
             //System.out.println("key is "+ key);
            Photo currPhoto = currAlbum.getPhotos().get(selectedPhoto);
            List<Tag> curTagList = currPhoto.getTagPairs();
            for (int i = 0; i < curTagList.size(); i++) {
                //System.out.println("hi");
                    int n = curTagList.get(i).isTagEqual(key, value);
                    if (n == 1) {
                        currPhoto.removeTags(i);
                        setTags();
                        try {
                            serial.writeAlbum(currAlbum);
                            serial.writeAlbums(allAlbums);
                        } catch (IOException e) {
                            e.getStackTrace();
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), "Tag removed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }

                }
                Toast toast = Toast.makeText(getApplicationContext(), "Tag does not exist", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
        }

    public void back(View view){
        Intent intent = new Intent(this, album_activity.class);
        intent.putExtra("selectedAlbum", selectedAlbum);
        startActivity(intent);
    }
    public void slideshow(View view){
        Intent intent = new Intent(this, slideshow_activity.class);
        intent.putExtra("selectedPhoto", selectedPhoto);
        intent.putExtra("selectedAlbum", selectedAlbum);
        startActivity(intent);
    }

    public void setTags() {
        TextView mText;
        mText = (TextView)findViewById(R.id.tags);
        mText.setText(currAlbum.getPhotos().get(selectedPhoto).tagsToString());
    }
}
