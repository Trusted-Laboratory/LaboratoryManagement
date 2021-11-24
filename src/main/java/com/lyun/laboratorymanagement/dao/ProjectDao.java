package com.lyun.laboratorymanagement.dao;

import com.lyun.laboratorymanagement.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectDao extends BaseDao<Project>{

    Project getById(int id);
    void newProject(@Param("project") Project project);
    void changeProject(int id, String title, String content, String type, String summarize);
    void delProject(int id);
}
