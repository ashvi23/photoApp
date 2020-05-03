package model;
/**
 * @author ashvi shah as2767
 * @author esther martens esm108
 */
import java.io.Serializable;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Photo;

public class Album implements Serializable{
    private static final long serialVersionUID = 678L;
    private ArrayList<Photo> photos;
    public String albumName;
    public LocalDate startDate;
    public LocalDate endDate;
    public int numPhotos;
    public LocalDate lastModified;
    public String dateRange;
    /**
     * constructor
     * @param name
     */
    public Album(String name) {
        this.albumName = name;
        this.photos= new ArrayList<Photo>();
        this.numPhotos = 0;
    }
    /**
     * setter for album name
     * @param name
     */
    public void setAlbumName(String name) {
        // if the name is unique --> no other albums should be named this
        albumName= name;
    }
    /**
     * getter alb name
     * @return
     */
    public String getAlbumName() {
        return albumName;
    }
    /**
     * get photos
     * @return list of photo obj
     */
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    public String getDateRange() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        //Date start=sdf.parse(sdf.format(this.startDate));
        //Date end = sdf.parse(sdf.format(this.endDate));
        LocalDate start=this.startDate;
        LocalDate end = this.endDate;
        if (start == null && end==null) {
            this.dateRange = "No date range";
        }
        else if(start == null && end!=null) {
            this.dateRange = "" +end;
        }
        else if(start != null && end==null) {
            this.dateRange = "" +start;
        }
        else {
            this.dateRange = start + " to " + end;
        }
        return dateRange;
    }

    /**
     * num photo in album
     * @return
     */
    public int getNumPhotos() {
        return numPhotos;
    }
    /**
     * sets number of photos
     * @param n
     */
    // n can be positive or negative if you want to increase/decrease #photos
    public void setNumPhotos(int n) {
        numPhotos = numPhotos + n;
    }
    /**
     * to string method for the details in albnum
     * @return
     */
    public String albumDetails() {
        //name, numberphotos, date modified, date range
        String albumDetails = albumName+ " "+ numPhotos+ " " + startDate + " "+ endDate;
        return albumDetails;
    }
    /**
     * adds photo in album
     * @param p
     * @return
     */
    public int addPhoto(Photo p) {
        if(p!=null) {
            for(int i=0; i<this.photos.size();i++) {
                if(p.getCaption().equals(this.photos.get(i).getCaption())) {
                    System.out.println("add returned false");
                    return -1;
                }
            }
        }
        this.photos.add(p);
        return this.photos.indexOf(p);
    }
    @Override
    public String toString(){
        return this.getAlbumName();
    }
    /**
     * deletes photo from album
     * @param p
     */
    public void deletePhoto(Photo p) {
        if(p!=null) {
            for(int i=0; i<this.photos.size();i++) {
                if(p.getCaption().equals(this.photos.get(i).getCaption())) {
                    this.photos.remove(i);
                }
            }
        }

    }
}
