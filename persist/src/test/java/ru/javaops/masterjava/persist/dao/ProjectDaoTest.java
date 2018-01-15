package ru.javaops.masterjava.persist.dao;


import org.junit.*;
import ru.javaops.masterjava.persist.model.Project;
import ru.javaops.masterjava.persist.testData.ProjectTestData;

public class ProjectDaoTest extends AbstractDaoTest<ProjectDao> {

    public ProjectDaoTest() {
        super(ProjectDao.class);
    }

    @BeforeClass
    public static void init() {
        ProjectTestData.init();
    }

    @Before
    public void setUp() {
        ProjectTestData.setUp();
    }

    @After
    public void clean() {
        dao.clean();
    }

    @Test
    public void insert() {
        dao.clean();
        Project project = dao.insert(ProjectTestData.project1);
        Assert.assertEquals(project, ProjectTestData.project1);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(ProjectTestData.FIRST2_PROJECTS.size(), dao.getAll().size());
    }

    @Test
    public void insertBatch() throws Exception {
        dao.clean();
        dao.insertBatch(ProjectTestData.FIRST2_PROJECTS);
        Assert.assertEquals(ProjectTestData.FIRST2_PROJECTS.size(), dao.getAll().size());
    }

    @Test
    public void getSeqAndSkip() throws Exception {
        int seq1 = dao.getSeqAndSkip(5);
        int seq2 = dao.getSeqAndSkip(1);
        Assert.assertEquals(5, seq2 - seq1);
    }
}
