package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao extends AbstractDao {

    public Project insert(Project project) {
        if (project.isNew()) {
            int id = insertGeneratedId(project);
            project.setId(id);
        } else {
            insertWitId(project);
        }
        return project;
    }

    @SqlUpdate("INSERT INTO project (name, description) " +
            "VALUES (:name, :description)")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Project project);

    @SqlUpdate("INSERT INTO project (id, name, description) " +
            "VALUES (:id, :name, :description) ")
    abstract void insertWitId(@BindBean Project project);

    @SqlQuery("SELECT * FROM project ORDER BY name")
    abstract List<Project> getAll();

    @SqlBatch("INSERT INTO project (id, name, description) " +
            "VALUES (:id, :name, :description) ON CONFLICT DO NOTHING")
    public abstract int[] insertBatch(@BindBean List<Project> projects);

    @SqlUpdate("TRUNCATE project CASCADE")
    public abstract void clean();
}
