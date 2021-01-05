package be.kuleuven.csa.domain;

import javax.persistence.Persistence;

public class dbTest {
        public static void main(String[] args) {
            System.out.println("Bootstrapping JPA/Hibernate...");
            var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
            var entityManager = sessionFactory.createEntityManager();

            System.out.println("Bootstrapping Repository...");
            var repo = new csaRepositoryJpaImpl(entityManager);

            System.out.println("Persisting nieuwehuis,appel, Kamiel...");
            var nieuwhuis = new Boerderij("nieuwhuis", "Boshoeve 11", "kamiel.allard@hotmail.com","BE45 4555 4555 4555");
            var appel = new Product("appel", "fruit");
            var Kamiel = new Klant("Allard Kamiel","Boshoeve 11", "kamiel.allard@hotmail.com", "0497688279");
            var medium = new Pakketbeschrijving("medium",2,1);
            var verkoopt = new Verkoopt(200,"2020-12-24",nieuwhuis,medium);
            var contract = new Koopt(verkoopt,Kamiel,false);
            var pakketinhoud = new PakketInhoud("tutifruti");
            var behoortTot = new BehoortTot(1,verkoopt,pakketinhoud);
            var bevat1  =new Bevat("aantal",3,pakketinhoud,appel);
            var haaltaf = new HaaltAf(behoortTot,Kamiel);
            //var verkoopt2 = new Verkoopt(300, "2020-12-24",nieuwhuis,medium);git
            repo.saveNewBoerderij(nieuwhuis);
            repo.saveNewProduct(appel);
            repo.saveNewKlant(Kamiel);
            repo.saveNewPakketbeschrijving(medium);
            repo.saveNewVerkoopt(verkoopt);
            repo.saveNewKoopt(contract, false);
            repo.saveNewPakketinhoud(pakketinhoud);
            repo.saveNewBehoortTot(behoortTot);
            repo.saveNewBevat(bevat1);
            repo.saveNewHaaltAf(haaltaf);
            nieuwhuis.setAdres("Boshoeve 12");
            appel.setNaam("jonagold");
            Kamiel.setEmail("kamiel.allard@student.uhasselt.com");
            repo.updateBoerderij(nieuwhuis);
            repo.updateProduct(appel);
            repo.updateKlant(Kamiel);

            var testdelete =new Boerderij("nieuwhuis", "Boshoeve 11", "kamiel.allard@hotmail.com","BE45 4555 4555 4555");
            //repo.deleteBoerderij(testdelete);// werkt niet want id gaat omhoog
            //repo.deleteBoerderij(nieuwhuis);// werkt wel
            isBevatEr(repo, pakketinhoud);
            isBoerderijEr(repo);
            isKlantEr(repo);
            isProductEr(repo);



        }

        private static void isBoerderijEr(csaRepositoryJpaImpl repo) {
            System.out.println("Is Boerderij er?");
            for(var eenStudent : repo.getBoerderijByName("nieuwhuis")) {
                System.out.println(eenStudent);
            }
        }
        private static void isProductEr(csaRepositoryJpaImpl repo) {
            System.out.println("Is Product er?");
            for(var eenStudent : repo.getProductByName("jonagold")) {
                System.out.println(eenStudent);
            }
        }
        private static void isKlantEr(csaRepositoryJpaImpl repo) {
            System.out.println("Is Klant er?");
            for(var eenStudent : repo.getKlantByName("Allard Kamiel")) {
                System.out.println(eenStudent);
            }
        }
        private static void isBevatEr(csaRepositoryJpaImpl repo,PakketInhoud pakketInhoud) {
            System.out.println("Is bevat er?");
            for(var eenStudent : repo.getBevat(pakketInhoud)) {
                System.out.println(eenStudent.getEenheid());
            }
        }
}


