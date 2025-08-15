package sn.dev.crudProduit.controller.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import sn.dev.crudProduit.Entities.Categorie;
import sn.dev.crudProduit.controller.CategorieController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategorieModelAssembler implements RepresentationModelAssembler<Categorie, EntityModel<Categorie>> {

    @Override
    public EntityModel<Categorie> toModel(Categorie categorie) {
        return EntityModel.of(categorie,
                linkTo(methodOn(CategorieController.class).getById(categorie.getId())).withSelfRel(),
                linkTo(methodOn(CategorieController.class).getAll("")).withRel("categories")
        );
    }
}
