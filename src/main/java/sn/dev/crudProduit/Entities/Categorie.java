package sn.dev.crudProduit.Entities;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Categorie {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull @Size(min=3, max=30)
        private String nom;

        // Getters / Setters


    public Categorie() {
    }

    public Categorie(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
