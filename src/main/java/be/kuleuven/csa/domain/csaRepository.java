package be.kuleuven.csa.domain;

import java.util.List;

public interface csaRepository {

    void saveObjectToDb(Object object);
    void updateObjectFromDb(Object object);

    List<Boerderij> getBoerderijByName(String boerderij);
    List<Boerderij> getBoerderij();
    void saveNewBoerderij(Boerderij boerderij);
    void updateBoerderij(Boerderij boerderij);
    void deleteBoerderij(Boerderij boerderij);

    List<Product> getProductByName(String product);
    List<Product> getProduct();
    void saveNewProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);

    List<Klant> getKlantByName(String klant);
    List<Klant> getKlant();
    void saveNewKlant(Klant klant);
    void updateKlant(Klant klant);
    void deleteKlant(Klant klant);

    List<Pakketbeschrijving> getPakketbeschrijvingByName(String pakketbeschrijving);
    List<Pakketbeschrijving> getPakketbeschijving();
    void saveNewPakketbeschrijving(Pakketbeschrijving pakketbeschrijving);
    void updatePakketbeschrijving(Pakketbeschrijving pakketbeschrijving);
    void deletePakketbeschrijving(Pakketbeschrijving pakketbeschrijving);

    List<Verkoopt> getVerkopen();
    List<Verkoopt> getVerkopenPakketbeschrijving(Pakketbeschrijving pakketbeschrijving);
    void saveNewVerkoopt(Verkoopt verkoopt);
    void updateVerkoopt(Verkoopt verkoopt);
    void deleteVerkoopt(Verkoopt verkoopt);

    List<Koopt> getKopen();
    void saveNewKoopt(Koopt koopt, boolean betaald);
    void updateKoopt(Koopt koopt, boolean albetaald, boolean nuBetaald);
    void deleteKoopt(Koopt koopt);

    List<PakketInhoud> getPakketinhouden();
    void saveNewPakketinhoud(PakketInhoud pakketInhoud);
    void updatePakketinhoud(PakketInhoud pakketInhoud);
    void deletePakketinhoud(PakketInhoud pakketInhoud);

    List<BehoortTot> getBehoortTot();
    void saveNewBehoortTot(BehoortTot behoortTot);
    void updateBehoortTot(BehoortTot behoortTot);
    void deleteBehoortTot(BehoortTot behoortTot);

    List<Bevat> getBevat(PakketInhoud pakketinhoud);
    void saveNewBevat(Bevat bevat);
    void updateBevat(Bevat bevat);
    void deleteBevat(Bevat bevat);

    List<HaaltAf> getHaaltAf(Klant klant);
    List<HaaltAf> getHaaltAfBoerderij(Boerderij boerderij);
    void saveNewHaaltAf(HaaltAf haaltAf);
    void updateHaaltAf(HaaltAf haaltAf);
    void deleteHaaltAf(HaaltAf haaltAf);



}