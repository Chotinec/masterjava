package ru.javaops.masterjava.persist.testData;


import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

public class CityTestData {
    public static City city1 = new City("spb", "Санкт-Петербург");;
    public static City city2 = new City("mow", "Москва");;
    public static City city3 =  new City("kiv", "Киев");;
    public static City city4 = new City("mnsk", "Минск");;
    public static List<City> FIRST4_CITIES = ImmutableList.of(city1, city2, city3, city4);;

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIRST4_CITIES.forEach(dao::insert);
        });
    }
}
