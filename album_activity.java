package com.example.android26;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.Serializer;

public class album_activity extends AppCompatActivity {

    private static final int PICK_IMAGE =100 ;
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
        setContentView(R.layout.album_screen);
        Intent intent = getIntent();
        serial = new Serializer(this.getApplicationInfo().dataDir);
        //currAlbum = (Album) intent.getSerializableExtra("albumClicked"); //gets the album object clicked
        try {
            allAlbums = serial.readAlbums();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException c){
            c.printStackTrace();
        }
                //(ArrayList<Album>) intent.getSerializableExtra("albums");
        selectedAlbum = intent.getIntExtra("selectedAlbum", 0); // gets the position of the album object clicked
        System.out.println("ABOUT TO POPULATE LISTVIEW");
        currAlbum = allAlbums.get(selectedAlbum);
        System.out.println("1. Number of photos in new: "+ currAlbum.getNumPhotos());
        lView = (ListView) findViewById(R.id.photoThumbnailsList);
        System.out.println("JUST POPULATED LISTVIEW");
        lAdapter = new photos_adaptor(this, R.layout.photo_list, currAlbum.getPhotos()); // create photo adapter
        lAdapter.setNotifyOnChange(true);
        lView.setAdapter(lAdapter);
       // tryphoto = (ImageView) findViewById(R.id.tryphoto);
       // addPhoto = (Button) findViewById(R.id.addPhoto);
/*        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });*/

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lView.setItemChecked(position, true);
                selectedPhoto=position;
            }
        });
    }

    public void back(View view){
        Intent intent = new Intent(this, photoApp.class);
        startActivity(intent);
    }

    public void addPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, PICK_IMAGE);
        //Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            if (data != null) {
                imageURI = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String caption = getFileName(imageURI);
                Photo photo = new Photo(caption, bitmap);
                for (int index = 0; index < lAdapter.getCount(); index++)
                    if (photo.equals(lAdapter.getItem(index))) {
                        new AlertDialog.Builder(this)
                                .setMessage("Photo named\"" + caption + "\" already exists")
                                .setPositiveButton("OK", null)
                                .show();

                        return;
                    }

                currAlbum.addPhoto(photo);
                currAlbum.setNumPhotos(1);
                lAdapter.notifyDataSetChanged();

                //serialize here
                try {
                    serial.writeAlbum(currAlbum);
                    serial.writeAlbums(allAlbums);

                } catch(IOException e) {
                    e.printStackTrace();
                }
                System.out.println("2. Number of photos in new: "+ currAlbum.getNumPhotos());
            }
        }
        }
    public void deletePhoto(View view){
        //final photos_Adaptor adapter = (PhotoAdapter) listView.getAdapter();
        System.out.println("first delete");
        if (lAdapter.getCount() == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("This checkedAlbum does not have any photos.")
                    .setPositiveButton("OK", null)
                    .show();

            return;
        }
        System.out.println("no hptos checked");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int checkedItemPosition = selectedPhoto;
        final Photo checkedPhoto = lAdapter.getItem(checkedItemPosition);
        builder.setMessage("Are you sure you want to remove \"" +checkedPhoto.getCaption()+ "\"?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("before remove");

                        lAdapter.remove(checkedPhoto);
                        System.out.println("after removed");

                        //DataSaver.saveData(albums, path);
                        try{
                            serial.writeAlbum(currAlbum);
                            serial.writeAlbums(allAlbums);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        lView.setItemChecked(checkedItemPosition, true);
                    }
                });

        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();

        /*if(selectedPhoto <0){
            Toast toast = Toast.makeText(getApplicationContext(),"No album selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        else {
            //currAlbum.getPhotos().remove(selectedPhoto);
            final int checkedItemPosition = lView.getCheckedItemPosition();
            Photo toDelete= lAdapter.getItem(checkedItemPosition);
            lAdapter.remove(toDelete);
            lView.setItemChecked(checkedItemPosition, true);

            //lAdapter.notifyDataSetChanged();
            /*try{
              serial.writeAlbum(currAlbum);
              serial.writeAlbums(allAlbums);
            }catch (IOException e){
                e.printStackTrace();
            }
            String delete = currAlbum.getPhotos().get(selectedPhoto).getCaption() + " deleted";
            Toast toast = Toast.makeText(getApplicationContext(), delete, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            selectedPhoto = -1;
            return;
        }*/
    }
    public void movePhoto(View view){
        if(selectedPhoto<0){
            Toast toast = Toast.makeText(getApplicationContext(),"No photo selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        else{
            moveAlert(view);
            boolean foundAlbum=false;
        }
    }
    public void displayPhoto(View view){
        System.out.println("HELLO from display button");
        if(selectedPhoto<0){
            Toast toast = Toast.makeText(getApplicationContext(),"No photo selected",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        else{
            System.out.println("HELLO from display button else statement");
            Intent intent = new Intent(this, photo_activity.class);
            intent.putExtra("selectedPhoto", selectedPhoto);
            intent.putExtra("selectedAlbum", selectedAlbum);
            startActivity(intent);
        }
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    public void moveAlert(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        input.setHint("album name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        final int checkedItemPosition = selectedPhoto;
        final Photo checkedPhoto = lAdapter.getItem(checkedItemPosition);
        builder.setMessage("Where do you want to move  \"" +checkedPhoto.getCaption()+ "\"?");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        boolean foundAlbum;
                        String newAlbumName = input.getText().toString();
                        for (int i = 0; i < allAlbums.size(); i++) {
                            Album newAlbum = allAlbums.get(i);
                            if (newAlbumName.equals(allAlbums.get(i).getAlbumName()) && (!newAlbumName.equals(currAlbum.getAlbumName()))) {
                                foundAlbum = true;
                                if (doesPhotoExist(currAlbum.getPhotos().get(selectedPhoto).getCaption(), newAlbum) == true) {
                                    Toast toast = Toast.makeText(getApplicationContext(),"Image already exists in requested album.",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    return;

                                }
                                //the album name is valid and the photo does no currently exist in it
                                newAlbum.addPhoto(currAlbum.getPhotos().get(selectedPhoto));
                                currAlbum.deletePhoto(currAlbum.getPhotos().get(selectedPhoto));
                                lAdapter.remove(checkedPhoto);
                                currAlbum.setNumPhotos(-1);
                                newAlbum.setNumPhotos(1);
                                try {
                                    serial.writeAlbum(currAlbum);
                                    serial.writeAlbums(allAlbums);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                lView.setItemChecked(checkedItemPosition, true);
                                Toast toast = Toast.makeText(getApplicationContext(),"Image move to requested album.",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            } else {
                                if (newAlbumName.equals(allAlbums.get(i).getAlbumName()) && (newAlbumName.equals(currAlbum.getAlbumName()))) {
                                    Toast toast = Toast.makeText(getApplicationContext(),"Image already exists in requested album.",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    return;
                                }
                            }

                        }
                        Toast toast = Toast.makeText(getApplicationContext(),"Album not found.",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;

                    }});

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }
    public boolean doesPhotoExist(String currPath, Album newAlbum) {
        for(int i =0;i<newAlbum.getPhotos().size();i++) {
            if(currPath.equalsIgnoreCase(newAlbum.getPhotos().get(i).getCaption())) {
                return true;
            }
        }
        return false;
    }
    }