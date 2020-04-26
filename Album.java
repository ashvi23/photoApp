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
    /**
     * get start date
     * @return
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * set start date
     * @throws ParseException
     */
    public void setStartDate() throws ParseException{
        startDate = findStartDate();
    }
    /**
     * get end date
     * @return
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * set end date
     * @throws ParseException
     */
    public void setEndDate() throws ParseException{
        endDate = findEndDate();
    }
    /**
     * returns range of date of photos
     * @return
     * @throws ParseException
     */
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
     * find the earliest photo
     * @return
     * @throws ParseException
     */
    public LocalDate findStartDate() throws ParseException{

        if(!this.photos.isEmpty()) {
            LocalDate tempStartDate=this.photos.get(0).getDate();
            for(int i=0; i< this.photos.size(); i++) {
                if(this.photos.get(i).getDate().isBefore(tempStartDate)) {
                    //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    //tempStartDate=sdf.parse(sdf.format(this.photos.get(i).getDate()));
                    tempStartDate=this.photos.get(i).getDate();
                }
            }
            return tempStartDate;
        }
        return null;
    }
    /**
     * finds the latest photo
     * @return
     * @throws ParseException
     */
    public LocalDate findEndDate() throws ParseException{

        if(!this.photos.isEmpty()) {
            LocalDate tempEndDate=this.photos.get(0).getDate();
            for(int i=0; i< this.photos.size(); i++) {
                if(this.photos.get(i).getDate().isAfter(tempEndDate)) {
                    //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    //tempEndDate=sdf.parse(sdf.format(this.photos.get(i).getDate()));
                    tempEndDate=this.photos.get(i).getDate();
                }
            }
            return tempEndDate;
        }
        return null;
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
                if(p.getPath().equals(this.photos.get(i).getPath())) {
                    System.out.println("add returned false");
                    return -1;
                }
            }
        }
        this.photos.add(p);
        return this.photos.indexOf(p);
    }
    /**
     * deletes photo from album
     * @param p
     */
    public void deletePhoto(Photo p) {
        if(p!=null) {
            for(int i=0; i<this.photos.size();i++) {
                if(p.getPath().equals(this.photos.get(i).getPath())) {
                    this.photos.remove(i);
                }
            }
        }

    }
}
