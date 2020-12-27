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
    void saveNewPakketbeschrijving(Pakketbeschrijving pakketbeschrijving);
    void updatePakketbeschrijving(Pakketbeschrijving pakketbeschrijving);
    void deletePakketbeschrijving(Pakketbeschrijving pakketbeschrijving);

    void saveNewVerkoopt(Verkoopt verkoopt);
    void updateVerkoopt(Verkoopt verkoopt);
    void deleteVerkoopt(Verkoopt verkoopt);

    void saveNewKoopt(Koopt koopt);
    void updateKoopt(Koopt koopt);
    void deleteKoopt(Koopt koopt);

    void saveNewPakketinhoud(PakketInhoud pakketInhoud);
    void updatePakketinhoud(PakketInhoud pakketInhoud);
    void deletePakketinhoud(PakketInhoud pakketInhoud);

    void saveNewBehoortTot(BehoortTot behoortTot);
    void updateBehoortTot(BehoortTot behoortTot);
    void deleteBehoortTot(BehoortTot behoortTot);

    void saveNewBevat(Bevat bevat);
    void updateBevat(Bevat bevat);
    void deleteBevat(Bevat bevat);

    void saveNewHaaltAf(HaaltAf haaltAf);
    void updateHaaltAf(HaaltAf haaltAf);
    void deleteHaaltAf(HaaltAf haaltAf);




}