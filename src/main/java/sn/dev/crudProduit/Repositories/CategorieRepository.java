package sn.dev.crudProduit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dev.crudProduit.Entities.Categorie;

import java.util.List;

@Repository
public interface CategorieDoa extends JpaRepository<Categorie, Long> {

    // Rechercher des catégories par nom contenant une chaîne spécifique
    List<Categorie> findAllByNomContaining(String nom);
}
