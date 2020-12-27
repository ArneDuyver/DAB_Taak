package be.kuleuven.csa.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class csaRepositoryJpaImplTest {
    public static final String DBNAME = "csaTest";
    private static final String INITIALISEMETHOD = "java";
    private Model model;
    private csaRepository repo;

    @Before
    public void setUp() {
        System.out.print("Initialising database ");
        if (INITIALISEMETHOD.equalsIgnoreCase("sql")){
            System.out.println("from sql ...");
            Model.initialiseStartingDatabaseSQL(DBNAME);
        } else if (INITIALISEMETHOD.equalsIgnoreCase("java")){
            this.model = new Model();
            model.initialiseStartingDatabaseJPA();
            repo = model.getRepo();
        } else {
            throw new RuntimeException("Choose a correct initialisation method 'sql' or 'java'.");
        }
    }
    @After
    public void tearDown() throws InterruptedException {
        if (INITIALISEMETHOD.equalsIgnoreCase("sql")){
            System.out.println("Deleting database ...");
            Model.deleteDatabase(DBNAME);
            Model.initialiseStartingDatabaseSQL(DBNAME);
        } else if (INITIALISEMETHOD.equalsIgnoreCase("java")){
            model.getSessionFactory().close();
            repo = null;
            model = null;
            Model.deleteDatabase(Model.DBNAME);
        }

    }

    //<editor-fold desc="PersistanceTests">
    @Test
    public void persistBoerderij_NoError() {
        Boerderij b = new Boerderij("testNaam","testAdres","testEmail","testRekeningNr");
        model.getRepo().saveObjectToDb(b);
    }
    @Test
    public void persistKlant_NoError() {
        Klant k = new Klant("testNaam","testAdres","testEmail","testTelefoonNr");
        model.getRepo().saveObjectToDb(k);
    }
    @Test
    public void persistProduct_NoError() {
        Product p = new Product("testNaam","testSoort");
        model.getRepo().saveObjectToDb(p);
    }
    @Test
    public void persistPakketbeschrijving_NoError() {
        Pakketbeschrijving p = new Pakketbeschrijving("testNaam",0,0);
        model.getRepo().saveObjectToDb(p);
    }
    @Test
    public void persistPakketInhoud_NoError() {
        PakketInhoud p = new PakketInhoud("testNaam");
        model.getRepo().saveObjectToDb(p);
    }
    @Test
    public void persistBevatObject_NoError() {
        PakketInhoud pi = new PakketInhoud("testNaam");
        Product p = new Product("testNaam","testSoort");
        Bevat b = new Bevat("testEenheid",0,pi,p);
        model.getRepo().saveObjectToDb(pi);
        model.getRepo().saveObjectToDb(p);
        model.getRepo().saveObjectToDb(b);
    }
    @Test
    public void persistVerkooptObject_NoError() {
        Boerderij b = new Boerderij("testNaam","testAdres","testEmail","testRekeningNr");
        Pakketbeschrijving p = new Pakketbeschrijving("testNaam",0,0);
        Verkoopt v = new Verkoopt(0,"testDatum",b,p);
        model.getRepo().saveObjectToDb(b);
        model.getRepo().saveObjectToDb(p);
        model.getRepo().saveObjectToDb(v);
    }
    @Test
    public void persistBehoortTotObject_NoError() {
        Boerderij b = new Boerderij("testNaam","testAdres","testEmail","testRekeningNr");
        Pakketbeschrijving p = new Pakketbeschrijving("testNaam",0,0);
        Verkoopt v = new Verkoopt(0,"testDatum",b,p);
        PakketInhoud pi = new PakketInhoud("testNaam");
        BehoortTot bt = new BehoortTot(0,v,pi);
        model.getRepo().saveObjectToDb(b);
        model.getRepo().saveObjectToDb(p);
        model.getRepo().saveObjectToDb(v);
        model.getRepo().saveObjectToDb(pi);
        model.getRepo().saveObjectToDb(bt);
    }
    @Test
    public void persistKooptObject_NoError() {
        Boerderij b = new Boerderij("testNaam","testAdres","testEmail","testRekeningNr");
        Pakketbeschrijving p = new Pakketbeschrijving("testNaam",0,0);
        Verkoopt v = new Verkoopt(0,"testDatum",b,p);
        Klant k = new Klant("testNaam","testAdres","testEmail","testTelefoonNr");
        Koopt koopt = new Koopt(v,k);
        model.getRepo().saveObjectToDb(b);
        model.getRepo().saveObjectToDb(p);
        model.getRepo().saveObjectToDb(v);
        model.getRepo().saveObjectToDb(k);
        model.getRepo().saveObjectToDb(koopt);
    }
    @Test
    public void persistHaaltAfObject_NoError() {
        Boerderij b = new Boerderij("testNaam","testAdres","testEmail","testRekeningNr");
        Pakketbeschrijving p = new Pakketbeschrijving("testNaam",0,0);
        Verkoopt v = new Verkoopt(0,"testDatum",b,p);
        PakketInhoud pi = new PakketInhoud("testNaam");
        BehoortTot bt = new BehoortTot(0,v,pi);
        Klant k = new Klant("testNaam","testAdres","testEmail","testTelefoonNr");
        HaaltAf h = new HaaltAf(bt,k);
        model.getRepo().saveObjectToDb(b);
        model.getRepo().saveObjectToDb(p);
        model.getRepo().saveObjectToDb(v);
        model.getRepo().saveObjectToDb(pi);
        model.getRepo().saveObjectToDb(bt);
        model.getRepo().saveObjectToDb(k);
        model.getRepo().saveObjectToDb(h);
    }
    //</editor-fold>

    //<editor-fold desc="getTests">
    @Test
    public void getBoerderijByName_NameUnknown_ReturnsEmptyList() {
        List<Boerderij> result = repo.getBoerderijByName("lalaland");
        Assert.assertNotNull("result should not be null", result);
        Assert.assertTrue("resultset should be zero", result.size() == 0);
    }
    @Test
    public void getBoerderijByName_Geeritshof_1() {
        List<Boerderij> result = repo.getBoerderijByName("Geeritshof");
        Assert.assertNotNull("result should not be null", result);
        Assert.assertTrue("resultset should be one", result.size() == 1);
    }
    @Test
    public void getBoerderij_4() {
        List<Boerderij> result = repo.getBoerderij();
        Assert.assertNotNull("result should not be null", result);
        Assert.assertTrue("resultset should be one", result.size() == 4);
    }

    //TODO: write more get tests

    //</editor-fold>

    //TODO: write extra tests
    @Test
    public void saveObjectToDb_TestBoerderij_emailNameShouldBe_testEmail() {
        Boerderij b = new Boerderij("testNaam","testAdres","testEmail","testRekeningNr");
        model.getRepo().saveObjectToDb(b);
        List<Boerderij> result = repo.getBoerderijByName("testNaam");
        Assert.assertNotNull("result should not be null", result);
        Assert.assertTrue("resultset should be one", result.size() == 1);
        Assert.assertTrue("result Item 0, email should be testEmail", result.get(0).getEmail().equalsIgnoreCase("testEmail"));
    }

    //TODO: example exception test
    @Test(expected = RuntimeException.class)
    public void exceptionTestExample() {
        throw new RuntimeException();
    }

}

