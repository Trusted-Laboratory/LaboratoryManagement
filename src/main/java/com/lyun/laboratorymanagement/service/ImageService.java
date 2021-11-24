package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();
    String getById(Integer id);
    Image getByImg(String img);
    void addImg(String img);
}
