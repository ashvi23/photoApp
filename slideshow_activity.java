package com.example.android26;

import android.os.Bundle;

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
import static com.example.android26.R.layout.photo_display;
import static com.example.android26.R.layout.slideshow;

import model.Album;
import model.Photo;
import model.Serializer;
public class slideshow_activity extends AppCompatActivity {
    ArrayList<Album> allAlbums;
    Album currAlbum;
    int selectedAlbum;
    ArrayList<Photo> images;
    ListView lView;
    photos_adaptor lAdapter;
    Button addPhoto;
    Uri imageURI;
    int selectedPhoto;
    //ImageView tryphoto;
    Serializer serial;
    ImageView imageView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(slideshow);
        Intent intent = getIntent();
        serial = new Serializer(this.getApplicationInfo().dataDir);

        try {
            allAlbums = serial.readAlbums();
        } catch(IOException e){
            e.getStackTrace();
        } catch (ClassNotFoundException c){
            c.getStackTrace();
        }
        selectedAlbum = intent.getIntExtra("selectedAlbum", 0);
        selectedPhoto = intent.getIntExtra("selectedPhoto", 0);
        System.out.println("photo: "+ selectedPhoto + " album: "+ selectedAlbum);

        imageView = (ImageView) findViewById(R.id.slideshowImg);
        if(selectedPhoto>=0 && selectedAlbum >=0) {
            System.out.println("hi photo: "+ selectedPhoto + " album: "+ selectedAlbum);

            imageView.setImageBitmap(allAlbums.get(selectedAlbum).getPhotos().get(selectedPhoto).getBitmap());
        }
    }
    public void prev(View view){
        if(selectedPhoto == 0) {
            Toast toast = Toast.makeText(getApplicationContext(),"First photo in album.",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        else{
            selectedPhoto --;
            imageView.setImageBitmap(allAlbums.get(selectedAlbum).getPhotos().get(selectedPhoto).getBitmap());
        }

    }
    public void next(View view){
        if(selectedPhoto == allAlbums.get(selectedAlbum).getPhotos().size() -1) {
            Toast toast = Toast.makeText(getApplicationContext(),"Last photo in album.",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        else{
            selectedPhoto ++;
            imageView.setImageBitmap(allAlbums.get(selectedAlbum).getPhotos().get(selectedPhoto).getBitmap());
        }

    }
    public void back(View view){
        Intent intent = new Intent(this, photo_activity.class);
        intent.putExtra("selectedPhoto", selectedPhoto);
        intent.putExtra("selectedAlbum", selectedAlbum);
        startActivity(intent);
    }

}
