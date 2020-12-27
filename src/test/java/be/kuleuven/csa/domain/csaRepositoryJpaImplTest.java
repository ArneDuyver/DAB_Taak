package be.kuleuven.csa.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class csaRepositoryJpaImplTest {
    public static final String DBNAME = "csaTest";
    private static final String INITIALISEMETHOD = "sql";
    //private CsaRepository repo;


    @Before
    public void setUp() {
        System.out.print("Initialising database ");
        if (INITIALISEMETHOD.equalsIgnoreCase("sql")){
            System.out.println("from sql ...");
            Model.initialiseStartingDatabaseSQL(DBNAME);
        } else if (INITIALISEMETHOD.equalsIgnoreCase("java")){
            System.out.println("from java ...");
        } else {
            throw new RuntimeException("Choose a correct initialisation method 'sql' or 'java'.");
        }
        //TODO: finish setup for hibernate and jpa
//        System.out.println("Bootstrapping JPA/Hibernate...");
//        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
//        var entityManager = sessionFactory.createEntityManager();
//        System.out.println("Bootstrapping Repository...");
//        repo = new CsaRepositoryJpaImpl(entityManager);
    }

    @After
    public void tearDown() throws InterruptedException {
        //repo = null;
        System.out.println("Deleting database ...");
        Model.deleteDatabase(DBNAME);
    }

    //TODO: finish test
    @Test
    public void getStudentsByName_NameUnknown_ReturnsEmptyList() {
        System.out.println("*****TEST1******");
        /*List<Student> result = studentRepository.getStudentsByName("bloekiebla");
        Assert.assertNotNull("result should not be null", result);
        Assert.assertTrue("resultset should be zero", result.size() == 0);*/
    }

    //TODO: finish test
    @Test(expected = RuntimeException.class)
    public void test2() {
        throw new RuntimeException();
        /*// We weten dat student "Peeters" reeds in de DB zit met als key 456.
        studentRepository.saveNewStudent(new Student("PeetersCopy", "Jozefien", 456, true));*/
    }

}

