package be.kuleuven.csa.domain;

import javax.persistence.EntityManager;
import java.util.List;

public class csaRepositoryJpaImpl implements csaRepository {
    public static final String TAG = "csaRepository";
    private final EntityManager entityManager;

    public csaRepositoryJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Boerderij> getBoerderijByName(String boerderij) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Boerderij.class);
        var root = query.from(Boerderij.class);

        query.where(criteriaBuilder.equal(root.get("naam"), boerderij));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Boerderij> getBoerderij() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Boerderij.class);
        var root = query.from(Boerderij.class);
        var all = query.select(root);
        return entityManager.createQuery(all).getResultList();
    }

    @Override
    public void saveNewBoerderij(Boerderij boerderij) {
        entityManager.getTransaction().begin();
        entityManager.persist(boerderij);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateBoerderij(Boerderij boerderij) {
        entityManager.getTransaction().begin();
        entityManager.merge(boerderij);
        entityManager.getTransaction().commit();

    }

    @Override
    public void deleteBoerderij(Boerderij boerderij) {
        entityManager.getTransaction().begin();
        entityManager.remove(boerderij);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Product> getProductByName(String product) {
        var criteriabuilder = entityManager.getCriteriaBuilder();
        var query = criteriabuilder.createQuery(Product.class);
        var root = query.from(Product.class);

        query.where(criteriabuilder.equal(root.get("naam"), product));
        return entityManager.createQuery(query).getResultList() ;
    }

    @Override
    public List<Product> getProduct() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Product.class);
        var root = query.from(Product.class);
        var all = query.select(root);
        return entityManager.createQuery(all).getResultList();
    }

    @Override
    public void saveNewProduct(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateProduct(Product product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteProduct(Product product) {
        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Klant> getKlantByName(String klant) {
        var criteriabuilder = entityManager.getCriteriaBuilder();
        var query = criteriabuilder.createQuery(Klant.class);
        var root = query.from(Klant.class);
        query.where(criteriabuilder.equal(root.get("naam"), klant));
        return entityManager.createQuery(query).getResultList() ;
    }

    @Override
    public List<Klant> getKlant() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Klant.class);
        var root = query.from(Klant.class);
        var all = query.select(root);
        return entityManager.createQuery(all).getResultList();
    }

    @Override
    public void saveNewKlant(Klant klant) {
        entityManager.getTransaction().begin();
        entityManager.persist(klant);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateKlant(Klant klant) {
        entityManager.getTransaction().begin();
        entityManager.merge(klant);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteKlant(Klant klant) {
        entityManager.getTransaction().begin();
        entityManager.remove(klant);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Pakketbeschrijving> getPakketbeschrijvingByName(String pakketbeschrijving) {
        var criteriabuilder = entityManager.getCriteriaBuilder();
        var query = criteriabuilder.createQuery(Pakketbeschrijving.class);
        var root = query.from(Pakketbeschrijving.class);

        query.where(criteriabuilder.equal(root.get("naam"), pakketbeschrijving));
        return entityManager.createQuery(query).getResultList() ;
    }

    @Override
    public void saveNewPakketbeschrijving(Pakketbeschrijving pakketbeschrijving) {
        entityManager.getTransaction().begin();
        entityManager.persist(pakketbeschrijving);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updatePakketbeschrijving(Pakketbeschrijving pakketbeschrijving) {
        entityManager.getTransaction().begin();
        entityManager.merge(pakketbeschrijving);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deletePakketbeschrijving(Pakketbeschrijving pakketbeschrijving) {
        entityManager.getTransaction().begin();
        entityManager.remove(pakketbeschrijving);
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveNewVerkoopt(Verkoopt verkoopt) {
        entityManager.getTransaction().begin();
        entityManager.persist(verkoopt);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateVerkoopt(Verkoopt verkoopt) {
        entityManager.getTransaction().begin();
        entityManager.merge(verkoopt);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteVerkoopt(Verkoopt verkoopt) {
        entityManager.getTransaction().begin();
        entityManager.remove(verkoopt);
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveNewKoopt(Koopt koopt) {
        entityManager.getTransaction().begin();
        entityManager.persist(koopt);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateKoopt(Koopt koopt) {
        entityManager.getTransaction().begin();
        entityManager.merge(koopt);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteKoopt(Koopt koopt) {
        entityManager.getTransaction().begin();
        entityManager.remove(koopt);
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveNewPakketinhoud(PakketInhoud pakketInhoud) {
        entityManager.getTransaction().begin();
        entityManager.persist(pakketInhoud);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updatePakketinhoud(PakketInhoud pakketInhoud) {
        entityManager.getTransaction().begin();
        entityManager.merge(pakketInhoud);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deletePakketinhoud(PakketInhoud pakketInhoud) {
        entityManager.getTransaction().begin();
        entityManager.remove(pakketInhoud);
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveNewBehoortTot(BehoortTot behoortTot) {
        entityManager.getTransaction().begin();
        entityManager.persist(behoortTot);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateBehoortTot(BehoortTot behoortTot) {
        entityManager.getTransaction().begin();
        entityManager.merge(behoortTot);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteBehoortTot(BehoortTot behoortTot) {
        entityManager.getTransaction().begin();
        entityManager.remove(behoortTot);
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveNewBevat(Bevat bevat) {
        entityManager.getTransaction().begin();
        entityManager.persist(bevat);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateBevat(Bevat bevat) {
        entityManager.getTransaction().begin();
        entityManager.merge(bevat);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteBevat(Bevat bevat) {
        entityManager.getTransaction().begin();
        entityManager.remove(bevat);
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveNewHaaltAf(HaaltAf haaltAf) {
        entityManager.getTransaction().begin();
        entityManager.persist(haaltAf);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateHaaltAf(HaaltAf haaltAf) {
        entityManager.getTransaction().begin();
        entityManager.merge(haaltAf);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteHaaltAf(HaaltAf haaltAf) {
        entityManager.getTransaction().begin();
        entityManager.remove(haaltAf);
        entityManager.getTransaction().commit();
    }
    @Override
    public void deleteObjectFromDb(Object object){
        if (Model.isPersistableObject(object)) {
            entityManager.getTransaction().begin();
            entityManager.remove(object);
            entityManager.getTransaction().commit();
        } else {
            throw new RuntimeException(TAG +": Trying to remove wrong object to JPA");
        }
    }
    @Override
    public void saveObjectToDb(Object object){
        if (Model.isPersistableObject(object)) {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } else {
            throw new RuntimeException(TAG +": Trying to save wrong object to JPA");
        }
    }
    @Override
    public void updateObjectFromDb(Object object){
        if (Model.isPersistableObject(object)) {
            entityManager.getTransaction().begin();
            entityManager.merge(object);
            entityManager.getTransaction().commit();
        } else {
            throw new RuntimeException(TAG +": Trying to update wrong object to JPA");
        }
    }

}
