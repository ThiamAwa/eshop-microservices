package sn.dev.crudProduit.controller.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import sn.dev.crudProduit.Entities.Produit;
import sn.dev.crudProduit.controller.ProduitController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class ProduitModelAssembler implements RepresentationModelAssembler<Produit, EntityModel<Produit>> {
    @Override
    public EntityModel<Produit> toModel(Produit produit) {
        return EntityModel.of(produit,

                WebMvcLinkBuilder.linkTo(methodOn(ProduitController.class).getById(produit.getId().longValue())).withSelfRel(),

                linkTo(methodOn(ProduitController.class).getAll("")).withRel("produits"));
    }
}


