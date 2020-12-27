package be.kuleuven.csa.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class Model {
    public static final String TAG = "Model: ";
    public static final String DBNAME = "csa.db";
    //Data members
    private EntityManagerFactory sessionFactory;
    private EntityManager entityManager;
    private csaRepository repo;

    public static void main(String[] args) {
        Model model = new Model();
        model.initialiseStartingDatabaseJPA();
        initialiseStartingDatabaseSQL("csaSqlTest");
    }

    public Model(){
        System.out.println("Bootstrapping JPA/Hibernate...");
        sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        entityManager = sessionFactory.createEntityManager();
        System.out.println("Bootstrapping Repository...");
        repo = new csaRepositoryJpaImpl(entityManager);
    }
    public void initialiseStartingDatabaseJPA(){
        //<editor-fold desc="Instantiating objects">
            //<editor-fold desc="Boerderijen">
        var boerderij_1 = new Boerderij("Geeritshof","Boshoeve 11, 3900 Peer","geeritsEnZonen@hotmail.com","BE37 7390 1252 5428");
        var boerderij_2 = new Boerderij("Koekoekshof","Laarderweg 9, 3900 Peer","koekoekshof@hotmail.com","BE37 6201 5879 2587");
        var boerderij_3 = new Boerderij("Sezoenshof","apotherkerhendrixstraat 13, 3900 Peer","sezoenshof@hotmail.com","BE69 2222 4444 7777");
        var boerderij_4 = new Boerderij("Woutershof","Universiteitslaan 12, 3650 Hasselt","woutershof@gmail.com","BE36 5420 4796 4562");
            //</editor-fold>
            //<editor-fold desc="Klanten">
        var klant_1 = new Klant("Dirk Waterslagers","Boshoeve 11, 3900 Peer","dirk@hotmail.com","0486652354");
        var klant_2 = new Klant("Jef Maesen","Laarderweg 9, 3900 Peer ","jef@hotmail.com","0466956824");
        var klant_3 = new Klant("Jos Winters","Smeetshofweg z/n, 3900 Peer ","jos@gmail.com","0476585952");
        var klant_4 = new Klant("Thomas Allard","Paardendries 19, 9300 Aalst ","thomas@hotmail.com","0476569873");
        var klant_5 = new Klant("Arne Peeters","Hommelbeek 11, 3980 Tessenderlo ","arne@hotmail.com","0472589633");
            //</editor-fold>
            //<editor-fold desc="Producten">
        var product_1 = new Product("Broccoli","groenten");
        var product_2 = new Product("Spruitjes","groenten");
        var product_3 = new Product("Seranoham","varkensvlees");
        var product_4 = new Product("Hyacint","bloemen");
        var product_5 = new Product("Bloedappelsienen","fruit");
        var product_6 = new Product("Wortelen","groenten");
        var product_7 = new Product("Kipfilets","kippenvlees");
        var product_8 = new Product("Appels","groenten");
        var product_9 = new Product("Filet pur","rundsvlees");
            //</editor-fold>
            //<editor-fold desc="Pakketbeschrijvingen">
        var pakketbeschrijving_1 = new Pakketbeschrijving("XS",0,1);
        var pakketbeschrijving_2 = new Pakketbeschrijving("S",0,2);
        var pakketbeschrijving_3 = new Pakketbeschrijving("M",2,2);
        var pakketbeschrijving_4 = new Pakketbeschrijving("L",3,2);
        var pakketbeschrijving_5 = new Pakketbeschrijving("XL",5,2);
            //</editor-fold>
            //<editor-fold desc="Pakketinhouden">
        var pakketInhoud_1 = new PakketInhoud(null);
        var pakketInhoud_2 = new PakketInhoud(null);
        var pakketInhoud_3 = new PakketInhoud(null);
        var pakketInhoud_4 = new PakketInhoud(null);
            //</editor-fold>
            //<editor-fold desc="Bevat objecten">
                //Pakket 1:
        var bevat_1 = new Bevat("gram",300,pakketInhoud_1,product_1); //Broccoli
        var bevat_2 = new Bevat("stuks",2,pakketInhoud_1,product_5); //Bloedappelsien
        var bevat_3 = new Bevat("gram",200,pakketInhoud_1,product_9); //Filet pur
                //Pakket 2:
        var bevat_4 = new Bevat("kilogram",2,pakketInhoud_2,product_2); //Broccoli
        var bevat_5 = new Bevat("kilogram",1,pakketInhoud_2,product_6); //Broccoli
        var bevat_6 = new Bevat("gram",400,pakketInhoud_2,product_9); //Filet pur
                //Pakket 3:
        var bevat_7 = new Bevat("gram",100,pakketInhoud_3,product_3); //Broccoli
        var bevat_8 = new Bevat("gram",50,pakketInhoud_3,product_7); //Broccoli
        var bevat_9 = new Bevat("gram",400,pakketInhoud_3,product_9); //Filet pur
                //Pakket 4:
        var bevat_10 = new Bevat("stuks",4,pakketInhoud_4,product_4); //Broccoli
        var bevat_11 = new Bevat("stuks",4,pakketInhoud_4,product_8); //Broccoli
        var bevat_12 = new Bevat("gram",600,pakketInhoud_4,product_9); //Filet pur
            //</editor-fold>
            //<editor-fold desc="Verkoopt objecten">
                //Boerderij 1:
        var verkoopt_1 = new Verkoopt(360,"1 januari",boerderij_1,pakketbeschrijving_2);
        var verkoopt_2 = new Verkoopt(700,"1 januari",boerderij_1,pakketbeschrijving_3);
                //Boerderij 2:
        var verkoopt_3 = new Verkoopt(650,"1 januari",boerderij_2,pakketbeschrijving_3);
                //Boerderij 3:
        var verkoopt_4 = new Verkoopt(560,"1 januari",boerderij_3,pakketbeschrijving_2);
        var verkoopt_5 = new Verkoopt(840,"1 januari",boerderij_3,pakketbeschrijving_3);
        var verkoopt_6 = new Verkoopt(1200,"1 januari",boerderij_3,pakketbeschrijving_4);
                //Boerderij 4:
        var verkoopt_7 = new Verkoopt(750,"1 januari",boerderij_4,pakketbeschrijving_3);
        var verkoopt_8 = new Verkoopt(1050,"1 januari",boerderij_4,pakketbeschrijving_4);
            //</editor-fold>
            //<editor-fold desc="Behoort tot objecten">
                //weken 1
        var behoortTot_1 = new BehoortTot(1,verkoopt_1,pakketInhoud_1);
        var behoortTot_2 = new BehoortTot(1,verkoopt_2,pakketInhoud_2);
        var behoortTot_3 = new BehoortTot(1,verkoopt_3,pakketInhoud_3);
        var behoortTot_4 = new BehoortTot(1,verkoopt_4,pakketInhoud_4);
        var behoortTot_5 = new BehoortTot(1,verkoopt_5,pakketInhoud_1);
        var behoortTot_6 = new BehoortTot(1,verkoopt_6,pakketInhoud_2);
        var behoortTot_7 = new BehoortTot(1,verkoopt_7,pakketInhoud_3);
        var behoortTot_8 = new BehoortTot(1,verkoopt_8,pakketInhoud_4);
                //weken 2
        var behoortTot_9 = new BehoortTot(2,verkoopt_1,pakketInhoud_2);
        var behoortTot_10 = new BehoortTot(2,verkoopt_2,pakketInhoud_3);
        var behoortTot_11 = new BehoortTot(2,verkoopt_3,pakketInhoud_4);
        var behoortTot_12 = new BehoortTot(2,verkoopt_4,pakketInhoud_1);
        var behoortTot_13 = new BehoortTot(2,verkoopt_5,pakketInhoud_3);
        var behoortTot_14 = new BehoortTot(2,verkoopt_6,pakketInhoud_4);
        var behoortTot_15 = new BehoortTot(2,verkoopt_7,pakketInhoud_1);
        var behoortTot_16 = new BehoortTot(2,verkoopt_8,pakketInhoud_2);
            //</editor-fold
            //<editor-fold desc="Koopt objecten">
        var koopt_1 = new Koopt(verkoopt_1,klant_1);
        var koopt_2 = new Koopt(verkoopt_3,klant_2);
        var koopt_3 = new Koopt(verkoopt_4,klant_3);
        var koopt_4 = new Koopt(verkoopt_1,klant_4);
        var koopt_5 = new Koopt(verkoopt_7,klant_4);
            //</editor-fold>
            //<editor-fold desc="HaaltAf objecten">
        var haaltAf_1 = new HaaltAf(behoortTot_1,klant_1);
        var haaltAf_2 = new HaaltAf(behoortTot_9,klant_1);
        var haaltAf_3 = new HaaltAf(behoortTot_3,klant_2);
        var haaltAf_4 = new HaaltAf(behoortTot_11,klant_2);
        var haaltAf_5 = new HaaltAf(behoortTot_4,klant_3);
        var haaltAf_6 = new HaaltAf(behoortTot_12,klant_3);
        var haaltAf_7 = new HaaltAf(behoortTot_1,klant_4);
        var haaltAf_8 = new HaaltAf(behoortTot_9,klant_4);
        var haaltAf_9 = new HaaltAf(behoortTot_7,klant_4);
        var haaltAf_10 = new HaaltAf(behoortTot_15,klant_4);
            //</editor-fold>
        //</editor-fold>

        //<editor-fold desc="Persisting objects">
        repo.saveObjectToDb(boerderij_1);
        repo.saveObjectToDb(boerderij_2);
        repo.saveObjectToDb(boerderij_3);
        repo.saveObjectToDb(boerderij_4);

        repo.saveObjectToDb(klant_1);
        repo.saveObjectToDb(klant_2);
        repo.saveObjectToDb(klant_3);
        repo.saveObjectToDb(klant_4);
        repo.saveObjectToDb(klant_5);

        repo.saveObjectToDb(product_1);
        repo.saveObjectToDb(product_2);
        repo.saveObjectToDb(product_3);
        repo.saveObjectToDb(product_4);
        repo.saveObjectToDb(product_5);
        repo.saveObjectToDb(product_6);
        repo.saveObjectToDb(product_7);
        repo.saveObjectToDb(product_8);
        repo.saveObjectToDb(product_9);

        repo.saveObjectToDb(pakketbeschrijving_1);
        repo.saveObjectToDb(pakketbeschrijving_2);
        repo.saveObjectToDb(pakketbeschrijving_3);
        repo.saveObjectToDb(pakketbeschrijving_4);
        repo.saveObjectToDb(pakketbeschrijving_5);

        repo.saveObjectToDb(pakketInhoud_1);
        repo.saveObjectToDb(pakketInhoud_2);
        repo.saveObjectToDb(pakketInhoud_3);
        repo.saveObjectToDb(pakketInhoud_4);

        repo.saveObjectToDb(bevat_1);
        repo.saveObjectToDb(bevat_2);
        repo.saveObjectToDb(bevat_3);
        repo.saveObjectToDb(bevat_4);
        repo.saveObjectToDb(bevat_5);
        repo.saveObjectToDb(bevat_6);
        repo.saveObjectToDb(bevat_7);
        repo.saveObjectToDb(bevat_8);
        repo.saveObjectToDb(bevat_9);
        repo.saveObjectToDb(bevat_10);
        repo.saveObjectToDb(bevat_11);
        repo.saveObjectToDb(bevat_12);

        repo.saveObjectToDb(verkoopt_1);
        repo.saveObjectToDb(verkoopt_2);
        repo.saveObjectToDb(verkoopt_3);
        repo.saveObjectToDb(verkoopt_4);
        repo.saveObjectToDb(verkoopt_5);
        repo.saveObjectToDb(verkoopt_6);
        repo.saveObjectToDb(verkoopt_7);
        repo.saveObjectToDb(verkoopt_8);

        repo.saveObjectToDb(behoortTot_1);
        repo.saveObjectToDb(behoortTot_2);
        repo.saveObjectToDb(behoortTot_3);
        repo.saveObjectToDb(behoortTot_4);
        repo.saveObjectToDb(behoortTot_5);
        repo.saveObjectToDb(behoortTot_6);
        repo.saveObjectToDb(behoortTot_7);
        repo.saveObjectToDb(behoortTot_8);
        repo.saveObjectToDb(behoortTot_9);
        repo.saveObjectToDb(behoortTot_10);
        repo.saveObjectToDb(behoortTot_11);
        repo.saveObjectToDb(behoortTot_12);
        repo.saveObjectToDb(behoortTot_13);
        repo.saveObjectToDb(behoortTot_14);
        repo.saveObjectToDb(behoortTot_15);
        repo.saveObjectToDb(behoortTot_16);

        repo.saveObjectToDb(koopt_1);
        repo.saveObjectToDb(koopt_2);
        repo.saveObjectToDb(koopt_3);
        repo.saveObjectToDb(koopt_4);
        repo.saveObjectToDb(koopt_5);

        repo.saveObjectToDb(haaltAf_1);
        repo.saveObjectToDb(haaltAf_2);
        repo.saveObjectToDb(haaltAf_3);
        repo.saveObjectToDb(haaltAf_4);
        repo.saveObjectToDb(haaltAf_5);
        repo.saveObjectToDb(haaltAf_6);
        repo.saveObjectToDb(haaltAf_7);
        repo.saveObjectToDb(haaltAf_8);
        repo.saveObjectToDb(haaltAf_9);
        repo.saveObjectToDb(haaltAf_10);
        //</editor-fold>
    }

    public static void resetAutoIncrementValue() {
        try {
            final String ConnectionString = "jdbc:sqlite:"+DBNAME;
            //Establish connection with the database
            Connection connection;
            try {
                connection = DriverManager.getConnection(ConnectionString);
                connection.setAutoCommit(false);

            } catch (Exception e) {
                System.out.println(TAG+"resetAutoIncrementValue: Db connection handle failure");
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            //reset hibernate_sequence -> next_val
            var sql = "UPDATE hibernate_sequence SET next_val= 1;";
            //System.out.println(sql);
            var s = connection.createStatement();
            s.executeUpdate(sql);
            s.close();

            //verify next_val contents
            var s2 = connection.createStatement();
            var result = s2.executeQuery("SELECT next_val FROM hibernate_sequence");
            assert result.getInt("next_val") == 1;
            connection.close();

        } catch (Exception e) {
            System.out.println(TAG+"resetAutoIncrementValue: Error while initialising db or verifying table content");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void initialiseStartingDatabaseSQL(String dbName) {
        try {
            final String ConnectionString = "jdbc:sqlite:"+dbName+".db";
            //Establish connection with the database
            Connection connection;
            try {
                connection = DriverManager.getConnection(ConnectionString);
                connection.setAutoCommit(false);

            } catch (Exception e) {
                System.out.println(TAG+"initialiseStartingDatabaseSQL: Db connection handle failure");
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            //initTables
            var sql = new String(Files.readAllBytes(Paths.get("./src/main/resources/dbcreate.sql")));
            //System.out.println(sql);
            var s = connection.createStatement();
            s.executeUpdate(sql);
            s.close();

            //verifyTableContents
            var s2 = connection.createStatement();
            var result = s2.executeQuery("SELECT COUNT(*) as cnt FROM boerderij");
            assert result.getInt("cnt") == 4;
            connection.close();

        } catch (Exception e) {
            System.out.println(TAG+"initialiseStartingDatabaseSQL: Error while initialising db or verifying table content");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static void deleteDatabase(String dbName) {
        try {
            File f= new File("./"+dbName+".db");
            if(f.delete()) {
                System.out.println(f.getName() + " deleted");
            } else {
                System.out.println("failed");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static Boolean isPersistableObject(Object object){
        return (object instanceof BehoortTot ||
                object instanceof Bevat ||
                object instanceof Boerderij ||
                object instanceof HaaltAf ||
                object instanceof Klant ||
                object instanceof Koopt ||
                object instanceof Pakketbeschrijving ||
                object instanceof PakketInhoud ||
                object instanceof Product ||
                object instanceof Verkoopt);
    }

    public EntityManagerFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public csaRepository getRepo() {
        return repo;
    }

    public void setRepo(csaRepository repo) {
        this.repo = repo;
    }
}
