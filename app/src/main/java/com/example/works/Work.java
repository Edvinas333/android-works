package com.example.works;

public class Work {

    private int id;
    private String comment;

    public Work() {

    }



    public Work(int id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Work(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
