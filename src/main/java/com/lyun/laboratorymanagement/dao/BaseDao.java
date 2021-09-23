package com.lyun.laboratorymanagement.dao;

import java.util.List;

public interface BaseDao<T> {
    void save(T t);
    List<T> findAll();
}
