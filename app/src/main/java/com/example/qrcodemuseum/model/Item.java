package com.example.qrcodemuseum.model;

public class Item {

    private Integer id;
    private String title;
    private Integer year;
    private String description;

    public Item() {

    }

    public Item(Integer id, String title, Integer year, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
