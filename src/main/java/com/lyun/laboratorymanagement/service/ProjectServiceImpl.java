package com.lyun.laboratorymanagement.service;

import com.lyun.laboratorymanagement.dao.ProjectDao;
import com.lyun.laboratorymanagement.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Project getById(Integer id) {
        return projectDao.getById(id);
    }

    @Override
    public void newProject(Project project) {
        projectDao.newProject(project);
    }

    @Override
    public void changeProject(int id, String title, String content, String type, String summarize) {
        Project project = projectDao.getById(id);
        if (title == null)title = project.getTitle();
        if (content == null)content = project.getContent();
        if (type == null)type = project.getType();
        if (summarize == null)summarize = project.getSummarize();
        projectDao.changeProject(id,title,content,type,summarize);
    }

    @Override
    public void delProject(int id) {
        projectDao.delProject(id);
    }
}
