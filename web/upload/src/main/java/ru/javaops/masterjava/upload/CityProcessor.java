package ru.javaops.masterjava.upload;


import lombok.val;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CityProcessor {

    private static CityDao cityDao = DBIProvider.getDao(CityDao.class);

    public Map<String, City> process(StaxStreamProcessor processor) throws XMLStreamException {
        val map = cityDao.cityMap();
        int id = cityDao.getSeqAndSkip(1);
        List<City> cities = new ArrayList<>();
        while (processor.doUntil(XMLEvent.START_ELEMENT, "City")) {
            String ref = processor.getAttribute("id");
            if (map.containsKey(ref)) {
                String name = processor.getText();
                cities.add(new City(id++, ref, name));
            }
        }

        cityDao.insertBatch(cities);
        return cityDao.cityMap();
    }
}
