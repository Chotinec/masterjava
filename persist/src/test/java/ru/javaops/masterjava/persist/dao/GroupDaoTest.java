package ru.javaops.masterjava.persist.dao;


import org.junit.*;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.testData.GroupTestData;
import ru.javaops.masterjava.persist.testData.ProjectTestData;

public class GroupDaoTest extends AbstractDaoTest<GroupDao> {

    public GroupDaoTest() {
        super(GroupDao.class);
    }

    @BeforeClass
    public static void init() {
        ProjectTestData.init();
        ProjectTestData.setUp();
        GroupTestData.init();
    }

    @Before
    public void setUp() {
        GroupTestData.setUp();
    }

    @After
    public void clean() {
        dao.clean();
    }

    @Test
    public void insertTest() {
        dao.clean();
        Group group = dao.insert(GroupTestData.GROUP1);
        Assert.assertEquals(group, GroupTestData.GROUP1);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(GroupTestData.FIRST4_GROUPS.size(), dao.getAll().size());
    }

    @Test
    public void insertBatch() {
        dao.clean();
        dao.insertBatch(GroupTestData.FIRST4_GROUPS);
        Assert.assertEquals(GroupTestData.FIRST4_GROUPS.size(), dao.getAll().size());
    }

    @Test
    public void getSeqAndSkip() throws Exception {
        int seq1 = dao.getSeqAndSkip(5);
        int seq2 = dao.getSeqAndSkip(1);
        Assert.assertEquals(5, seq2 - seq1);
    }
}
