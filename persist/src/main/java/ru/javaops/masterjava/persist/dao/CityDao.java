package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import one.util.streamex.StreamEx;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao extends AbstractDao{

    public City insert(City city) {
        if (city.isNew()) {
            int id = insertGeneratedId(city);
            city.setId(id);
        } else {
            insertWitId(city);
        }
        return city;
    }

    public Map<String, City> cityMap() {
        return StreamEx.of(getAll()).toMap(City::getRef, c -> c);
    }

    @SqlUpdate("INSERT INTO city (ref, value) " +
            "VALUES (:ref, :value) ")
    @GetGeneratedKeys
    public abstract int insertGeneratedId(@BindBean City city);

    @SqlUpdate("INSERT INTO city (id, ref, value) " +
            "VALUES (:id, :ref, :value )")
    abstract void insertWitId(@BindBean City city);

    @SqlQuery("SELECT * FROM city ORDER BY value")
    abstract List<City> getAll();

    @SqlBatch("INSERT INTO city (id, ref, value) VALUES (:id, :ref, :value) ON CONFLICT DO NOTHING ")
    public abstract int[] insertBatch(@BindBean List<City> cities);

    @SqlUpdate("TRUNCATE city CASCADE")
    public abstract void clean();
}
