package com.possystems.kingcobra.redditer.DataModels;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String header, subHeader,headerIconImageURL,timePostedAt,  id,mainImageURL,description,likes;
    public  DataModel(String header, String subHeader, String headerIconImageURL,String mainImageURL,
                      String timePostedAt, String postSourcedFrom, String description, String likes, String id){
        this.header = header;
        this.subHeader = subHeader;
        this.headerIconImageURL = headerIconImageURL;
        this.mainImageURL = mainImageURL;
        this.timePostedAt = timePostedAt;
        this.likes = likes;
        this.id = id;
        this.description = description;
    }
    public String getID() {if(id!=null) return id;
        return "";
    }
    public String getLikes() {if(likes!=null) return likes;
        return "";
    }
    public String getHeader() {
        if(header!=null)
            return header;
        return "";
    }
    public String getSubHeader() {if(subHeader!=null) return subHeader;
        return "";
    }
    public String getHeaderIconImageURL() {if(headerIconImageURL!=null) return headerIconImageURL;
        return "";
    }
    public String getTimePostedAt() {if(timePostedAt!=null) return timePostedAt;
        return "";
    }
    public String getDescription() {if(description!=null) return description;
        return "";
    }
    public String getMainImageURL() {if(mainImageURL!=null) return mainImageURL;
        return "";
    }
    public void setLikes(String likes){this.likes = likes;}
}