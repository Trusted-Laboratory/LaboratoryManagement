package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.entity.News;

import java.util.List;

public interface NewsService {
    void save(News news);
    List<News> findAll();
    void addNews(News news);
}
