package model;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.List;
import model.SerializableBitmap;
import android.graphics.Bitmap;

public class Photo implements Serializable{

    private static final long serialVersionUID = 3L;
    private SerializableBitmap bitmap;
    private String photoCaption;
    private String photoDateString;
    private List<Tag> photoTags = new ArrayList<Tag>();
    private int row;
    private int col;
    /**
     * constructor
     * @param caption
     *
     */
    public Photo(String caption, Bitmap bitmap) {
        this.photoCaption = caption;
        this.bitmap = new SerializableBitmap(bitmap);
        //this.filePath = path;
        this.photoTags = new ArrayList<Tag>();
        this.row=0;
        this.col=0;
    }
    public String tagsToString(){
        if(photoTags.size() == 0){
            return "No tags added yet";
        }
        String tagString = "";
        for(int i =0; i< photoTags.size(); i++){
            tagString = tagString + photoTags.get(i).toString();
            if(i < photoTags.size()-1) {
                tagString = tagString +" , ";
            }
        }

        return tagString;
    }
    /**
     * get photo caption
     * @return photoTitle
     */
    public String getCaption() {
        return this.photoCaption;
    }
    /**
     * set photo caption
     * @param caption
     */
    public void setCaption(String caption) {
        this.photoCaption=caption;
    }

    /**
     * get the current coordinates of the given photo in the gridpane in openAlbum
     * @return coords, the row and column it's in
     */
    public int[] getCoords() {
        int[] coords= {this.row, this.col};
        return coords;
    }
    /**
     * get the current coordinates of the given photo in the gridpane in openAlbum
     * @param, the row and column it's currently in
     */
    public void setCoords(int[] coords) {
        this.row=coords[0];
        this.col=coords[1];
    }
    /**
     * get photo tags. will be used to set the tags field in the photo details
     * this will return a String list of all of the tags a photo has
     * iterate through tagList, get each tag, call toString on tag, append to a stringList
     * @return stringTagList
     */
    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<String>();
        if(this.photoTags!=null) {
            for(int i=0; i<this.photoTags.size(); i++) {
                String newTagVal=this.photoTags.get(i).getValue();
                String tag = this.photoTags.get(i).getTagtype();
                String newTag = tag + ": " + newTagVal;
                tags.add(newTag);
            }
        }
        return tags;
    }
    /**
     * returns the list of tags in type tag
     * @return
     */
    public List<Tag> getTagPairs() {
        return this.photoTags;
    }
    /**
     * get tags by the type ex. get all location tags
     * iterate through tags, if tagType.isEqual(type), add to String list
     * @return
     */
    public ArrayList<String> getTagtype(String type) {
        ArrayList<String> typeTags = new ArrayList<String>();
        if(this.photoTags!=null) {
            for(int i=0; i<this.photoTags.size(); i++) {
                if(type.equals(this.photoTags.get(i).getTagtype())) {
                    String newTag=this.photoTags.get(i).getValue();
                    typeTags.add(newTag);
                }
            }
        }

        return typeTags;
    }
    /**
     * add photo tags
     * @param tagtype
     * @param value
     */
    public void addTags(String tagtype, String value) {
        /*
         * If tag type doesn't exist, return false
         * If tag type exists, and value exists, return false
         * Else add tags to list
         */
        Tag newTag = new Tag(tagtype, value);
        this.photoTags.add(newTag);
    }
    /**
     * removes tag
     * @param i
     */
    public void removeTags(int i) {
        /*
         * If tag type doesn't exist, return false
         * If tag type exists, and value exists, return false
         * Else add tags to list
         */
        this.photoTags.remove(i);
    }

    public Bitmap getBitmap() {
        return bitmap.getBitmap();
    }
}