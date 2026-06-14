package com.mrsshubhangi.shubhgyanadmin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipContent {

    private String id;
    private String title;
    private String description;
    private String category;
    private String imageUrl;
    private String audioUrl;
    private String youtubeUrl;
    private boolean featured;
    private long views;
    private long likes;
    private long shareCount;
    private long createdAt;
}