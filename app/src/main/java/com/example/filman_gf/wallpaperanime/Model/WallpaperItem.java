package com.example.filman_gf.wallpaperanime.Model;

public class WallpaperItem {
    private String imageLink;
    private String categoryId;
    public long viewCount;

    public WallpaperItem(){
    }

    public WallpaperItem(String imageLink, String categoryId) {
        this.imageLink = imageLink;
        this.categoryId = categoryId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
