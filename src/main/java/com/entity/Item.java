package com.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Item {
    private int id;
    private String name;
    private String description;
    private Date foundDate;
    private String location;
    private int userId;
    private String imagePath;
}