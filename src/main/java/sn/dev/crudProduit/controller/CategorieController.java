package sn.dev.crudProduit.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sn.dev.crudProduit.Entities.Categorie;
import sn.dev.crudProduit.doa.CategorieDoa;
import sn.dev.crudProduit.controller.Assembler.CategorieModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieDoa categorieDoa;
    private final CategorieModelAssembler assembler;

    public CategorieController(CategorieDoa categorieDoa, CategorieModelAssembler assembler) {
        this.categorieDoa = categorieDoa;
        this.assembler = assembler;
    }

    // GET /api/categories
    @GetMapping
    public CollectionModel<EntityModel<Categorie>> getAll(@RequestParam(name = "query", defaultValue = "") String query) {
        List<EntityModel<Categorie>> categories = (query.isEmpty() ?
                categorieDoa.findAll() :
                categorieDoa.findAllByNomContaining(query))
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(categories,
                linkTo(methodOn(CategorieController.class).getAll("")).withSelfRel());
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public EntityModel<Categorie> getById(@PathVariable Long id) {
        Categorie categorie = categorieDoa.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie non trouvée"));
        return assembler.toModel(categorie);
    }

    // POST /api/categories
    @PostMapping
    public ResponseEntity<EntityModel<Categorie>> create(@RequestBody Categorie categorie) {
        Categorie saved = categorieDoa.save(categorie);
        EntityModel<Categorie> model = assembler.toModel(saved);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    // PUT /api/categories/{id}
    @PutMapping("/{id}")
    public EntityModel<Categorie> update(@PathVariable Long id, @RequestBody Categorie input) {
        Categorie updated = categorieDoa.findById(id).map(old -> {
            old.setNom(input.getNom());
            return categorieDoa.save(old);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie non trouvée"));
        return assembler.toModel(updated);
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categorieDoa.deleteById(id);
    }
}
