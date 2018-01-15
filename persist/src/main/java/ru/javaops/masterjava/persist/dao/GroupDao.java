package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao extends AbstractDao {

    public Group insert(Group group) {
        if (group.isNew()) {
            int id = insertGeneratedId(group);
            group.setId(id);
        } else {
            insertWitId(group);
        }
        return group;
    }

    @SqlUpdate("INSERT INTO groups (name, type, project_id)" +
            "VALUES (:name, CAST(:type AS GROUP_TYPE), :projectId)")
    @GetGeneratedKeys
    public abstract int insertGeneratedId(@BindBean Group group);

    @SqlUpdate("INSERT INTO groups (id, name, type, project_id)" +
            "VALUES (:id, :name, CAST(:type AS GROUP_TYPE), :projectId)")
    public abstract void insertWitId(@BindBean Group group);

    @SqlQuery("SELECT * FROM groups ORDER BY name")
    public abstract List<Group> getAll();

    @SqlBatch("INSERT INTO groups (id, name, type, project_id)" +
            "VALUES (:id, :name, CAST(:type AS GROUP_TYPE), :projectId) ON CONFLICT DO NOTHING")
    public abstract int[] insertBatch(@BindBean List<Group> groups);

    @SqlUpdate("TRUNCATE groups CASCADE")
    public abstract void clean();
}
