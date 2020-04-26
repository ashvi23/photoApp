package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Photo implements Serializable{

    private static final long serialVersionUID = 3L;
    private String photoCaption;
    private String filePath;
    private LocalDate photoDate;
    private String photoDateString;
    private List<Tag> photoTags = new ArrayList<Tag>();
    private int row;
    private int col;
    /**
     * constructor
     * @param caption
     * @param path
     */
    public Photo(String caption, String path) {
        this.photoCaption = caption;
        this.filePath = path;
        this.photoTags = new ArrayList<Tag>();
        this.row=0;
        this.col=0;
    }

    /**
     * get photo path
     * @return filePath path to the photo
     */
    public String getPath() {
        return this.filePath;
    }
    /**
     * set photo path
     * @param path
     */
    public void setPath(String path) {
        this.filePath = path;
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

    /**
     * get photo date
     * @return photoDate
     */
    public LocalDate getDate() {
        return this.photoDate;
    }
    /**
     * set photo title
     * @throws ParseException
     */
    public void setDate() throws ParseException {
        File file = new File(filePath);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        long millis=file.lastModified();
      //  LocalDate currDate= LocalDate.ofInstant(Instant.ofEpochMilli(millis), TimeZone.getDefault().toZoneId());;
      //  this.photoDate=currDate;
        LocalDate d= LocalDate.now();
        this.photoDate = d;
        photoDateString = sdf.format(file.lastModified());

    }

}