package com.xiaok.androidqzone;

import java.io.Serializable;

public class VideoBrower implements Serializable {

    private static final long serialVersionUID = 1L;

    private int avatarId;
    private String username;
    private String date;
    private String videoDescripation;
    private String videoPath;
    private String position;


    public VideoBrower(int avatarId, String username, String date, String videoDescripation, String videoPath, String position) {
        this.avatarId = avatarId;
        this.username = username;
        this.date = date;
        this.videoDescripation = videoDescripation;
        this.videoPath = videoPath;
        this.position = position;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getVideoDescripation() {
        return videoDescripation;

    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getPosition() {
        return position;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public void setVideoDescripation(String videoDescripation) {
        this.videoDescripation = videoDescripation;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
