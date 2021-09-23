package com.lyun.laboratorymanagement.entity;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class News {
    private int news_id;
    private String title;
    private Date date;
    private Time time;
    private String content;
}
