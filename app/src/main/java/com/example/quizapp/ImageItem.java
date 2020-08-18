package com.example.quizapp;

public class ImageItem {

    private String name;
    private String description;
    private String path;

    public ImageItem(String name, String description, String path) {
        this.name = name;
        this.description = description;
        this.path = path;
    }

    public ImageItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
