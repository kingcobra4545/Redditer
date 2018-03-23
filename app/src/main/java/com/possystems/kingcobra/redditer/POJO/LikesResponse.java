package com.possystems.kingcobra.redditer.POJO;

/**
 * Created by KingCobra on 23/03/18.
 */

public class LikesResponse {
    String likes,id;
    public LikesResponse(String id, String likes){
        this.id = id;
        this.likes = likes;
    }
    public String getID(){return id;}
    public String getLikes(){return likes;}
}
