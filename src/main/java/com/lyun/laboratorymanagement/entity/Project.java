package com.lyun.laboratorymanagement.entity;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class Project {
    private int id;
    private String title;
    private String content;
    private Time time;
    private Date date;
    private String type;
    private String summarize;
}
