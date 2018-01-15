package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.UserGroup;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class UserGroupDao extends AbstractDao {

    @SqlUpdate("INSERT INTO user_group (user_id, group_id) VALUES (:userId, :groupId)")
    abstract void insert(@BindBean UserGroup userGroup);

    @SqlQuery("SELECT * FROM user_group")
    abstract List<Group> getAll();

    @SqlBatch("INSERT INTO user_group (user_id, group_id) VALUES (:userId, :groupId) ON CONFLICT DO NOTHING")
    public abstract int[] insertBatch(@BindBean List<UserGroup> userGroups);

    @SqlUpdate("TRUNCATE user_group")
    public abstract void clean();
}
