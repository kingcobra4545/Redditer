package com.possystems.kingcobra.redditer.POJO;

/**
 * Created by KingCobra on 23/03/18.
 */

public class LikesResponse {
    String likes,id, dislikes;
    public LikesResponse(String id, String likes, String dislikes){
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    public String getID(){return id;}
    public String getLikes(){return likes;}
    public String getDislikes(){return dislikes;}
}
