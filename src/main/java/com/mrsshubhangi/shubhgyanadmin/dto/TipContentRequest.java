package com.mrsshubhangi.shubhgyanadmin.dto;

public record TipContentRequest(
        String title,
        String description,
        String category,
        String imageUrl,
        String audioUrl,
        String youtubeUrl,
        boolean featured
) {
}