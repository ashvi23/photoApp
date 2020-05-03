package com.example.android26;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;
import model.Photo;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class photos_adaptor extends ArrayAdapter<Photo> {

    Context context;
    //private final String [] values;
    //private final ArrayList<Photo> images;

    public photos_adaptor(Context context, int resourceID, ArrayList<Photo> images){
        super(context, resourceID, images);
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        //this.values = values;
        //this.images = images;
    }
   /* @Override
    public int getCount() {
        return values.length;
    }*/

    @Override
    public long getItemId(int i) {
        return i;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;
        Photo photo = (Photo) getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.photo_list, parent, false);
            viewHolder.imgName = (TextView) convertView.findViewById(R.id.aNametxt);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.appIconIV);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.imgName.setText(photo.getCaption());
        viewHolder.image.setImageBitmap(photo.getBitmap());
        //viewHolder.icon.setImageResource(images.get(position));
        return convertView;
    }

    private static class ViewHolder {

        TextView imgName;
        ImageView image;

    }

}