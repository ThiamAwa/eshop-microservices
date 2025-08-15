package sn.dev.crudProduit.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sn.dev.crudProduit.Entities.User;
import sn.dev.crudProduit.doa.UserDoa;
import java.util.List;
import java.util.stream.Collectors;
import sn.dev.crudProduit.controller.Assembler.UserModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDoa userDoa;
    private final UserModelAssembler assembler;

    public UserController(UserDoa userDoa, UserModelAssembler assembler) {
        this.userDoa = userDoa;
        this.assembler = assembler;
    }

    // GET /api/users
    @GetMapping
    public CollectionModel<EntityModel<User>> getAll(@RequestParam(name = "query", defaultValue = "") String query) {
        List<EntityModel<User>> users = (query.isEmpty() ?
                userDoa.findAll() :
                userDoa.findAllByNomContaining(query))
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll("")).withSelfRel());
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public EntityModel<User> getById(@PathVariable Long id) {
        User user = userDoa.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User non trouvé"));
        return assembler.toModel(user);
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<EntityModel<User>> create(@RequestBody User user) {
        User saved = userDoa.save(user);
        EntityModel<User> model = assembler.toModel(saved);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public EntityModel<User> update(@PathVariable Long id, @RequestBody User input) {
        User updated = userDoa.findById(id).map(old -> {
            old.setNom(input.getNom());
            old.setEmail(input.getEmail());
            return userDoa.save(old);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User non trouvé"));
        return assembler.toModel(updated);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userDoa.deleteById(id);
    }
}
