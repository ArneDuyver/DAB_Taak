package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Klant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    private int klantId;
    @Column
    private String naam;
    @Column
    private String adres;
    @Column
    private String email;
    @Column
    private String telefoonnummer;
    @OneToMany(mappedBy = "klant")
    private List<Koopt> kooptList;
    @OneToMany(mappedBy = "klant")
    private List<HaaltAf> haaltAfList;

    public Klant(String naam, String adres, String email, String telefoonnummer) {
        this.naam = naam;
        this.adres = adres;
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.kooptList = new ArrayList<Koopt>();
        this.haaltAfList = new ArrayList<>();
    }

    public Klant() {
    }

    public List<HaaltAf> getHaaltAfList() {
        return haaltAfList;
    }

    public List<Koopt> getKooptList() {
        return kooptList;
    }

    public int getKlantId() {
        return klantId;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }
    @Override
    public String toString() {
        return "Klant{" +
                "klantId=" + klantId +
                ",naam='" + naam + '\'' +
                ", adres='" + adres + '\'' +
                ", email=" + email +
                ", telefoonnummer=" + telefoonnummer +
                '}';
    }
}
