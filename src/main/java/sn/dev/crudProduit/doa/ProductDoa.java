package sn.dev.crudProduit.doa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dev.crudProduit.Entities.Produit;

import java.util.List;

@Repository
public interface ProductDoa extends JpaRepository<Produit, Long> {
    List<Produit> findAllByDesignationContaining(String designation);
}
