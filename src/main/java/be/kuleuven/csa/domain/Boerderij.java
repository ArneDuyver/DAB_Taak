package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Boerderij {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    private int boerderijId;

    @Column
    private String naam;
    @Column
    private String adres;
    @Column
    private String email;
    @Column
    private String rekeningnummer;
    @Column
    private int opbrengst;
    @OneToMany(mappedBy = "boerderij")
    private List<Verkoopt> verkooptList;

    public Boerderij() {
    }

    public Boerderij(String naam, String adres, String email, String rekeningnummer) {
        this.naam = naam;
        this.adres = adres;
        this.email = email;
        this.rekeningnummer = rekeningnummer;
        this.verkooptList = new ArrayList<Verkoopt>();
    }

    public List<Verkoopt> getVerkooptList() {
        return verkooptList;
    }

    public int getBoerderijId() {
        return boerderijId;
    }

    public String getNaam() {
        return naam;
    }

    public String getAdres() {
        return adres;
    }

    public String getEmail() {
        return email;
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }

    public int getOpbrengst() {
        return opbrengst;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }

    public void setOpbrengst(int opbrengst) {
        this.opbrengst = opbrengst;
    }

   // public Set<Verkoopt> getPakketbeschrijvingen() {
     //   return pakketbeschrijvingen;
    //}

    @Override
    public String toString() {
        return
                "BoerderijId=" + boerderijId +
                ",naam='" + naam + '\'' ;
    }

    public void addOpbrengst(int prijs) {
        this.opbrengst = opbrengst + prijs;
    }

    public void subOpbrengst(int prijs) {this.opbrengst = opbrengst - prijs;}

}
