package com.kondaurov.todojavamvp.common;

public class ToDoData {


    private int id;
    private String name;
    private String day;
    private String month;
    private String year;
    private String description;
    private int OK;
    private int everyday;

    public ToDoData() {
        id = 0;
        name = "";
        day = "";
        month = "";
        year = "";
        description = "";
        OK = 0;
        everyday = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOK() {
        return OK;
    }

    public void setOK(int OK) {
        this.OK = OK;
    }

    public int getEveryday() {
        return everyday;
    }

    public void setEveryday(int everyday) {
        this.everyday = everyday;
    }

    public ToDoData(int id, String name, String day, String month, String year, String description, int OK, int everyday) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
        this.OK = OK;
        this.everyday = everyday;
    }


}
