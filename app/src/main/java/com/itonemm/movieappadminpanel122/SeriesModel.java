package com.itonemm.movieappadminpanel122;

public class SeriesModel {
    String Name;
    String Image;
    String Video;
    String Category;

    public SeriesModel(String name, String image, String video, String category) {

        Name = name;
        Image = image;
        Video = video;
        Category = category;
    }

    public SeriesModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
