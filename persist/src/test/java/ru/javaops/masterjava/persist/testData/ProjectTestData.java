package ru.javaops.masterjava.persist.testData;


import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

public class ProjectTestData {
    public static Project project1 = new Project("topjava", "Topjava");;
    public static Project project2 = new Project("masterjava", "Masterjava");;
    public static List<Project> FIRST2_PROJECTS = ImmutableList.of(project1, project2);;

    public static void setUp() {
        ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIRST2_PROJECTS.forEach(dao::insert);
        });
    }
}
