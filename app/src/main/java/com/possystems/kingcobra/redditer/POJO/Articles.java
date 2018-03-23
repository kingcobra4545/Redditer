package com.possystems.kingcobra.redditer.POJO;

/**
 * Created by KingCobra on 22/03/18.
 */

public class Articles {
    //{"sub_header": "qwe",
    // "description": "qwdwqd",
    // "time_posted_at": "qwdqwe",
    // "header_icon_url": "qwewqe",
    // "title": "dqwd",
    // "updated_time": "2018-03-22 00:12:43.668658",
    // "header": "wqer",
    // "query_class": null,
    // "image_size": "qwdwq",
    // "query": null,
    // "id": 1}
    private String header;
    private String sub_header;
    private String title;
    private String description;
    private String header_icon_url;
    private String time_posted_at;
    private String likes;
    private String updatedTime;
    private String query_class;
    private String image_size;
    private String query;
    private int id;
    private String imageSize;


    public String getID(){

            return String.valueOf(id);

    }
    public String getLikes(){
        if(likes!=null)
            return likes;
        return "";
    }
    public String getTitle(){
        if(title!=null)
            return title;
        return "";
    }
    public String getDescription(){
        if(description!=null)
            return description;
        return "";
        }
    public String getHeader(){
        if(header!=null)
            return header;
        return "";
    }
    public String getSubHeader(){
        if(sub_header!=null)
            return sub_header;
        return "";
    }
    public String getImageURL(){
        if(header_icon_url!=null)
            return header_icon_url;
        return "";
    }
    public String getTimePublishedAt(){
        if(time_posted_at!=null)
            return time_posted_at;
        return "";
    }



    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setHeader(String header){this.header = header;}
    public void setSubHeader(String sub_header){this.sub_header = sub_header;}
    public void setImageURL(String  header_icon_url){this.header_icon_url = header_icon_url;}
    public void setTimePublishedAt(String time_posted_at){this.time_posted_at = time_posted_at;}
    public void setUpdatedTime(String updatedTime){this.updatedTime = updatedTime;}
    public void setQueryClass(String query_class){this.query_class = query_class;}
    public void setImageSize(String image_size){this.image_size = image_size;}
    public void setQuery(String query){this.query = query;}


}
