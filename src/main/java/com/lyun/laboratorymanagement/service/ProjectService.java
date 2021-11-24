package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project getById(Integer id);
    void newProject(Project project);
    void changeProject(int id,String title,String content,String type,String summarize);
    void delProject(int id);
}
