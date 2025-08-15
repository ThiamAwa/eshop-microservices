package sn.dev.crudProduit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dev.crudProduit.Entities.Commande;

import java.util.List;

@Repository
public interface CommandeDoa extends JpaRepository<Commande, Long> {

    // Rechercher toutes les commandes d'un utilisateur spécifique
    List<Commande> findAllByUserId(Long userId);

    // Rechercher toutes les commandes contenant un produit spécifique
    List<Commande> findAllByProduitsId(Long produitId);

}
