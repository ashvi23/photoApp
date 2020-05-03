package androidPhoto;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Album;
import model.Photo;
import model.Serializer;
import model.Tag;

public class search {
	
	private ArrayList<Photo> searchResults;
	private ArrayList<Album> allAlbums;
	private Serializer serial= new Serializer();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void search(ActionEvent event) throws IOException, ClassNotFoundException {
		searchResults.clear();
		//get tagValueField1
		String tagPair1 = tagValue1.getText();
		String[] tag1Split;
		//System.out.println("tagPair1: " +tagPair1);
		//get tagValueField2
		String tagPair2 = tagValue2.getText();
		String[] tag2Split;
		//System.out.println("tagPair2: " +tagPair2);
		//get radio button
		
		boolean andSel = and.isSelected();
		boolean orSel = or.isSelected();
		
		boolean tag1= false; // 1 tag search
		boolean tag2= false; // 2 tag search
		
		boolean valid1=false;
		boolean valid2= false;

		//input error checks everything is empty
		if(tagPair1.isBlank() && tagPair2.isBlank()) {
			Alert error = new Alert(AlertType.ERROR);
			//do toast things here
			/*error.setTitle("input error");
			error.setContentText("No search parameters added");
			error.show();*/
			and.setSelected(false);
			or.setSelected(false);
			return;
			//System.out.println("Error: must enter something");
		}
	
		// if tags are entered 
		if(!(tagPair2.isBlank()) || !(tagPair1.isBlank())) {
				
				//searching by 1 tag
				
				valid1 = isValidTagType(tagPair1);
				valid2 = isValidTagType(tagPair2);
				
				if(valid1) {
					tag1Split= tagPair1.split(":");
				}
				if(valid2) {
					tag2Split= tagPair2.split(":");
				}
				
				if((!(tagPair1.isBlank())&& tagPair2.isBlank() ) || (!(tagPair2.isBlank())&& tagPair1.isBlank()) ) {
					if(!tagPair1.isBlank()) {
						if(valid1 == true) {
							tag1=true;
						}
						else {
							Alert error = new Alert(AlertType.ERROR);
							//do toast? or a dialogue box
							/*error.setTitle("input error");
							error.setContentText("Please enter tag in valid format (tag=value) and enter tag keys that exist (person, location etc)");
							error.show();*/
							tagValue1.clear();
							tagValue2.clear();

							and.setSelected(false);
							or.setSelected(false);
							return;

						}
					}
					if(!tagPair2.isBlank()) {
						if(valid2 == true) {
							tag1=true;
						}
						else {
							Alert error = new Alert(AlertType.ERROR);
							//do toast or dialogue box
							/*error.setTitle("input error");
							error.setContentText("Please enter tag in valid format (tag=value) and enter tag keys that exist (person, location etc)");
							error.show();*/
							tagValue1.clear();
							tagValue2.clear();
							and.setSelected(false);
							or.setSelected(false);
							return;
						}
					}
					//tag1 = true;
				}
				if(!tagPair1.isBlank() && !tagPair2.isBlank()) {
					//two tags were entered, get radio selection
					if(valid1 == true && valid2==true) {
						if(tagPair1.equalsIgnoreCase(tagPair2) || (tag1Split[0].equals("location") && tag2Split[0].equals("location"))) {
							//do a toast thing/dialogue thing
							/*Alert error = new Alert(AlertType.ERROR);
							error.setTitle("input error");
							error.setContentText("Please enter distinct tags value pairs");
							error.show();*/
							tagValue1.clear();
							tagValue2.clear();
							return;
						}
						if(andSel == true || orSel == true) {
							tag2 = true;
						}
						else {
							Alert error = new Alert(AlertType.ERROR);
							error.setTitle("input error");
							error.setContentText("Please select a filter (and/or)");
							error.show();
							tagValue1.clear();
							tagValue2.clear();
							return;
						}
				}
					else {
						//enter toast or dialogue
						/*Alert error = new Alert(AlertType.ERROR);
						error.setTitle("input error");
						error.setContentText("Please enter tag in valid format (tag=value) and enter tag keys that exist (person, location etc)");
						error.show();*/
						tagValue1.clear();
						tagValue2.clear();
						return;
					}
				}
				
			}
			//either nothing was entered or only one tag was entered
			if((tag1==false && tag2==false) || (tag1 == true) ){
				and.setSelected(false);
				or.setSelected(false);
			}
			//the tags are valid and dates are valid
		
			Set<String> resultSet=new HashSet<String>();
			for(int i=0; i<allAlbums.getSize(); i++) {
				Album currAlbum=allAlbums.get(i);
				if(currAlbum.getPhotos().size()!=0) {
					for(int j=0; j<currAlbum.getPhotos().size(); j++) {
						Photo currPhoto=currAlbum.getPhotos().get(j);
						addOrPassPhoto(tagPair1, tagPair2, valid1, valid2, andSel, orSel, currPhoto, resultSet);
						}
				}
				refreshGrid(searchResults);
			}
			
			
		}
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean isValidTagType(String input){
		input= input.strip();
		String split[] = input.split(":");
		if(split.length != 2) {
			return false;
		}
		
		if(input.equalsIgnoreCase(“person”) || input.equalsIgnoreCase(“location”)){
			return true;
		}else {
			return false;
		}
	}

	public boolean isValidTagValue(Photo photo, String key, String value){
		Tag newTag = new Tag (key, value);
		ArrayList<Tag> tags = photo.tags;
		for(int i =0; i<tags.size(); i++){
			if (tags.getItem(i).equals(newTag)) {
				return false; 
			}
		}
		return true;
	}


	public boolean addOrPassPhoto(String pair1, String pair2, boolean valid1, boolean valid2, boolean andSel, boolean orSel, Photo photo, Set<String> resultSet) {
		Photo currPhoto=photo;
		
		if(valid1 && valid2) {
			int andOr=0;
			for(int i=0; i<currPhoto.getTagPairs().size();i++) {
				Tag currTag=currPhoto.getTagPairs().get(i);
				String split1[] = pair1.split(":");
				String split2[] = pair2.split(":");
				
				if(currTag.isTagEqual(split1[0], split1[1])==1 || currTag.isTagEqual(split2[0], split2[1])==1) {
						andOr++;
				}
			}
			if((andSel && andOr==2) || (orSel && andOr>0)) {
				if(resultSet.add(currPhoto.getPath())) {
				    searchResults.add(currPhoto);
				}
				return true;
			}
		}else if(valid1 || valid2){
			String pair;
			if(valid1) {
				pair=pair1;
			}else {
				pair=pair2;
			}
			for(int i=0; i<currPhoto.getTagPairs().size();i++) {
				Tag currTag=currPhoto.getTagPairs().get(i);
				String split[] = pair.split(":");
				if(currTag.isTagEqual(split[0], split[1])==1) {
					if(resultSet.add(currPhoto.getPath())) {
						searchResults.add(currPhoto);
					}
						return true;
				}
			}
		}else {
			if(resultSet.add(currPhoto.getPath())) {//this needs to be updated to getBitMap or something, and resultSet is the listview of all of the images
				searchResults.add(currPhoto);
			}
			return true;
		}
		return false;
	}
	

}
