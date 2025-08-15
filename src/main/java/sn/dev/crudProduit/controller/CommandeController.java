package sn.dev.crudProduit.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sn.dev.crudProduit.Entities.Commande;
import sn.dev.crudProduit.doa.CommandeDoa;
import sn.dev.crudProduit.controller.Assembler.CommandeModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeDoa commandeDoa;
    private final CommandeModelAssembler assembler;

    public CommandeController(CommandeDoa commandeDoa, CommandeModelAssembler assembler) {
        this.commandeDoa = commandeDoa;
        this.assembler = assembler;
    }

    // GET /api/commandes
    @GetMapping
    public CollectionModel<EntityModel<Commande>> getAll(@RequestParam(name = "query", defaultValue = "") String query) {
        List<EntityModel<Commande>> commandes = commandeDoa.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(commandes,
                linkTo(methodOn(CommandeController.class).getAll("")).withSelfRel());
    }

    // GET /api/commandes/{id}
    @GetMapping("/{id}")
    public EntityModel<Commande> getById(@PathVariable Long id) {
        Commande commande = commandeDoa.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée"));
        return assembler.toModel(commande);
    }

    // POST /api/commandes
    @PostMapping
    public ResponseEntity<EntityModel<Commande>> create(@RequestBody Commande commande) {
        Commande saved = commandeDoa.save(commande);
        EntityModel<Commande> model = assembler.toModel(saved);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    // PUT /api/commandes/{id}
    @PutMapping("/{id}")
    public EntityModel<Commande> update(@PathVariable Long id, @RequestBody Commande input) {
        Commande updated = commandeDoa.findById(id).map(old -> {
            old.setUser(input.getUser());
            old.setProduits(input.getProduits());
            return commandeDoa.save(old);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande non trouvée"));
        return assembler.toModel(updated);
    }

    // DELETE /api/commandes/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        commandeDoa.deleteById(id);
    }

    //un endpoint pour les commandes d’un utilisateur

    // GET /api/commandes/user/{userId}
    @GetMapping("/user/{userId}")
    public CollectionModel<EntityModel<Commande>> getByUserId(@PathVariable Long userId) {
        List<EntityModel<Commande>> commandes = commandeDoa.findAllByUserId(userId)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(commandes,
                linkTo(methodOn(CommandeController.class).getByUserId(userId)).withSelfRel());
    }

    //endpoint pour les commandes contenant un produit spécifique

    // GET /api/commandes/produit/{produitId}
    @GetMapping("/produit/{produitId}")
    public CollectionModel<EntityModel<Commande>> getByProduitId(@PathVariable Long produitId) {
        List<EntityModel<Commande>> commandes = commandeDoa.findAllByProduitsId(produitId)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(commandes,
                linkTo(methodOn(CommandeController.class).getByProduitId(produitId)).withSelfRel());
    }

}
