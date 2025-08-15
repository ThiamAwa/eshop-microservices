package sn.dev.crudProduit.controller.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import sn.dev.crudProduit.Entities.Commande;
import sn.dev.crudProduit.controller.CommandeController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CommandeModelAssembler implements RepresentationModelAssembler<Commande, EntityModel<Commande>> {

    @Override
    public EntityModel<Commande> toModel(Commande commande) {
        return EntityModel.of(commande,
                linkTo(methodOn(CommandeController.class).getById(commande.getId())).withSelfRel(),
                linkTo(methodOn(CommandeController.class).getAll("")).withRel("commandes")
        );
    }
}
