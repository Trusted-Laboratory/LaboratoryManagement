package com.lyun.laboratorymanagement.dao;

import com.lyun.laboratorymanagement.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NewsDao extends BaseDao<News>{
    void addNews(@Param("news") News news);
    void updateNews(int id,String title,String content);
    News getById(int id);
    void deleteNews(int id);
}
