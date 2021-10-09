package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.dao.NewsDao;
import com.lyun.laboratorymanagement.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class NewsServiceImpl implements NewsService{

    @Autowired
    NewsDao newsDao;

    @Override
    public void save(News news) {

    }

    @Override
    public List<News> findAll() {
        return newsDao.findAll();
    }

    @Override
    public void addNews(News news) {
        newsDao.addNews(news);
    }
}
