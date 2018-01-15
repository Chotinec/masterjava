package ru.javaops.masterjava.persist.dao;


import org.junit.*;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.persist.testData.CityTestData;

public class CityDaoTest extends AbstractDaoTest<CityDao> {

    public CityDaoTest() {
        super(CityDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        CityTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        CityTestData.setUp();
    }

    @After
    public void clean() {
        dao.clean();
    }

    @Test
    public void insertTest() {
        dao.clean();
        City city =  dao.insert(CityTestData.city1);
        Assert.assertEquals(city, CityTestData.city1);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(CityTestData.FIRST4_CITIES.size(), dao.getAll().size());
    }

    @Test
    public void insertBatch() throws Exception {
        dao.clean();
        dao.insertBatch(CityTestData.FIRST4_CITIES);
        Assert.assertEquals(CityTestData.FIRST4_CITIES.size(), dao.getAll().size());
    }

    @Test
    public void getSeqAndSkip() throws Exception {
        int seq1 = dao.getSeqAndSkip(5);
        int seq2 = dao.getSeqAndSkip(1);
        Assert.assertEquals(5, seq2 - seq1);
    }
}
