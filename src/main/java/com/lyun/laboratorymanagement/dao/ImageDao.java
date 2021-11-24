package com.lyun.laboratorymanagement.dao;

import com.lyun.laboratorymanagement.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageDao extends BaseDao<Image>{
    Image getById(int id);
    Image getByImg(String img);
    void addImg(String img);
}
