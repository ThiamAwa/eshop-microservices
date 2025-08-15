package sn.dev.crudProduit.controller;

import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sn.dev.crudProduit.Entities.Produit;
import sn.dev.crudProduit.controller.Assembler.ProduitModelAssembler;
import sn.dev.crudProduit.doa.ProductDoa;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProductDoa productDoa;
    private final ProduitModelAssembler assembler;

    public ProduitController(ProductDoa productDoa, ProduitModelAssembler assembler) {
        this.productDoa = productDoa;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Produit>> getAll(@RequestParam(name = "query", defaultValue = "") String query) {
        List<EntityModel<Produit>> produits = (query.isEmpty() ?
                productDoa.findAll() :
                productDoa.findAllByDesignationContaining(query))
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(produits,
                linkTo(methodOn(ProduitController.class).getAll("")).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Produit> getById(@PathVariable Long id) {
        Produit produit = productDoa.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
        return assembler.toModel(produit);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Produit>> create(@Valid @RequestBody Produit produit) {
        Produit saved = productDoa.save(produit);
        EntityModel<Produit> model = assembler.toModel(saved);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping("/{id}")
    public EntityModel<Produit> update(@PathVariable Long id, @Valid @RequestBody Produit input) {
        Produit updated = productDoa.findById(id).map(old -> {
            old.setDesignation(input.getDesignation());
            old.setPrix(input.getPrix());
            return productDoa.save(old);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé"));
        return assembler.toModel(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productDoa.deleteById(id);
    }
}
