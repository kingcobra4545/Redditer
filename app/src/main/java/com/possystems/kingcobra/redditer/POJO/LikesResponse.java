package com.possystems.kingcobra.redditer.POJO;

/**
 * Created by KingCobra on 23/03/18.
 */

public class LikesResponse {
    String likes, dislikes;
    public LikesResponse( String likes, String dislikes){
        this.likes = likes;
        this.dislikes = dislikes;
    }
    public String getLikes(){return likes;}
    public String getDislikes(){return dislikes;}
}
