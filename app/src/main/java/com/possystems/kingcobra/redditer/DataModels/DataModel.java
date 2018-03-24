package com.possystems.kingcobra.redditer.DataModels;

import java.io.Serializable;

/**
 * Created by KingCobra on 24/11/17.
 */

public class DataModel implements Serializable {

    String header, subHeader,headerIconImageURL,timePostedAt,postSourcedFrom, title,  id,mainImageURL;
    String description;
    String author;
    String url, imageURL;
    String publishedAT, likes;

    //dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
    public DataModel(){}
/*<<<<<<< Updated upstream
    public  DataModel(String header, String subHeader, String headerIconImageURL, String timePostedAt, String postSourcedFrom, String title){
=======*/
    public  DataModel(String header, String subHeader, String headerIconImageURL,String mainImageURL,
                      String timePostedAt, String postSourcedFrom, String description, String likes, String id){
//>>>>>>> Stashed changes
        this.header = header;
        this.subHeader = subHeader;
        this.headerIconImageURL = headerIconImageURL;
        this.mainImageURL = mainImageURL;
        this.timePostedAt = timePostedAt;
        this.postSourcedFrom = postSourcedFrom;
        this.likes = likes;
        this.id = id;
/*<<<<<<< Updated upstream
        this.title = title;
=======*/
        this.description = description;
//>>>>>>> Stashed changes

    }
    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }
    /*public  DataModel(String ID, String name, String author, String title, String description, String url, String publishedAT){
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAT = publishedAT;
        this.author = author;

    }
    public  DataModel(String ID, String name, String author, String title, String description, String url, String imageURL, String publishedAT){
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageURL = imageURL;
        this.publishedAT = publishedAT;
        this.author = author;

    }*/
    public String getID() {
        return id;
    }
    public String getLikes() {
        return likes;
    }
    public String getHeader() {
        return header;
    }
    public String getSubHeader() {
        return subHeader;
    }

    public String getHeaderIconImageURL() {
        return headerIconImageURL;
    }
    public String getTimePostedAt() {
        return timePostedAt;
    }
    public String getTitle() {
        return title;
    }
    /*public void setGetURL(String getURL) {
        this.getURL = getURL;
    }*/

    public String getAuthor() {
        if(this.author == null)
            return "Pvt";
        else if(this.author.equals("null"))
            return "Pvt";
        else
            return this.author;
    }
    //public void setJsonVersion(String jsonVersion) {this.jsonVersion = jsonVersion;}

    /*public String getDistanceTravelled() {
        return distanceTravelled;
    }
    public void setDistanceTravelled(String distanceTravelled) {this.distanceTravelled=distanceTravelled;}

    public String getDateClicked() {
        return dateClicked;
    }
    public void setDateClicked(String dateClicked) {this.dateClicked=dateClicked;}*/


    public String getDescription() {return description;}
    //public void setPhotoLocation(String  photoLocation) {this.photoLocation=photoLocation;}
    //public void setPhoto(Bitmap photo) {this.photo=photo;}
    //public Bitmap getPhoto() {return photo;}

    public String getUrl() {
        return url;
    }

    public String getImageURL() {
/*<<<<<<< Updated upstream
        return imageURL;
=======*/
        return headerIconImageURL;
//>>>>>>> Stashed changes
    }
    public String getMainImageURL() {
/*<<<<<<< Updated upstream
        return imageURL;
=======*/
        return mainImageURL;
//>>>>>>> Stashed changes
    }
    public String getPublishedAT() {
        return timePostedAt;
    }
    /*public  void setCategory(String category){
        this.category = category;
    }*/

    public void setLikes(String likes){this.likes = likes;}
}