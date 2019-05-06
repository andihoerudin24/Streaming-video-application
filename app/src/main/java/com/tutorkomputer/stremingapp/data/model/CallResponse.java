package com.tutorkomputer.stremingapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallResponse {

    public String getResponse() {
        return response;
    }
    @SerializedName("response")
    private  String response;


    public List<Video> getVideos() {
        return videos;
    }

    @SerializedName("videos")
    private List<Video> videos;


    public List<Category> getCategories() {
        return categories;
    }
    @SerializedName("categories")
    private List<Category> categories;


    public List<ListVideo> getList() {
        return list;
    }

    @SerializedName("list")
    private List<ListVideo> list;


    public List<Like> getLikes() {
        return likes;
    }

    @SerializedName("likes")
    private List<Like> likes;


}
