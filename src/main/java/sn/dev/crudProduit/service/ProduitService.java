package sn.dev.crudProduit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.dev.crudProduit.Entities.Produit;
import sn.dev.crudProduit.doa.ProductDoa;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {
    private ProductDoa productDoa;

    public ProduitService(ProductDoa productDoa) {
        this.productDoa = productDoa;
    }

    public List<Produit> findAll() {
        return productDoa.findAll();
    }

    public List<Produit> findAllByDesignationContaining(String designation) {
        return productDoa.findAllByDesignationContaining(designation);
    }

    public Optional<Produit> findById(Long id) {
        return productDoa.findById(id);
    }

    public Produit create(Produit produit) {
        return productDoa.save(produit);
    }
    public Produit update(Produit produit) {
        return productDoa.save(produit);
    }
    public void delete(Long id) {
        productDoa.deleteById(id);
    }


}
